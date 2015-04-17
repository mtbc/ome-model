/*
 * #%L
 * OME-BIOFORMATS C++ library for image IO.
 * Copyright © 2006 - 2014 Open Microscopy Environment:
 *   - Massachusetts Institute of Technology
 *   - National Institutes of Health
 *   - University of Dundee
 *   - Board of Regents of the University of Wisconsin-Madison
 *   - Glencoe Software, Inc.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are
 * those of the authors and should not be interpreted as representing official
 * policies, either expressed or implied, of any organization.
 * #L%
 */

#include <cassert>

#include <boost/format.hpp>
#include <boost/range/size.hpp>

#include <ome/bioformats/FormatException.h>
#include <ome/bioformats/FormatTools.h>
#include <ome/bioformats/MetadataTools.h>
#include <ome/bioformats/out/MinimalTIFFWriter.h>
#include <ome/bioformats/tiff/IFD.h>
#include <ome/bioformats/tiff/TIFF.h>

#include <ome/internal/config.h>

using ome::bioformats::detail::WriterProperties;
using ome::bioformats::tiff::TIFF;
using ome::bioformats::tiff::IFD;
using ome::xml::model::enums::PixelType;
using ome::xml::meta::MetadataRetrieve;

namespace ome
{
  namespace bioformats
  {
    namespace out
    {

      namespace
      {

        // Note that tf2, tf8 and btf are all extensions for "bigTIFF"
        // (2nd generation TIFF, TIFF with 8-byte offsets and big TIFF
        // respectively).
        const char *suffixes[] = {"tif", "tiff", "tf2", "tf8", "btf"};

        WriterProperties
        tiff_properties()
        {
          WriterProperties p("MinimalTIFF",
                             "Baseline Tagged Image File Format");

          p.suffixes = std::vector<boost::filesystem::path>(suffixes,
                                                            suffixes + boost::size(suffixes));


          const PixelType::value_map_type& pv = PixelType::values();
          std::set<ome::xml::model::enums::PixelType> pixeltypes;
          for (PixelType::value_map_type::const_iterator i = pv.begin();
               i != pv.end();
               ++i)
            {
              pixeltypes.insert(i->first);
            }
          p.codec_pixel_types.insert(WriterProperties::codec_pixel_type_map::value_type("default", pixeltypes));

          return p;
        }

        const WriterProperties props(tiff_properties());

      }

      MinimalTIFFWriter::MinimalTIFFWriter():
        ::ome::bioformats::detail::FormatWriter(props),
        logger(ome::common::createLogger("MinimalTIFFWriter")),
        tiff(),
        ifd(),
        ifdIndex(0),
        seriesIFDRange(),
        bigTIFF(boost::none)
      {
      }

      MinimalTIFFWriter::MinimalTIFFWriter(const WriterProperties& writerProperties):
        ::ome::bioformats::detail::FormatWriter(writerProperties),
        logger(ome::common::createLogger("MinimalTIFFWriter")),
        tiff(),
        ifd(),
        ifdIndex(0),
        seriesIFDRange(),
        bigTIFF(boost::none)
      {
      }

      MinimalTIFFWriter::~MinimalTIFFWriter()
      {
      }

      void
      MinimalTIFFWriter::setId(const boost::filesystem::path& id)
      {
        FormatWriter::setId(id);

        std::string flags("w");

        // File extension in use.
        boost::filesystem::path ext = currentId->extension();

        // Get expected size of pixel data.
        ome::compat::shared_ptr<const ::ome::xml::meta::MetadataRetrieve> mr(getMetadataRetrieve());
        storage_size_type pixelSize = significantPixelSize(*mr);

        // Enable BigTIFF if using a "big" file extension.
        bool extBig =
          (ext == boost::filesystem::path(".tf2") ||
           ext == boost::filesystem::path(".tf8") ||
           ext == boost::filesystem::path(".btf"));

        // Enable BigTIFF if the pixel size is sufficiently large.
        // Multiply by 5% to allow for alignment and TIFF metadata
        // overhead.
        bool needBig = (pixelSize + pixelSize/20) > storage_size_type(std::numeric_limits<uint32_t>::max());

        boost::optional<bool> wantBig = getBigTIFF();
#if TIFF_HAVE_BIGTIFF
        if ((wantBig && *wantBig)     // BigTIFF explicitly requested.
            || extBig                 // BigTIFF file extension used
            || (!wantBig && needBig)) // BigTIFF unspecified but needed.
          {
            flags += "8";

            if (!wantBig && !extBig) // Not set manually
              {
                boost::format fmt
                  ("Pixel data size is %1%, but TIFF without BigTIFF "
                   "support enabled has a maximum size of %2%; "
                   "automatically enabling BigTIFF support to prevent potential failure");
                fmt % pixelSize % std::numeric_limits<uint32_t>::max();

                BOOST_LOG_SEV(logger, ome::logging::trivial::warning) << fmt.str();
              }
          }
        else if (wantBig && !*wantBig && needBig) // BigTIFF explicitly disabled but needed.
          {
            boost::format fmt
              ("Pixel data size is %1%, but TIFF with BigTIFF "
               "support disabled has a maximum size of %2%; "
               "TIFF writing may fail if the limit is exceeded");
            fmt % pixelSize % std::numeric_limits<uint32_t>::max();

            BOOST_LOG_SEV(logger, ome::logging::trivial::warning) << fmt.str();
          }
#else // ! TIFF_HAVE_BIGTIFF
        if (needBig) // BigTIFF needed (but unsupported)
          {
            boost::format fmt
              ("Unable to enable BigTIFF support since libtiff support "
               " for BigTIFF is unavailable.  "
               "Pixel data size is %1%, but TIFF without BigTIFF "
               "support enabled has a maximum size of %2%; "
               "TIFF writing may fail if the limit is exceeded; ");
            fmt % pixelSize % std::numeric_limits<uint32_t>::max();

            BOOST_LOG_SEV(logger, ome::logging::trivial::warning) << fmt.str();
          }
        else if ((wantBig && *wantBig) || extBig) // BigTIFF explicitly requested (but unsupported)
          {
            BOOST_LOG_SEV(logger, ome::logging::trivial::warning)
              << "Unable to enable BigTIFF support since libtiff support "
              " for BigTIFF is unavailable";
          }
#endif // TIFF_HAVE_BIGTIFF

        tiff = TIFF::open(id, flags);
        ifd = tiff->getCurrentDirectory();
        setupIFD();

        // Create IFD mapping from metadata.
        MetadataRetrieve::index_type imageCount = metadataRetrieve->getImageCount();
        dimension_size_type currentIFD = 0U;
        for (MetadataRetrieve::index_type i = 0;
             i < imageCount;
             ++i)
          {
            dimension_size_type planeCount = getImageCount();

            tiff::IFDRange range;
            range.filename = *currentId;
            range.begin = currentIFD;
            range.end = currentIFD + planeCount;

            seriesIFDRange.push_back(range);
            currentIFD += planeCount;
          }
      }

