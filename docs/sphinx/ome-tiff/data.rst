OME-TIFF sample data
====================

This section provides some sample data in OME-TIFF format. They include data
produced from an acquisition system as well as artificial sample datasets (i.e.
designed for developer testing that illustrate some possible data
organizations, which should be useful if you are interested in implementing
support for OME-TIFF within your software.

All the OME-TIFF sample data discussed below are available from our
:ometiff_downloads:`OME-TIFF sample images resource <>`.

Single file OME-TIFF
--------------------

This section lists various examples of single file OME-TIFF. For examples of
OME-TIFF dataset distributed across multiple TIFF files, see
:ref:`multifile_samples`.

.. _artificial-datasets:

Artificial datasets
^^^^^^^^^^^^^^^^^^^

All datasets in the following table were :source:`artificially
generated <components/formats-bsd/src/loci/formats/tools/MakeTestOmeTiff.java>`
with each plane labeled according to its dimensional position for easy
testing. Each one consists of a single OME-TIFF file (2016-06 schema
version) containing every constituent image plane. 

.. list-table::
  :header-rows: 1
  :widths: 40, 20, 20, 20

  -  * Sample
     * Channels
     * Focal planes
     * Time points
  
  -  * :ometiff_downloads:`single-channel.ome.tif <bioformats-artificial/single-channel.ome.tif>`
     * 1
     * 1
     * 1

  -  * :ometiff_downloads:`multi-channel.ome.tif <bioformats-artificial/multi-channel.ome.tif>`
     * 3
     * 1
     * 1

  -  * :ometiff_downloads:`z-series.ome.tif <bioformats-artificial/z-series.ome.tif>`
     * 1
     * 5
     * 1

  -  * :ometiff_downloads:`time-series.ome.tif <bioformats-artificial/time-series.ome.tif>`
     * 1
     * 1
     * 7

  -  * :ometiff_downloads:`multi-channel-z-series.ome.tif <bioformats-artificial/multi-channel-z-series.ome.tif>`
     * 3
     * 5
     * 1

  -  * :ometiff_downloads:`multi-channel-time-series.ome.tif <bioformats-artificial/multi-channel-time-series.ome.tif>`
     * 3
     * 1
     * 7

  -  * :ometiff_downloads:`4D-series.ome.tif <bioformats-artificial/4D-series.ome.tif>`
     * 1
     * 5
     * 7

  -  * :ometiff_downloads:`multi-channel-4D-series.ome.tif <bioformats-artificial/multi-channel-4D-series.ome.tif>`
     * 3
     * 5
     * 7

.. _modulo-datasets:

Modulo datasets
^^^^^^^^^^^^^^^

Sample files implementing the :doc:`/developers/6d-7d-and-8d-storage` are available from the
:ometiff_downloads:`Modulo <modulo>` folder of the image downloads resource.

SPIM
""""

- :ometiff_downloads:`SPIM-ModuloAlongZ.ome.tiff <modulo/SPIM-ModuloAlongZ.ome.tiff>` - 4 tiles each recorded at 4 angles.

Big lambda
""""""""""

- :ometiff_downloads:`LAMBDA-ModuloAlongZ-ModuloAlongT.ome.tiff <modulo/LAMBDA-ModuloAlongZ-ModuloAlongT.ome.tiff>` -
  excitation of 5 wavelength [Λ, big-lambda] each recorded at 10 emission
  wavelength ranges [λ, lambda].

FLIM
""""

- :ometiff_downloads:`FLIM-ModuloAlongT-TSCPC.ome.tiff <modulo/FLIM-ModuloAlongT-TSCPC.ome.tiff>` -
  2 channels and 8 histogram bins each recorded at 2 'real-time' points T,
  with additional relative-time points (time relative to the
  excitation pulse) interleaved as ModuloAlongT.

- :ometiff_downloads:`FLIM-ModuloAlongC.ome.tiff <modulo/FLIM-ModuloAlongC.ome.tiff>` -
  2 real channels and 8 histogram bins each recorded at 2 timepoints, with
  additional relative-time points interleaved between channels as
  ModuloAlongC. 

.. _multifile_samples:

Multi-file OME-TIFF filesets
----------------------------

This section lists various examples of OME-TIFF datasets distributed across multiple TIFF files.

.. _tubhiswt_samples:

Sample biological dataset
^^^^^^^^^^^^^^^^^^^^^^^^^

The following OME-TIFF datasets consist of tubulin histone GFP coexpressing 
*C. elegans* embryos. Many thanks to
`Josh Bembenek <http://loci.wisc.edu/people/josh-bembenek>`_ for preparing
and imaging this sample data.

The datasets were acquired on a multiphoton workstation (2.1 GHz Athlon
XP 3200+ with 1GB of RAM) using
`WiscScan <http://loci.wisc.edu/software/wiscscan>`_. All image
planes were collected at 512x512 resolution in 8-bit grayscale, with an
integration value of 2.

The files available for download have been updated to the current schema
version since their initial creation.

.. list-table::
  :header-rows: 1
  :widths: 20, 20, 15, 15, 15, 15

  -  * Fileset
     * Zipped fileset
     * Channels
     * Focal planes
     * Time points
     * Number of files
  
  -  * :ometiff_downloads:`tubhiswt-2D <tubhiswt-2D>`
     * :ometiff_downloads:`tubhiswt-2D.zip <tubhiswt-2D.zip>`
     * 2
     * 1
     * 1
     * 2

  -  * :ometiff_downloads:`tubhiswt-3D <tubhiswt-3D>`
     * :ometiff_downloads:`tubhiswt-3D.zip <tubhiswt-3D.zip>`
     * 2
     * 1
     * 20
     * 2

  -  * :ometiff_downloads:`tubhiswt-4D <tubhiswt-4D>`
     * :ometiff_downloads:`tubhiswt-4D.zip <tubhiswt-4D.zip>`
     * 2
     * 10
     * 43
     * 86

Partial OME-XML metadata filesets
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

The :ometiff_downloads:`master OME-TIFF fileset <binaryonly>` and
:ometiff_downloads:`companion OME-XML fileset <companion>` both contain a set
of 18 by 24 pixel images with black and white text on each plane giving
its time, z-depth and channel. Each of the five focal planes is saved as a
separate OME-TIFF named :file:`multifile-Zxx.ome.tiff` where `xx` is the index
of the focal plane.

.. _master-sample:

Master OME-TIFF fileset
"""""""""""""""""""""""

For this sample, the full OME-XML metadata describing the whole fileset is
embedded into a
:ometiff_downloads:`master OME-TIFF file <binaryonly/multifile-Z1.ome.tiff>`.
Partial OME-XML metadata blocks are embedded into the four other OME-TIFF
files and refer to the master OME-TIFF file as described in the
:ref:`specification <binary_only>`.

.. _companion-sample:

Companion OME-XML fileset
"""""""""""""""""""""""""

For this sample, the full OME-XML metadata describing the whole fileset is
stored into a separate
:ometiff_downloads:`companion OME-XML file<companion/multifile.companion.ome>`.
Partial OME-XML metadata blocks are embedded into the five OME-TIFF
files and refer to the companion OME-XML file as described in the
:ref:`specification <binary_only>`.
