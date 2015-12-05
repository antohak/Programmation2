
import java.util.ArrayList;

public class TestsPartiels {
      
   //donnees pour tests
   public static IListeAssociative<String, Integer> l3;
   

   /**
    * Tests ajouter (C cle, ArrayList<E> listeElements, int index)
    */
   public static void testsAjouter() {
      int points = 0;
      final int total = 15;
      boolean b;
      
      afficherTitre("tests - ajouter (C cle, ArrayList<E> listeElements, int index)");
      
       try {
         System.out.println("Construction d'une liste associative vide.\n");
         l3 = new ListeAssociativeChainee();
         
         System.out.print("1. Test Ajout de cle1->[9, 3, 4, 8] a l'indice 6 (ajout)... ");
         b = l3.ajouter("cle1", liste(9, 3, 4, 8), 6);
         if (l3.toString().equals("cle1 -> [9, 3, 4, 8]")) {
            points = points + ok(1);
         } else {
            erreur("ERREUR\nAttendu :\n" + "cle1 -> [9, 3, 4, 8]" + "\nTrouve :\n" + l3.toString());
         }
         System.out.print("2. Test valeur de retour (true)... ");
         if (b) {
            points = points + ok(1);
         } else {
            erreur("ERREUR\nDevrait retourner true");
         }
         System.out.print("3. Test Ajout de cle2->[5, 5, null, 2, 1, 5] a l'indice 0 (ajout partiel)... ");
         b = l3.ajouter("cle2", liste(5, 5, null, 2, 1, 5), 0);
         if (l3.toString().equals("cle1 -> [9, 3, 4, 8]\ncle2 -> [5, 2, 1]")) {
            points = points + ok(1);
         } else {
            erreur("ERREUR\nAttendu :\n" + "cle1 -> [9, 3, 4, 8]\ncle2 -> [5, 2, 1]" 
                    + "\nTrouve :\n" + l3.toString());
         }
         System.out.print("4. Test valeur de retour (true)... ");
         if (b) {
            points = points + ok(1);
         } else {
            erreur("ERREUR\nDevrait retourner true");
         }
         System.out.print("5. Test Ajout de cle1->[8, 9] a l'indice 1 (pas ajoute)... ");
         b = l3.ajouter("cle1", liste(8, 9), 1);
         if (l3.toString().equals("cle1 -> [9, 3, 4, 8]\ncle2 -> [5, 2, 1]")) {
            points = points + ok(1);
         } else {
            erreur("ERREUR\nAttendu :\n" + "cle1 -> [9, 3, 4, 8]\ncle2 -> [5, 2, 1]" 
                    + "\nTrouve :\n" + l3.toString());
         }
         System.out.print("6. Test valeur de retour (false)... ");
         if (!b) {
            points = points + ok(1);
         } else {
            erreur("ERREUR\nDevrait retourner false");
         }
         System.out.print("7. Test Ajout de cle1->[2, 3, 0, 1] a l'indice 2 (ajout partiel)... ");
         b = l3.ajouter("cle1", liste(2, 3, 0, 1), 2);
         if (l3.toString().equals("cle1 -> [9, 3, 2, 0, 1, 4, 8]\ncle2 -> [5, 2, 1]")) {
            points = points + ok(1);
         } else {
            erreur("ERREUR\nAttendu :\n" + "cle1 -> [9, 3, 2, 0, 1, 4, 8]\ncle2 -> [5, 2, 1]" 
                    + "\nTrouve :\n" + l3.toString());
         }
         System.out.print("8. Test valeur de retour (true)... ");
         if (b) {
            points = points + ok(1);
         } else {
            erreur("ERREUR\nDevrait retourner true");
         }
         System.out.print("9. Test Ajout de cle3->[null, null, null, null] a l'indice 0 (pas ajoute)... ");
         b = l3.ajouter("cle3", liste(null, null, null, null), 0);
         if (l3.toString().equals("cle1 -> [9, 3, 2, 0, 1, 4, 8]\ncle2 -> [5, 2, 1]")) {
            points = points + ok(1);
         } else {
            erreur("ERREUR\nAttendu :\n" + "cle1 -> [9, 3, 2, 0, 1, 4, 8]\ncle2 -> [5, 2, 1]" 
                    + "\nTrouve :\n" + l3.toString());
         }
         System.out.print("10.Test valeur de retour (false)... ");
         if (!b) {
            points = points + ok(1);
         } else {
            erreur("ERREUR\nDevrait retourner false");
         }
         
         System.out.print("11.Test Ajout de cle3->null a l'indice 9 (NPException)... ");
         try {
            ArrayList liste = null;
            l3.ajouter("cle3", liste, 9);
            erreur("ERREUR\nDevrait lever une NPException");
         } catch (NullPointerException npe) {
            points = points + ok(1);
         } catch (Exception e) {
            erreur("ERREUR\nDevrait lever une NPException, leve une " + e.getClass().getSimpleName());
         }
         System.out.print("12.Test Ajout de null->[1, 2, 3] a l'indice 0 (NPException)... ");
         try {
            l3.ajouter(null, liste(1, 2, 3), 0);
            erreur("ERREUR\nDevrait lever une NPException");
         } catch (NullPointerException npe) {
            points = points + ok(1);
         } catch (Exception e) {
            erreur("ERREUR\nDevrait lever une NPException, leve une " + e.getClass().getSimpleName());
         }
         System.out.print("13.Test Ajout de cle2->[7, 2, null] a l'indice 4 (IOBException)... ");
         try {
            l3.ajouter("cle2", liste(7, 2, null), 4);
            erreur("ERREUR\nDevrait lever une IOBException");
         } catch (IndexOutOfBoundsException npe) {
            points = points + ok(1);
         } catch (Exception e) {
            erreur("ERREUR\nDevrait lever une IOBException, leve une " + e.getClass().getSimpleName());
         }
         System.out.print("14.Test Ajout de cle2->[7, 2, null] a l'indice 3 (ajout partiel)... ");
         b = l3.ajouter("cle2", liste(7, 2, null), 3);
         if (l3.toString().equals("cle1 -> [9, 3, 2, 0, 1, 4, 8]\ncle2 -> [5, 2, 1, 7]")) {
            points = points + ok(1);
         } else {
            erreur("ERREUR\nAttendu :\n" + "le1 -> [9, 3, 2, 0, 1, 4, 8]\ncle2 -> [5, 2, 1, 7]" 
                    + "\nTrouve :\n" + l3.toString());
         }
         System.out.print("15. Test valeur de retour (true)... ");
         if (b) {
            points = points + ok(1);
         } else {
            erreur("ERREUR\nDevrait retourner true");
         }
         
      } catch (Exception e) {
         exceptionInattendue(e.getClass().getSimpleName());
      }
       
      scoreInter(points, total);

   }
   
