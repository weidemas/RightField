/*******************************************************************************
 * Copyright (c) 2009, 2017, The University of Manchester
 *
 * Licensed under the New BSD License.
 * Please see LICENSE file that is distributed with the source code
 *  
 *******************************************************************************/
package uk.ac.manchester.cs.owl.semspreadsheets.ui.action;

import java.awt.event.ActionEvent;

import uk.ac.manchester.cs.owl.semspreadsheets.model.Range;
import uk.ac.manchester.cs.owl.semspreadsheets.model.WorkbookManager;
import uk.ac.manchester.cs.owl.semspreadsheets.ui.WorkbookFrame;

/**
 * @author Stuart Owen
 * @author Matthew Horridge
 */
@SuppressWarnings("serial")
public class InsertOntologyValuesAction extends SpreadSheetAction {

    public InsertOntologyValuesAction(WorkbookManager workbookManager, WorkbookFrame workbookFrame) {
        super("Ontology values", workbookManager, workbookFrame);
    }

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e) {
        Range range = getSpreadSheetManager().getSelectionModel().getSelectedRange();
        if(range == null) {
            return;
        }
        if(!range.isCellSelection()) {
            return;
        }
        getSpreadSheetFrame().repaint();
    }
}
