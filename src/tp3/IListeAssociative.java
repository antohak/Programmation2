
import java.util.ArrayList;

/**
 * Une liste associative permet d'associer des cles (de type C) a des listes 
 * d'elements E (chaque element de la liste etant de type E). Une cle peut donc etre
 * associee a plusieurs elements (regroupes dans une liste) :
 * 
 * cle1 -> [ e1, e2, e3... ]
 * cle2 -> [ e4, e5, ... ]
 * ...
 * 
 * on appelle le couple cle -> [liste elements] une association.
 * 
 * Chaque cle de cette liste associative est unique et non null.
 * 
 * La liste des elements associee a une cle :
 *   - ne contient pas d'elements null, ni de doublons.
 *   - est une liste ordonnee c.-a-d. dans laquelle chaque element a une 
 *     position (un index) : le premier element est a la position 0, 
 *     le deuxieme, a la position 1 et ainsi de suite.
 * 
 * Deux cles differentes peuvent se voir associer les mêmes elements :
 * Ex: 
 *    cle1 -> e1, e2, e3, e4, e5
 *    cle2 -> e1, e3, e6, e7
 *    ...
 *
 * A tout moment, si une cle existe, elle doit etre associee a une liste d'au 
 * moins un element (liste non vide). Lorsqu'une liste d'elements associee a 
 * une cle devient vide, cette cle, et sa liste vide associee, sont retirees de 
 * cette liste associative.
 * 
 * Notez que plusieurs methodes de cette classe utilisent (directement ou indirectement) 
 * la methode equals des objets de type C et V pour déterminer, par exemple, si une 
 * cle existe ou si un element existe. La methode equals devrait donc etre redefinie 
 * dans les types C et V, pour assurer le bon fonctionnement de ces methodes.
 * 
 * @author Melanie Lord
 * @param <C> le type des cles de cette liste associative.
 * @param <E> le type des elements dans les listes d'elements associees aux 
 *            cles.
 */
public interface IListeAssociative <C, E> {
   
   /**
    * Associe l'element donnee a la cle donnee.
    * Si la cle donnee existe : si l'element donne n'est pas deja associe a 
    * cette cle, il est ajoute a l'index donne dans la liste des elements 
    * associee a cette cle (en poussant les elements deja presents vers la 
    * gauche). Apres l'ajout, l'index des elements qui etaient a une position 
    * >= a index (avant l'ajout) a augmente de 1.
    * 
    * Si la cle donnee n'existe pas : la cle est ajoutee en lui associant une 
    * nouvelle liste, contenant seulement l'element donne, sans tenir compte
    * de l'index donne.
    * 
    * @param cle la cle d'association pour l'element donne.
    * @param element l'element a associer a cle.
    * @param index la position a laquelle ajouter l'element dans la liste des 
    *              elements deja associee a la cle donnee, s'il y a lieu.
    * @return true si l'element a ete ajoutee, faux sinon.
    * @throws IndexOutOfBoundsException si cle existe et si index n'est pas une 
    *         position valide dans la liste des elements deja associee a cle.
    *         Un index valide dans la liste doit se trouver entre 
    *         0 et liste.size() inclusivement.
    * @throws NullPointerException si cle ou element est null.
    */
   boolean ajouter (C cle, E element, int index);
   
   /**
    * Associe l'element donne a la cle donnee. 
    * Si la cle donnee existe, l'element donne est ajoute a la fin de la liste 
    * des elements deja associes a cette cle, si et seulement si l'element n'est 
    * pas deja dans cette liste.
    * 
    * Si la cle donnee n'existe pas, la cle est ajoutee en lui associant une 
    * nouvelle liste, contenant seulement l'element donne.
    * 
    * @param cle la cle d'association pour l'element donne.
    * @param element l'element a associer a cle.
    * @return true si l'element a ete ajoutee, faux sinon.
    * @throws NullPointerException si cle ou element est null.
    */
   boolean ajouter (C cle, E element);
   
