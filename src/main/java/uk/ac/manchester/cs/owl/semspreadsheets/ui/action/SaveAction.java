/*******************************************************************************
 * Copyright (c) 2009, 2017, The University of Manchester
 *
 * Licensed under the New BSD License.
 * Please see LICENSE file that is distributed with the source code
 *  
 *******************************************************************************/
package uk.ac.manchester.cs.owl.semspreadsheets.ui.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import uk.ac.manchester.cs.owl.semspreadsheets.ui.ErrorHandler;
import uk.ac.manchester.cs.owl.semspreadsheets.ui.WorkbookFrame;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 07-Nov-2009
 */
@SuppressWarnings("serial")
public class SaveAction extends WorkbookFrameAction {

    public SaveAction(WorkbookFrame workbookFrame) {
        super("Save spreadsheet", workbookFrame);
        setAcceleratorKey(KeyEvent.VK_S);
    }

    /**
     * Invoked when an action occurs.
     */
    public void actionPerformed(ActionEvent e) {
        try {
            getWorkbookFrame().saveWorkbook();
        }
        catch (Exception e1) {
            ErrorHandler.getErrorHandler().handleError(e1);
        }
    }
}
