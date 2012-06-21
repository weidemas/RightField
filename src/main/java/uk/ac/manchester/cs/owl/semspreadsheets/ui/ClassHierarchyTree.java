/*******************************************************************************
 * Copyright (c) 2009-2012, University of Manchester
 * 
 * Licensed under the New BSD License. 
 * Please see LICENSE file that is distributed with the source code
 ******************************************************************************/
package uk.ac.manchester.cs.owl.semspreadsheets.ui;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.apache.log4j.Logger;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLOntology;

import uk.ac.manchester.cs.owl.semspreadsheets.model.WorkbookManager;

/**
 * @author Matthew Horridge
 * @author Stuart Owen
 *
 */
@SuppressWarnings("serial")
public class ClassHierarchyTree extends JTree {
	
	private static Logger logger = Logger.getLogger(ClassHierarchyTree.class);

    private WorkbookManager workbookManager;    
    
    private OWLOntology ontology;	

    public ClassHierarchyTree(final WorkbookManager manager, OWLOntology ontology) {
        super(new ClassHierarchyTreeModel(manager.getOntologyManager(),ontology));
		this.ontology = ontology;
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);        
        this.workbookManager = manager;
        
        addTreeSelectionListener(new TreeSelectionListener() {
            @Override
			public void valueChanged(TreeSelectionEvent e) {
                previewSelectedClass();
            }
        });
        
        
        setCellRenderer(new OntologyCellRenderer(workbookManager.getOntologyManager()));
    }

    public void updateModel() {
    	setModel(new ClassHierarchyTreeModel(workbookManager.getOntologyManager(),ontology));
    }
    
    public ClassHierarchyTreeModel getClassHierarchyTreeModel() {
        return (ClassHierarchyTreeModel) super.getModel();
    }        

	private void previewSelectedClass() {
		logger.debug("In previewSelectedClass");
				
		TreePath[] selectedPaths = getSelectionPaths();
		if (selectedPaths == null) {
			return;
		}
		Set<OWLEntity> selectedEntities = new HashSet<OWLEntity>();
		
		for (TreePath path : selectedPaths) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
					.getLastPathComponent();
			if (node instanceof ClassHierarchyNode) {
				selectedEntities.addAll(((ClassHierarchyNode) node)
						.getOWLClasses());
			} else {
				selectedEntities
						.add((OWLNamedIndividual) ((ClassHierarchyIndividualNode) node)
								.getUserObject());
			}
		}
		OWLEntity selectedEntity = selectedEntities.iterator().next();
		if (!workbookManager.getEntitySelectionModel().getSelection().equals(selectedEntity)) {
			workbookManager.getEntitySelectionModel().setSelection(selectedEntity);
		}			
		workbookManager.previewValidation();		
	}    

    public boolean containsClass(OWLClass cls) {
    	return !getClassHierarchyTreeModel().getTreePathsForEntity(cls).isEmpty();
    }
    
    public void setSelectedClass(OWLClass cls) {
        Collection<TreePath> treePaths = getClassHierarchyTreeModel().getTreePathsForEntity(cls);        
        clearSelection();
        if (!treePaths.isEmpty()) {
        	TreePath path = treePaths.iterator().next();
        	addSelectionPath(path);
            scrollPathToVisible(path);        	
        }              
    }         
}
