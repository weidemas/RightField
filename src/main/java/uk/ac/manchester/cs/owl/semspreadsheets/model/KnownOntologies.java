/*******************************************************************************
 * Copyright (c) 2009, 2017, The University of Manchester
 *
 * Licensed under the New BSD License.
 * Please see LICENSE file that is distributed with the source code
 *  
 *******************************************************************************/
package uk.ac.manchester.cs.owl.semspreadsheets.model;

import org.semanticweb.owlapi.model.IRI;

/**
 * Place for holding IRI's for known ontologies
 * 
 * @author Stuart Owen
 *
 */
public class KnownOntologies {
	
	//the protege ontology appears when some ontologies are loaded, and we want to ignore it
	public static String PROTEGE_ONTOLOGY = "http://protege.stanford.edu/plugins/owl/protege";
	public static IRI PROTEGE_ONTOLOGY_IRI = IRI.create(PROTEGE_ONTOLOGY);
}
