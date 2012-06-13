/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * ManchesterQueryService - ManchesterQueryService
 *  
 * May 23, 2012, 2:40:00 PM
 *
 * BioModels.net 
 *
 * EMBL-EBI Cambridge 
 *
 * For Alice - Zhao Yangyang - https://plus.google.com/u/0/103939286317705041310/about
 *
 *
 */
package manchesterqueryservice;


import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxEditorParser;
import org.semanticweb.owlapi.expression.OWLEntityChecker;
import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.expression.ShortFormEntityChecker;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.BidirectionalShortFormProvider;
import org.semanticweb.owlapi.util.BidirectionalShortFormProviderAdapter;
import org.semanticweb.owlapi.util.SimpleShortFormProvider;

import java.util.Set;






public class ManchesterQueryService {
    private OWLOntologyManager owlOntologyManager = null;
    private OWLOntology queryOntology = null;
    private IRI docIRI = null;
    
    
    public ManchesterQueryService(IRI docIRI, OWLOntologyManager owlOntologyManager) {
        this.owlOntologyManager = owlOntologyManager;
        this.docIRI = docIRI;
        try {
            queryOntology = owlOntologyManager.loadOntology(docIRI);
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }
    }

    
    public OWLClassExpression runManchesterQuery(String manchesterQuery){
        ManchesterOWLSyntaxEditorParser parser = new ManchesterOWLSyntaxEditorParser(owlOntologyManager.getOWLDataFactory(), manchesterQuery);
        parser.setDefaultOntology(this.queryOntology);
        Set<OWLOntology> importsClosure = queryOntology.getImportsClosure();
        BidirectionalShortFormProvider bidiShortFormProvider = new BidirectionalShortFormProviderAdapter(owlOntologyManager, importsClosure, new SimpleShortFormProvider());
        OWLEntityChecker entityChecker = new ShortFormEntityChecker(bidiShortFormProvider);
        parser.setOWLEntityChecker(entityChecker);

        OWLClassExpression classExp=null;
        try {
            classExp = parser.parseClassExpression();
        } catch (ParserException e) {
            e.printStackTrace();
        }
        
        
      
        return classExp;
    }
}