   /**
    * Associe la cle donnee a la liste des elements donnee. 
    * Si la cle donnee existe : la liste des elements donnee est ajoutee a 
    * l'index donne dans la liste des elements deja associee a cette cle, 
    * MAIS seuls les elements NON NULL ET qui ne sont pas deja associes a cette 
    * cle sont ajoutes. 
    * 
    * Si la cle donnee n'existe pas : la cle est ajoutee en lui associant une 
    * nouvelle liste, contenant les elements NON NULL de la liste des elements
    * donnee, sans tenir compte de l'index donne. Si tous les elements de la 
    * listeElements sont null, l'association n'est pas ajoute.
    * 
    * En tout temps, l'ordre des elements dans la liste des elements donnee doit 
    * etre respecte lors de l'ajout a cette liste associative.
    * 
    * @param cle la cle d'association pour listeElements.
    * @param listeElements les elements a associer a cle.
    * @param index la position a laquelle ajouter la liste des elements donnee
    *              dans la liste de elements deja associee a la cle donnee, 
    *              s'il y a lieu.
    * @return true si au moins un des elements de listeElements a ete ajoutee a  
    *         cette liste associative, false sinon.
    * @throws NullPointerException si cle est null ou listeElements est null.
    * @throws IndexOutOfBoundsException si cle existe et si index n'est pas une 
    *         position valide dans la liste des elements deja associee a cle.
    *         Un index valide dans la liste doit se trouver entre 
    *         0 et liste.size() inclusivement.
    */
   boolean ajouter (C cle, ArrayList<E> listeElements, int index);
   
   /**
    * Associe la cle donnee a la liste des elements donnee. 
    * Si la cle donnee existe : la liste des elements donnee est ajoutee a la fin 
    * de la liste des elements deja associes a cette cle, MAIS seuls les elements 
    * NON NULL ET qui ne sont pas deja associes a cette cle sont ajoutes. 
    *  
    * Si la cle donnee n'existe pas : la cle est ajoutee en lui associant une 
    * nouvelle liste, contenant les elements NON NULL de la liste des elements
    * donnee. Si tous les elements de la listeElements sont null, 
    * l'association n'est pas ajoute.
    * 
    * En tout temps, l'ordre des elements dans la liste des elements donnee doit 
    * etre respecte lors de l'ajout a cette liste associative.
    * 
    * @param cle la cle d'association.
    * @param listeElements les elements a associer a cle.
    * @return true si au moins un des elements de listeElements a ete ajoute a  
    *         cette liste associative, false sinon.
    * @throws NullPointerException si la cle est null ou si listeElements est 
    *         null.
    */
   boolean ajouter (C cle, ArrayList<E> listeElements);

   /**
    * Supprime l'element a l'index donne dans la liste des elements associee a 
    * la cle donnee. Si la cle donnee n'existe pas, aucun retrait n'est effectue.
    * 
    * Si apres un retrait, la liste des elements associee a la cle donnee est
    * vide, la cle est aussi supprimee de cette liste associative.
    * 
    * @param cle la cle dont on veut supprimer un des elements 
    *        qui lui sont associes.
    * @param index l'index de l'element a supprimer dans la liste des elements
    *        associee a la cle donnee.
    * @return l'element supprime ou null si cle n'existe pas.
    * @throws NullPointerException si cle est null.
    * @throws IndexOutOfBoundsException si cle existe et si l'index donne est 
    *         un index invalide dans la liste des elements associee a la cle 
    *         donnee. Un index valide dans la liste doit se trouver entre 
    *         0 et (liste.size() - 1) inclusivement.
    */
   E supprimer (C cle, int index);

   /**
    * Supprime la cle donnee et tous ses elements associes de cette liste
    * associative.
    * 
    * @param cle la cle de l'association a supprimer de cette liste associative.
    * @return la liste des elements associee a la cle donnee, avant le retrait 
    *         ou null si la cle donnee n'existe pas dans cette liste associative.
    * @throws NullPointerException si cle est null.
    */
   ArrayList<E> supprimer (C cle);
   
   /**
    * Supprime l'element donne de la liste des elements associee a la cle
    * donnee. Si la cle donnee n'existe pas ou si l'element donne n'est pas
    * un element associe a la cle donnee, aucun retrait n'est effectue.
    * 
    * Si apres un retrait, la liste des elements associee a la cle donnee est
    * vide, la cle est aussi supprimee de cette liste associative.
    * 
    * @param cle la cle dont on veut supprimer un des elements qui lui sont
    *            associes.
    * @param element l'element a supprimer dans la liste des elements associee
    *        a la cle donnee.
    * @return true si l'element a ete supprime, false sinon.
    * @throws NullPointerException si cle est null ou element est null.
    */
   boolean supprimer (C cle, E element);
   
   /**
    * Permet d'obtenir la liste des elements associee a la cle donnee.
    * @param cle la cle pour laquelle on veut obtenir les elements.
    * @return la liste des elements associee a la cle donnee ou null si la cle
    *         n'existe pas.
    * @throws NullPointerException si cle est null.
    */
   ArrayList<E> obtenirElements (C cle);
   
