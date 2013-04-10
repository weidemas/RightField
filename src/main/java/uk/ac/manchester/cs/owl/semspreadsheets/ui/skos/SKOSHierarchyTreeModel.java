/*******************************************************************************
 * Copyright (c) 2009-2013, University of Manchester
 *  
 * Licensed under the New BSD License.
 * Please see LICENSE file that is distributed with the source code
 ******************************************************************************/

package uk.ac.manchester.cs.owl.semspreadsheets.ui.skos;

import java.util.Set;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.log4j.Logger;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.skos.SKOSConcept;
import org.semanticweb.skos.SKOSDataset;

import uk.ac.manchester.cs.owl.semspreadsheets.model.OntologyManager;
import uk.ac.manchester.cs.owl.semspreadsheets.model.skos.SKOSHierarchyReader;
import uk.ac.manchester.cs.owl.semspreadsheets.ui.ClassHierarchyTreeModel;

public class SKOSHierarchyTreeModel extends ClassHierarchyTreeModel {
	
	private static final Logger logger = Logger.getLogger(SKOSHierarchyTreeModel.class);
	private DefaultMutableTreeNode rootNode;
	private SKOSHierarchyReader skosReader;
	
	public SKOSHierarchyTreeModel(OntologyManager ontologyManager,OWLOntology ontology) {
		super(ontologyManager,ontology);
		logger.debug("Using SKOSHierarchyTreeModel for "+ontology.getOntologyID().getOntologyIRI());
		
		
	}

	@Override
	protected void buildTreeModel() {
		rootNode = new DefaultMutableTreeNode("Top");
		skosReader = new SKOSHierarchyReader(getOntologyManager(), getOntology());
		SKOSDataset dataset = skosReader.getDataset(getOntology());
		Set<SKOSConcept> topConcepts = skosReader.getTopConcepts();
		for (SKOSConcept concept : topConcepts) {
			SKOSHierarchyTreeNode node = new SKOSHierarchyTreeNode(concept,dataset);
			rootNode.add(node);
			buildChildren(node,dataset);
		}
		
	}		

	@Override
	public Object getRoot() {
		return rootNode;
	}
	
	private void buildChildren(SKOSHierarchyTreeNode node,SKOSDataset dataset) {
		SKOSConcept concept = node.getSKOSConcept();
		for (SKOSConcept c : skosReader.getNarrowerThan(concept)) {
			SKOSHierarchyTreeNode newNode = new SKOSHierarchyTreeNode(c,dataset);
			node.add(newNode);
			buildChildren(newNode,dataset);
		}
	}	
}
