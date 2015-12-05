
import java.util.ArrayList;

import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class ListeAssociativeChaineeTest {
    
    ListeAssociativeChainee <String,String>chaineControle = new ListeAssociativeChainee<String, String>();
    ListeAssociativeChainee <String,String>chaineTest = new ListeAssociativeChainee<String,String>();

    @Before
    public void setUp() throws Exception {
        
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * @see ListeAssociativeChainee#equals(Object)
     */
    @Test
    public void testEquals() {
        chaineControle = new ListeAssociativeChainee<String,String>();
        chaineTest = new ListeAssociativeChainee<String,String>();
        chaineControle.ajouter("2", liste("1","2","3","4"), 0);
        chaineTest.ajouter("2", liste("1","2","3","4"), 0);
        assertEquals(true, chaineControle.equals(chaineTest));
    }

    /**
     * @see ListeAssociativeChainee#toString()
     */
    @Test
    public void testToString() {
        chaineControle = new ListeAssociativeChainee<String,String>();
        chaineControle.ajouter("2", liste("1","2","3","4"), 0);
        System.out.println("testToString " + chaineControle.toString());
    }

    /**
     * @see ListeAssociativeChainee#ajouter(Object,Object,int)
     */
    @Test
    public void testAjouter() {
        chaineControle = new ListeAssociativeChainee<String,String>();
        chaineControle.ajouter("1", "1", 0);
        System.out.println("testAjouter " + chaineControle.ajouter("1", "12", 0));
        System.out.println("testAjouter " + chaineControle.toString());
    }

    /**
     * @see ListeAssociativeChainee#ajouter(Object,Object)
     */
    @Test
    public void testAjouter1() {
        chaineControle = new ListeAssociativeChainee<String,String>();
        chaineControle.ajouter("1", "2");
        chaineControle.ajouter("2", "2");
        System.out.println("testAjouter1 " + chaineControle.ajouter("3", "3"));
        System.out.println("testAjouter1 " + chaineControle.toString());
    }

    /**
     * @see ListeAssociativeChainee#ajouter(Object,java.util.ArrayList,int)
     */
    @Test
    public void testAjouter2() {
        chaineControle = new ListeAssociativeChainee<String,String>();
        chaineControle.ajouter("2", liste("5","6","7","8"));
        System.out.println("testAjouter2 " + chaineControle.ajouter("2", liste("1","2","3","4"), 2));
        System.out.println("testAjouter2 " + chaineControle.toString());
    }

    /**
     * @see ListeAssociativeChainee#ajouter(Object,java.util.ArrayList)
     */
    @Test
    public void testAjouter3() {
        chaineControle = new ListeAssociativeChainee<String,String>();
        chaineControle.ajouter("3", liste("8","7","6","5"));
        System.out.println("testAjouter3 " + chaineControle.ajouter("3", liste("1","2","3","4")));
        System.out.println("testAjouter3 " + chaineControle.toString());
    }

    /**
     * @see ListeAssociativeChainee#supprimer(Object,int)
     */
    @Test
    public void testSupprimer() {
        chaineControle = new ListeAssociativeChainee<String,String>();
        chaineControle.ajouter("3", "element");
        chaineControle.ajouter("3", "elements");
        chaineControle.ajouter("4", "elements");
        chaineControle.ajouter("5", "elementes");
        System.out.println("supprimer obj int " + chaineControle.supprimer("3", (Object)0));
        System.out.println("supprimer obj int " + chaineControle.toString());
    }

    /**
     * @see ListeAssociativeChainee#supprimer(Object)
     */
    @Test
    public void testSupprimer1() {
        chaineControle = new ListeAssociativeChainee<String,String>();
        chaineControle.ajouter("3", "element");
        chaineControle.ajouter("4", "element");
        System.out.println("supprimer obj " + chaineControle.supprimer("3"));
        System.out.println("supprimer obj " + chaineControle.toString());
    }

    /**
     * @see ListeAssociativeChainee#supprimer(Object,Object)
     */
    @Test
    public void testSupprimer2() {
        chaineControle = new ListeAssociativeChainee<String,String>();
        chaineControle.ajouter("1", "1");
        chaineControle.ajouter("1", "2");
        chaineControle.ajouter("1", "3");
        System.out.println("supprimer obj obj " + chaineControle.supprimer("1","1"));
        System.out.println("supprimer obj obj " + chaineControle.toString());
    }

    /**
     * @see ListeAssociativeChainee#obtenirElements(Object)
     */
    @Test
    public void testObtenirElements() {
        chaineControle = new ListeAssociativeChainee<String,String>();
        chaineControle.ajouter("5", liste("1","2","3"));
        System.out.println("obtenirElements obj " + chaineControle.obtenirElements("5"));
        System.out.println("obtenirElements obj " + chaineControle.toString());
        
    }

    /**
     * @see ListeAssociativeChainee#obtenirElement(Object,int)
     */
    @Test
    public void testObtenirElement() {
        chaineControle = new ListeAssociativeChainee<String,String>();
        chaineControle.ajouter("6", liste("1","2","3"));
        System.out.println("obtenirElement obj int " + chaineControle.obtenirElement("6",1));
        System.out.println("obtenirElement obj int " + chaineControle.toString());
    }

    /**
     * @see ListeAssociativeChainee#obtenirIndex(Object,Object)
     */
    @Test
    public void testObtenirIndex() {
        chaineControle = new ListeAssociativeChainee<String,String>();
        chaineControle.ajouter("7", liste("1","2","3"));
        System.out.println("obtenirIndex obj obj " + chaineControle.obtenirIndex("7","1"));
        System.out.println("obtenirIndex obj obj " + chaineControle.toString());
    }

    /**
     * @see ListeAssociativeChainee#obtenirCles(Object)
     */
    @Test
    public void testObtenirCles() {
        chaineControle = new ListeAssociativeChainee<String,String>();
        chaineControle.ajouter("1", "element");
        chaineControle.ajouter("2", "elements");
        chaineControle.ajouter("3", "element");
        System.out.println("obtenirCles obj " + chaineControle.obtenirCles("element"));
    }

    /**
     * @see ListeAssociativeChainee#obtenirCles()
     */
    @Test
    public void testObtenirCles1() {
        chaineControle = new ListeAssociativeChainee<String,String>();
        chaineControle.ajouter("1", "element");
        chaineControle.ajouter("2", "elements");
        chaineControle.ajouter("3", "element");
        System.out.println("obtenirCles " + chaineControle.obtenirCles());
    }

    /**
     * @see ListeAssociativeChainee#modifier(Object,Object,int)
     */
    @Test
    public void testModifier() {
        chaineControle = new ListeAssociativeChainee<String,String>();
        chaineControle.ajouter("2","ta");
        System.out.println("modifier obj obj int " + chaineControle.modifier("2", "mod", 0));
        System.out.println("modifier obj obj int " + chaineControle.toString());
    }

    /**
     * @see ListeAssociativeChainee#modifier(Object,java.util.ArrayList)
     */
    @Test
    public void testModifier1() {
        chaineControle = new ListeAssociativeChainee<String,String>();
        chaineControle.ajouter("2", liste("1","2","3"));
        System.out.println("modifier obj array " + chaineControle.modifier("2", liste("4","5","6")));
        System.out.println("modifier obj array " + chaineControle.toString());
    }

    /**
     * @see ListeAssociativeChainee#vider()
     */
    @Test
    public void testVider() {
        chaineControle = new ListeAssociativeChainee<String,String>();
        chaineControle.ajouter("cle", "element");
        chaineControle.vider();
        System.out.println("vider");
        System.out.println("vider " + chaineControle.toString());
    }

    /**
     * @see ListeAssociativeChainee#estVide()
     */
    @Test
    public void testEstVide() {
        chaineControle = new ListeAssociativeChainee<String,String>();
        chaineControle.ajouter("1", "element");
        chaineControle.ajouter("2", "elements");
        chaineControle.ajouter("3", "element");
        System.out.println("estVide " + chaineControle.estVide());
        System.out.println("estVide " + chaineControle.toString());
    }

    /**
     * @see ListeAssociativeChainee#taille()
     */
    @Test
    public void testTaille() {
        chaineControle = new ListeAssociativeChainee<String,String>();
        chaineControle.ajouter("1", "1");
        chaineControle.ajouter("2", "1");
        chaineControle.ajouter("3", "1");
        System.out.println("taille " + chaineControle.taille());
        System.out.println("taille " + chaineControle.toString());
    }

    /**
     * @see ListeAssociativeChainee#cleExiste(Object)
     */
    @Test
    public void testCleExiste() {
        chaineControle = new ListeAssociativeChainee<String,String>();
        chaineControle.ajouter("1", "element");
        System.out.println("cleExiste " + chaineControle.cleExiste("1"));
        System.out.println("cleExiste " + chaineControle.toString());
    }
    
    private static ArrayList<Integer> liste (Integer... nombres) {
       ArrayList<Integer> liste = new ArrayList<>();
       for (int i = 0 ; i < nombres.length ; i++) {
          liste.add(nombres[i]);
       }
       return liste;
    }
    
    private static ArrayList<String> liste (String... nombres) {
       ArrayList<String> liste = new ArrayList<>();
       for (int i = 0 ; i < nombres.length ; i++) {
          liste.add(nombres[i]);
       }
       return liste;
    }
}
