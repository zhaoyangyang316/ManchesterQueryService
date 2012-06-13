/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * ManchesterQueryService - Test
 *  
 * May 23, 2012, 2:37:24 PM
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



import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.IRI;
import java.io.File;
import java.util.Scanner;
import org.semanticweb.owlapi.model.OWLClassExpression;

public class Test {
        public static ManchesterQueryService queryServiece;
        public static void main(String[] args) { 
            Runtime rt = Runtime.getRuntime();
            File kbfile = new File("/Users/Crystal/codes/java/ManchesterQueryService/resources/DOID_el.owl");
            OWLOntologyManager owlManager = OWLManager.createOWLOntologyManager();
            IRI docIRI = IRI.create(kbfile);
            Scanner scanner=new Scanner(System.in);  
            //String testURL=scanner.next();
            
            Test.queryServiece = new ManchesterQueryService(docIRI,owlManager);
            int j= 0;
            Test.unitTest("DOID_11337");
            long startTime = System.currentTimeMillis();
            long lastQueryTime = System.currentTimeMillis();
            for(int i = 0; i < 50; i++){
                Test.unitTest("DOID_11337");
                if(j == 10){
                    j = 0;
                    long start = System.currentTimeMillis();
                    rt.gc();
                    long timeGC = System.currentTimeMillis() - start;
                    System.out.println("GC time:"+ timeGC);
                }
                //rt.gc();
                double time = System.currentTimeMillis() - lastQueryTime ;
                lastQueryTime = System.currentTimeMillis();
                //System.out.println("lalalal"+ classExp.toString());
                System.out.println(rt.totalMemory() + "    NO." +i+"   Time:"+time);
                j++;
                

            }
            long endTime = System.currentTimeMillis();
            double responseTime = (endTime - startTime)/1000.0;
            System.out.println(responseTime);
        }
        
        public static void unitTest(String testQuery){
            
            OWLClassExpression classExp =  queryServiece.runManchesterQuery(testQuery);
            

        }
        
        public static String owlClassExpressionToString(OWLClassExpression exp){
            return exp.asOWLClass().toStringID();
        }
}
