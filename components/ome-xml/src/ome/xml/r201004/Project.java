/*
 * ome.xml.r201004.Project
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

public class Project extends AbstractOMEModelObject
{
	// -- Constants --

	public static final String NAMESPACE = "http://www.openmicroscopy.org/Schemas/OME/2010-04";

	// -- Instance variables --

	// Property
	private String name;

	// Property
	private String id;

	// Property
	private String description;

	// Property
	private Experimenter experimenter;

	// Property
	private Group group;

	// Reference AnnotationRef
	private List<Annotation> annotationList = new ArrayList<Annotation>();

	// Back reference Dataset_BackReference
	private List<Dataset> dataset_BackReferenceList = new ArrayList<Dataset>();

	// -- Constructors --

	/** Default constructor. */
	public Project()
	{
		super();
	}

	/** 
	 * Constructs Project recursively from an XML DOM tree.
	 * @param element Root of the XML DOM tree to construct a model object
	 * graph from.
	 * @throws EnumerationException If there is an error instantiating an
	 * enumeration during model object creation.
	 */
	public Project(Element element) throws EnumerationException
	{
		update(element);
	}

	/** 
	 * Updates Project recursively from an XML DOM tree. <b>NOTE:</b> No
	 * properties are removed, only added or updated.
	 * @param element Root of the XML DOM tree to construct a model object
	 * graph from.
	 * @throws EnumerationException If there is an error instantiating an
	 * enumeration during model object creation.
	 */
	public void update(Element element) throws EnumerationException
	{	
		super.update(element);
		String tagName = element.getTagName();
		if (!"Project".equals(tagName))
		{
			System.err.println(String.format(
					"WARNING: Expecting node name of Project got %s",
					tagName));
			// TODO: Should be its own Exception
			//throw new RuntimeException(String.format(
			//		"Expecting node name of Project got %s",
			//		tagName));
		}
		if (element.hasAttribute("Name"))
		{
			// Attribute property Name
			setName(String.valueOf(
					element.getAttribute("Name")));
		}
		if (element.hasAttribute("ID"))
		{
			// Attribute property ID
			setID(String.valueOf(
					element.getAttribute("ID")));
		}
		NodeList Description_nodeList = element.getElementsByTagName("Description");
		if (Description_nodeList.getLength() > 1)
		{
			// TODO: Should be its own Exception
			throw new RuntimeException(String.format(
					"Description node list size %d != 1",
					Description_nodeList.getLength()));
		}
		else if (Description_nodeList.getLength() != 0)
		{
			// Element property Description which is not complex (has no
			// sub-elements)
			setDescription(Description_nodeList.item(0).getTextContent());
		}
		// *** IGNORING *** Skipped back reference ExperimenterRef
		// *** IGNORING *** Skipped back reference GroupRef
		// *** IGNORING *** Skipped back reference AnnotationRef
		// *** IGNORING *** Skipped back reference Dataset_BackReference
	}

	// -- Project API methods --


	// Property
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

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
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	// Reference
	public Experimenter getLinkedExperimenter()
	{
		return experimenter;
	}

	public void linkExperimenter(Experimenter o)
	{
		experimenter = o;
	}

	public void unlinkExperimenter(Experimenter o)
	{
		if (experimenter == o)
		{
			experimenter = null;
		}
	}

	// Reference
	public Group getLinkedGroup()
	{
		return group;
	}

	public void linkGroup(Group o)
	{
		group = o;
	}

	public void unlinkGroup(Group o)
	{
		if (group == o)
		{
			group = null;
		}
	}

	// Reference which occurs more than once
	public int sizeOfLinkedAnnotationList()
	{
		return annotationList.size();
	}

	public List<Annotation> copyLinkedAnnotationList()
	{
		return new ArrayList<Annotation>(annotationList);
	}

	public Annotation getLinkedAnnotation(int index)
	{
		return annotationList.get(index);
	}

	public Annotation setLinkedAnnotation(int index, Annotation o)
	{
		return annotationList.set(index, o);
	}

	public boolean linkAnnotation(Annotation o)
	{
		o.linkProject(this);
		return annotationList.add(o);
	}

	public boolean unlinkAnnotation(Annotation o)
	{
		o.unlinkProject(this);
		return annotationList.remove(o);
	}

	// Reference which occurs more than once
	public int sizeOfLinkedDatasetList()
	{
		return dataset_BackReferenceList.size();
	}

	public List<Dataset> copyLinkedDatasetList()
	{
		return new ArrayList<Dataset>(dataset_BackReferenceList);
	}

	public Dataset getLinkedDataset(int index)
	{
		return dataset_BackReferenceList.get(index);
	}

	public Dataset setLinkedDataset(int index, Dataset o)
	{
		return dataset_BackReferenceList.set(index, o);
	}

	public boolean linkDataset(Dataset o)
	{
		return dataset_BackReferenceList.add(o);
	}

	public boolean unlinkDataset(Dataset o)
	{
		return dataset_BackReferenceList.remove(o);
	}

	public Element asXMLElement(Document document)
	{
		return asXMLElement(document, null);
	}

	protected Element asXMLElement(Document document, Element Project_element)
	{
		// Creating XML block for Project
		if (Project_element == null)
		{
			Project_element =
					document.createElementNS(NAMESPACE, "Project");
		}

		if (name != null)
		{
			// Attribute property Name
			Project_element.setAttribute("Name", name.toString());
		}
		if (id != null)
		{
			// Attribute property ID
			Project_element.setAttribute("ID", id.toString());
		}
		if (description != null)
		{
			// Element property Description which is not complex (has no
			// sub-elements)
			Element description_element = 
					document.createElementNS(NAMESPACE, "Description");
			description_element.setTextContent(description);
			Project_element.appendChild(description_element);
		}
		if (experimenter != null)
		{
			// Element property ExperimenterRef which is complex (has
			// sub-elements)
			Project_element.appendChild(experimenter.asXMLElement(document));
		}
		if (group != null)
		{
			// Element property GroupRef which is complex (has
			// sub-elements)
			Project_element.appendChild(group.asXMLElement(document));
		}
		if (annotationList != null)
		{
			// Reference property AnnotationRef
			for (Annotation o : annotationList)
			{
				Element annotationList_element = 
						document.createElementNS(NAMESPACE, "AnnotationRefRef");
				annotationList_element.setAttribute("ID", o.getID());
				Project_element.appendChild(annotationList_element);
			}
		}
		if (dataset_BackReferenceList != null)
		{
			// *** IGNORING *** Skipped back reference Dataset_BackReference
		}
		return super.asXMLElement(document, Project_element);
	}
}