   /*******************************
    * Methodes utilitaires
    *******************************/

   /**
    * Affiche un message d'erreur
    */
   private static void erreur(String msg) {
      System.out.println(msg + "\n");
   }
   
   /**
    * Affiche un message de confirmation
    */
   private static int ok(int points) {
      System.out.println("OK");
      return points;
   }
   
   /**
    * Affiche un message d'erreur pour une exception inatttendue levee dans une
    * methode
    * @param nomMethode le nom de la methode dans laquelle l'exception a ete 
    *        levee.
    */
   private static void exceptionInattendue(String nomException) {
      System.out.println("ERREUR - " + nomException + " inattendue.");
   }
   
   private static void scoreInter(int points, int total) {
      System.out.println("\nPoints : " + points + " / " + total);
      System.out.println();
   }
   
   private static ArrayList<Integer> liste (Integer... nombres) {
      ArrayList<Integer> liste = new ArrayList<>();
      for (int i = 0 ; i < nombres.length ; i++) {
         liste.add(nombres[i]);
      }
      return liste;
   }
   
   private static void afficherTitre (String s) {
      for (int i = 0 ; i < s.length() ; i++) {
         System.out.print("-");
      }
      System.out.println("\n" + s.toUpperCase());
      for (int i = 0 ; i < s.length() ; i++) {
         System.out.print("-");
      }
      System.out.println();
   }

   /**
    * Execution des tests.
    * @param args - aucun.
    */
   public static void main (String [] args) {
      testsAjouter();
      
   }
   
}
