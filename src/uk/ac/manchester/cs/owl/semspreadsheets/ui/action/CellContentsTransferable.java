package uk.ac.manchester.cs.owl.semspreadsheets.ui.action;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Collection;

import uk.ac.manchester.cs.owl.semspreadsheets.model.OntologyTermValidation;
import uk.ac.manchester.cs.owl.semspreadsheets.model.OntologyTermValidationDescriptor;
/**
 * This is a Transferable implemenation to support the copy and paste of both cell text values, and the OntologyTermValidationDescriptor.
 * 
 * It will allow the copy/paste of both from within RightField, and also text values between RightField and other application.
 * 
 * @author Stuart Owen
 *
 */
public class CellContentsTransferable implements	Transferable {
	
	/**
	 * The DataFlavor for handling the collection of OntologyTermValidation's and the text value of the cell.
	 */
	public static DataFlavor dataFlavour = new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType
			+ OntologyTermValidationDescriptor.class.getCanonicalName(),"OntologyTermValidations");
	
	
	private final String textValue;


	private final OntologyTermValidationDescriptor descriptor;
	
	public CellContentsTransferable(String textValue,OntologyTermValidationDescriptor descriptor) {
		super();
		this.textValue = textValue;
		this.descriptor = descriptor;
				
		this.dataFlavour=new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType
				+ OntologyTermValidationDescriptor.class.getCanonicalName(),"OntologyTermValidations");		
	}

	/**
	 * This will provide either a single String object if the flavour is DataFlavor.stringFlavor<br>
	 * or an array that contains [textValue,OntologyTermValidationDescriptor] if the flavour is <br>
	 * <br>
	 * Note that the OntologyTermValidationDescriptor may be null if the copied cell did not have any validation applied.
	 * {@link #dataFlavour}
	 */
	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		if (DataFlavor.stringFlavor.equals(flavor)) {
			return textValue;
		}
		else {
			return new Object[]{textValue,descriptor};
		}		
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[]{dataFlavour,DataFlavor.stringFlavor};
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return CellContentsTransferable.dataFlavour.equals(flavor) || DataFlavor.stringFlavor.equals(flavor);
	}

}