   /**
    * Permet d'obtenir l'element se trouvant a l'index donne dans la liste 
    * des elements associee a la cle donnee.
    * @param cle la cle pour laquelle on veut obtenir un element qui lui est 
    *            associe.
    * @param index l'index de l'element a retourner dans la liste des elements
    *              associee a la cle donnee.
    * @return l'element a l'index donne dans la liste des elements associee 
    *         a cle ou null si cle n'existe pas.
    * @throws NullPointerException si cle est null.
    * @throws IndexOutOfBoundsException si cle existe et si l'index donne est 
    *         un index invalide dans la liste des elements associee a la cle 
    *         donnee. Un index valide dans la liste doit se trouver entre 
    *         0 et (liste.size() - 1) inclusivement.
    */
   E obtenirElement (C cle, int index);
   
   /**
    * Permet d'obtenir l'index de l'element donne dans la liste des elements
    * associee a la cle donnee. 
    * @param cle la cle d'association dont on veut obtenir l'index d'un des
    *        elements qui lui sont associes.
    * @param element l'element dont on veut obtenir l'index dans la liste 
    *                des elements associee a la cle donnee.
    * @return l'index de l'element donnee dans la liste des elements 
    *         associee a cle ou -1 si l'element n'est pas dans cette liste ou 
    *         si la cle donnee n'existe pas.
    * @throws NullPointerException si cle est null ou si element est null.
    */
   int obtenirIndex (C cle, E element);
   
   /**
    * Permet d'obtenir une NOUVELLE LISTE contenant toutes les cles de cette 
    * liste associative qui ont, parmi leurs elements associees, l'element donne.
    * 
    * @param element l'element dont on cherche toutes les cles d'association.
    * @return la liste des cles d'association pour l'element donne (la liste
    *         retournee est vide si aucune cle n'est trouvee).
    * @throws NullPointerException si element est null.
    */
   ArrayList<C> obtenirCles (E element);
   
   /**
    * Permet d'obtenir une NOUVELLE liste de toutes les cles de cette liste 
    * associative. 
    *
    * @return la liste de toutes les cles de cette liste associative. 
    *         (La liste retournee est vide si cette liste associative
    *         est vide).
    */
   ArrayList<C> obtenirCles ();
   
   /**
    * Permet de modifier l'element a l'index donne dans la liste des elements
    * associee a la cle donnee par le nouvelElement donne. Si la cle donnee
    * n'existe pas ou si elle existe mais que l'element donnee est deja associe 
    * a cette cle, la modification n'est pas effectuee.
    *
    * @param cle la cle dont on veut modifier un element associe.
    * @param nouvelElement l'element qui remplace celui deja present dans 
    *                      la liste des elements associee a cle, a l'index
    *                      donne.
    * @param index la position de l'element a modifier dans la liste des 
    *              elements associee a la cle donnee.
    * @return true si la modification a ete effectuee, false sinon.
    * @throws NullPointerException si cle est null ou nouvelElement est null.
    * @throws IndexOutOfBoundsException si cle existe et si l'index donne est 
    *         un index invalide dans la liste des elements associee a la cle 
    *         donnee. Un index valide dans la liste doit se trouver entre 
    *         0 et (liste.size() - 1) inclusivement.
    */
   boolean modifier (C cle, E nouvelElement, int index);
   
   /**
    * Permet de remplacer la liste des elements associee a la cle donnee par 
    * la liste donnee nouveauxElements. Si la cle donnee n'existe pas, la 
    * modification n'est pas effectuee.
    * 
    * Seul les elements de nouveauxElements qui ne sont pas null
    * et qui ne sont pas des doublons sont associes a la cle donnee.
    * Si tous les elements de nouveauxElements sont null, la modification 
    * n'est pas effectuee.
    * 
    * L'ordre des elements (non null et non doublons) dans nouveauxElements 
    * n'est pas modifie lors du remplacement.
    * 
    * @param cle la cle dont on veut modifier les elements associes.
    * @param nouveauxElements la nouvelle liste d'elements a associer a la 
    *                         cle donnee.
    * @return true si la modification a ete effectuee, false sinon.
    * @throws NullPointerException si cle est null ou nouveauxElements est null.
    */
   boolean modifier (C cle, ArrayList<E> nouveauxElements);
   
   /**
    * Retire toutes les cles et leurs elements associees de cette liste
    * associative. Apres l'appel de cette methode, estVide() retourne true.
    */
   void vider ();
   
   /**
    * Verifie si cette liste associative est vide (ne contient aucune cle).
    * 
    * @return true si cette liste associative est vide, false sinon.
    */
   boolean estVide ();
   
   /**
    * Permet d'obtenir la taille de cette liste associative. 
    * La taille correspond au nombre de cles dans cette liste associative.
    * 
    * @return la taille de cette liste associative.
    */
   int taille ();
   
   /**
    * Verifie si la cle donnee existe dans cette liste associative.
    * 
    * @param cle la cle dont on teste l'existence.
    * @return true si la cle existe, false sinon.
    * @throws NullPointerException si cle est null.
    */
   boolean cleExiste(C cle);
   
}
