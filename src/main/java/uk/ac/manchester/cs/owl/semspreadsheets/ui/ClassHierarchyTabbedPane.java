package uk.ac.manchester.cs.owl.semspreadsheets.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.semanticweb.owlapi.model.OWLOntology;

import uk.ac.manchester.cs.owl.semspreadsheets.model.KnownOntologies;
import uk.ac.manchester.cs.owl.semspreadsheets.model.WorkbookManager;

/**
 * Pane that contains an individual tab for each Ontology. This class handles the displaying and creation of the tabs, and listens to events
 * for when new ontologies are loaded.
 * 
 * @author Stuart Owen
 *
 */

@SuppressWarnings("serial")
public class ClassHierarchyTabbedPane extends JTabbedPane {	
	
	private final WorkbookManager workbookManager;	
	
	private List<OWLOntology> knownOntologies = new ArrayList<OWLOntology>();


	public ClassHierarchyTabbedPane(WorkbookManager workbookManager) {
		super();
		this.workbookManager = workbookManager;		
		
		workbookManager.addListener(new WorkbookManagerListener() {
			
			@Override
			public void workbookLoaded(WorkbookManagerEvent event) {
				// TODO Auto-generated method stub				
			}
			
			@Override
			public void workbookChanged(WorkbookManagerEvent event) {
				// TODO Auto-generated method stub				
			}
			
			@Override
			public void validationAppliedOrCancelled() {
				// TODO Auto-generated method stub				
			}
			
			@Override
			public void ontologiesChanged(WorkbookManagerEvent event) {
				updateTabs();			
			}
		});
		
	}
	
	public int tabIndexForOntology(OWLOntology ontology) {
		return indexOfTab(tabTitle(ontology));
	}
	
	public String tabTitle(OWLOntology ontology) {
		return ontology.getOntologyID().getOntologyIRI().getFragment();
	}
	
	private void updateTabs() {		
		for (OWLOntology ontology : getLoadedOntologies()) {
			if (!ontology.getOntologyID().getOntologyIRI().toString().equals(KnownOntologies.PROTEGE_ONTOLOGY)) {
				if (!knownOntologies.contains(ontology)) {				
					createTab(ontology);
					knownOntologies.add(ontology);
				}	
			}					
		}		
	}

	private void createTab(OWLOntology ontology) {
		ClassHierarchyTree tree = new ClassHierarchyTree(getWorkbookManager(),ontology,this);
		tree.updateModel();
		JScrollPane sp = new JScrollPane(tree);
		sp.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));				
		addTab(tabTitle(ontology), sp);
	}		
	
	private Set<OWLOntology> getLoadedOntologies() {
		return getWorkbookManager().getLoadedOntologies();
	}	
			
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font ontologyNotLoadedFont = new Font("Lucida Grande", Font.BOLD, 14);
        if (getTabCount() == 0) {
            Color oldColor = g.getColor();
            g.setColor(Color.LIGHT_GRAY);
            Font oldFont = g.getFont();
            g.setFont(ontologyNotLoadedFont);
            String msg = "No ontologies loaded";
            Rectangle bounds = g.getFontMetrics().getStringBounds(msg, g).getBounds();
            g.drawString(msg, getWidth() / 2 - bounds.width / 2, getHeight() / 2 - g.getFontMetrics().getAscent());
            g.setFont(oldFont);
            g.setColor(oldColor);
        }
    }

	public WorkbookManager getWorkbookManager() {
		return workbookManager;
	}

}
