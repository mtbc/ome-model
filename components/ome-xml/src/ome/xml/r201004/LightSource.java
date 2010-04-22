/*
 * ome.xml.r201004.LightSource
 *
 *-----------------------------------------------------------------------------
 *
 *  Copyright (C) @year@ Open Microscopy Environment
 *      Massachusetts Institute of Technology,
 *      National Institutes of Health,
 *      University of Dundee,
 *      University of Wisconsin-Madison
 *
 *
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation; either
 *    version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public
 *    License along with this library; if not, write to the Free Software
 *    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *-----------------------------------------------------------------------------
 */

/*-----------------------------------------------------------------------------
 *
 * THIS IS AUTOMATICALLY GENERATED CODE.  DO NOT MODIFY.
 * Created by callan via xsd-fu on 2010-04-22 16:50:50+0100
 *
 *-----------------------------------------------------------------------------
 */

package ome.xml.r201004;

import java.util.ArrayList;
import java.util.List;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ome.xml.r201004.enums.*;

public abstract class LightSource extends ManufacturerSpec
{
	// -- Constants --

	public static final String NAMESPACE = "http://www.openmicroscopy.org/Schemas/OME/2010-04";

	// -- Instance variables --

	// Property
	private String id;

	// Property
	private Double power;

	// *** WARNING *** Unhandled or skipped property Laser

	// *** WARNING *** Unhandled or skipped property Filament

	// *** WARNING *** Unhandled or skipped property Arc

	// *** WARNING *** Unhandled or skipped property LightEmittingDiode

	// -- Constructors --

	/** Default constructor. */
	public LightSource()
	{
		super();
	}

	/** 
	 * Constructs LightSource recursively from an XML DOM tree.
	 * @param element Root of the XML DOM tree to construct a model object
	 * graph from.
	 * @throws EnumerationException If there is an error instantiating an
	 * enumeration during model object creation.
	 */
	public LightSource(Element element) throws EnumerationException
	{
		update(element);
	}

	/** 
	 * Updates LightSource recursively from an XML DOM tree. <b>NOTE:</b> No
	 * properties are removed, only added or updated.
	 * @param element Root of the XML DOM tree to construct a model object
	 * graph from.
	 * @throws EnumerationException If there is an error instantiating an
	 * enumeration during model object creation.
	 */
	public void update(Element element) throws EnumerationException
	{	
		super.update(element);
		if (element.hasAttribute("ID"))
		{
			// Attribute property ID
			setID(String.valueOf(
					element.getAttribute("ID")));
		}
		if (element.hasAttribute("Power"))
		{
			// Attribute property Power
			setPower(Double.valueOf(
					element.getAttribute("Power")));
		}
		NodeList Laser_nodeList = element.getElementsByTagName("Laser");
		if (Laser_nodeList.getLength() > 1)
		{
			// TODO: Should be its own Exception
			throw new RuntimeException(String.format(
					"Laser node list size %d != 1",
					Laser_nodeList.getLength()));
		}
		else if (Laser_nodeList.getLength() != 0)
		{
		}
		NodeList Filament_nodeList = element.getElementsByTagName("Filament");
		if (Filament_nodeList.getLength() > 1)
		{
			// TODO: Should be its own Exception
			throw new RuntimeException(String.format(
					"Filament node list size %d != 1",
					Filament_nodeList.getLength()));
		}
		else if (Filament_nodeList.getLength() != 0)
		{
		}
		NodeList Arc_nodeList = element.getElementsByTagName("Arc");
		if (Arc_nodeList.getLength() > 1)
		{
			// TODO: Should be its own Exception
			throw new RuntimeException(String.format(
					"Arc node list size %d != 1",
					Arc_nodeList.getLength()));
		}
		else if (Arc_nodeList.getLength() != 0)
		{
		}
		NodeList LightEmittingDiode_nodeList = element.getElementsByTagName("LightEmittingDiode");
		if (LightEmittingDiode_nodeList.getLength() > 1)
		{
			// TODO: Should be its own Exception
			throw new RuntimeException(String.format(
					"LightEmittingDiode node list size %d != 1",
					LightEmittingDiode_nodeList.getLength()));
		}
		else if (LightEmittingDiode_nodeList.getLength() != 0)
		{
		}
	}

	// -- LightSource API methods --


	// Property
	public String getID()
	{
		return id;
	}

	public void setID(String id)
	{
		this.id = id;
	}

	// Property
	public Double getPower()
	{
		return power;
	}

	public void setPower(Double power)
	{
		this.power = power;
	}

	// *** WARNING *** Unhandled or skipped property Laser

	// *** WARNING *** Unhandled or skipped property Filament

	// *** WARNING *** Unhandled or skipped property Arc

	// *** WARNING *** Unhandled or skipped property LightEmittingDiode

	protected Element asXMLElement(Document document, Element LightSource_element)
	{
		// Creating XML block for LightSource
		// Class is abstract so we may need to create its "container" element
		if (!"LightSource".equals(LightSource_element.getTagName()))
		{
			Element abstractElement =
					document.createElementNS(NAMESPACE, "LightSource");
			abstractElement.appendChild(LightSource_element);
			LightSource_element = abstractElement;
		}
		if (LightSource_element == null)
		{
			LightSource_element =
					document.createElementNS(NAMESPACE, "LightSource");
		}

		if (id != null)
		{
			// Attribute property ID
			LightSource_element.setAttribute("ID", id.toString());
		}
		if (power != null)
		{
			// Attribute property Power
			LightSource_element.setAttribute("Power", power.toString());
		}
		return super.asXMLElement(document, LightSource_element);
	}
}