      void
      MinimalTIFFWriter::close(bool fileOnly)
      {
        if (tiff)
          {
            tiff->writeCurrentDirectory();
            tiff->close();
            ifd.reset();
            tiff.reset();
          }
        if (!fileOnly)
          {
            ifdIndex = 0;
            seriesIFDRange.clear();
            bigTIFF = boost::none;
          }
        detail::FormatWriter::close(fileOnly);
      }

      void
      MinimalTIFFWriter::setSeries(dimension_size_type no) const
      {
        const dimension_size_type currentSeries = getSeries();
        detail::FormatWriter::setSeries(no);

        if (currentSeries != no)
          {
            nextIFD();
            setupIFD();
          }
      }

      void
      MinimalTIFFWriter::setPlane(dimension_size_type no) const
      {
        const dimension_size_type currentPlane = getPlane();
        detail::FormatWriter::setPlane(no);

        if (currentPlane != no)
          {
            nextIFD();
            setupIFD();
          }
      }

      void
      MinimalTIFFWriter::nextIFD() const
      {
        tiff->writeCurrentDirectory();
        ifd = tiff->getCurrentDirectory();
        ++ifdIndex;
      }

      void
      MinimalTIFFWriter::setupIFD() const
      {
        // Default to single strips for now.
        ifd->setImageWidth(getSizeX());
        ifd->setImageHeight(getSizeY());

        ifd->setTileType(tiff::STRIP);
        ifd->setTileWidth(getSizeX());
        ifd->setTileHeight(1U);

        ome::compat::array<dimension_size_type, 3> coords = getZCTCoords(getPlane());

        dimension_size_type channel = coords[1];

        ifd->setPixelType(getPixelType());
        ifd->setBitsPerSample(bitsPerPixel(getPixelType()));
        ifd->setSamplesPerPixel(getRGBChannelCount(channel));

        const boost::optional<bool> interleaved(getInterleaved());
        if (isRGB(channel) && interleaved && *interleaved)
          ifd->setPlanarConfiguration(tiff::CONTIG);
        else
          ifd->setPlanarConfiguration(tiff::SEPARATE);

        if (isRGB(channel) && getRGBChannelCount(channel) == 3)
          ifd->setPhotometricInterpretation(tiff::RGB);
        else
          ifd->setPhotometricInterpretation(tiff::MIN_IS_BLACK);
      }

      void
      MinimalTIFFWriter::saveBytes(dimension_size_type no,
                                   VariantPixelBuffer& buf,
                                   dimension_size_type x,
                                   dimension_size_type y,
                                   dimension_size_type w,
                                   dimension_size_type h)
      {
        assertId(currentId, true);

        setPlane(no);

        dimension_size_type expectedIndex =
          tiff::ifdIndex(seriesIFDRange, getSeries(), no);

        if (ifdIndex != expectedIndex)
          {
            boost::format fmt("IFD index mismatch: actual is %1% but %2% expected");
            fmt % ifdIndex % expectedIndex;
            throw FormatException(fmt.str());
          }

        ifd->writeImage(buf, x, y, w, h);
      }

      void
      MinimalTIFFWriter::setBigTIFF(boost::optional<bool> big)
      {
        bigTIFF = big;
      }

      boost::optional<bool>
      MinimalTIFFWriter::getBigTIFF() const
      {
        return bigTIFF;
      }

    }
  }
}
