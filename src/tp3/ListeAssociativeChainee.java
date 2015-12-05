/**
 * Code Permanent : MICP17049305
 * Nom : Michel
 * Prénom : Pierrick
 * Sigle du cours : INF2120
 * Groupe : 30
 * Nom du professeur : Mélanie Lord
 *
 * @author Pierrick Michel
 *          LePmnin@Gmail.Com
 *
 * @description : Travail Pratique 02
 *                  Implémentation de l'interface IListeAssociative
 *                  en utilisant liste de maillons comme structure de données
 *
 * @class : ListeAssociative
 * @classDescription : Classe implémentant l'interface à implémenter
 * @version 1.0
 */

import java.util.ArrayList;

/**
 * @param <E> Les objets-maillons elements
 * @param <C> Les objets-maillons cles
 */
public class ListeAssociativeChainee<C, E> implements IListeAssociative {
    
    Maillon<C> cles;
    Maillon<Maillon<E>> elements;
    int nbrCles;
    
    ListeAssociativeChainee(){
        cles = new Maillon<C>(null);
        elements = new Maillon<Maillon<E>>(new Maillon<E>(null));
        nbrCles = 0;
    }
        
    /**
     * Méthode Equals
     * Comparaison des deux objets ListeAssociative.
     *
     * instance of IListeAssociative | même nombre de clés | même taille | Toutes les clés ont les mêmes éléments
     *
     * @param autreListeAssociativeChainee L'objet à comparer
     * @return true si les deux objets ListeAssociative sont identiques
     */
    @SuppressWarnings("unchecked")
    public boolean equals(Object autreListeAssociativeChainee) {
        boolean controlBool = true;
        
        if(autreListeAssociativeChainee == null){
            controlBool = false;
        } else if(!(autreListeAssociativeChainee instanceof IListeAssociative)){
            controlBool = false;
        } else{
            IListeAssociative test = (IListeAssociative) autreListeAssociativeChainee;
            
            if(test.taille() != this.taille()){
                controlBool = false;
            } else{
                controlBool = true;
                
                Maillon<C> boucleCles = cles;
                Maillon<Maillon<E>> boucleElements = elements;
                Maillon<E> boucleElement;
                
                while(boucleElements != null){
                    boucleElement = boucleElements.info();
                    while(boucleElement != null){
                        if(test.obtenirIndex(boucleCles.info(), boucleElement.info()) != this.obtenirIndex(boucleCles.info(), boucleElement.info())){
                            controlBool = false;
                        }
                        
                        if(boucleElement.suivant() == null){
                            boucleElement = null;
                        }else{
                            boucleElement = boucleElement.suivant();
                        }
                    }
                    
                    if(boucleElements.suivant() == null){
                        boucleElements = null;
                    }
                    else{
                        boucleCles = boucleCles.suivant();
                        boucleElements = boucleElements.suivant();
                    }
                }
            }
        }
        
        return controlBool;
    }
    
    /**
     * Permet d'obtenir une representation sous forme de chaine de caracteres
     * de cette liste associative.
     * @return une representation sous forme de chaine de caracteres de 
     *         cette liste associative.
     */
    //ne pas utiliser obtenirElements(cle)...
    @Override
    public String toString() {
       String s = "";
       Maillon<C> tmpC = cles;
       Maillon<Maillon<E>> tmpE = elements;
       Maillon<E> lesElements;
       C cle;
       if (estVide()) {
          s = "liste vide";
       } else {
          while (tmpC != null) {
             cle = tmpC.info();
             s = s + cle + " -> ";
             lesElements = tmpE.info();
             s = s + "[";
             while (lesElements != null) {
                s = s + lesElements.info();
                if (lesElements.suivant() != null) {
                   s = s + ", ";
                }
                lesElements = lesElements.suivant();
             }
             s = s + "]\n";
             tmpC = tmpC.suivant();
             tmpE = tmpE.suivant();
          }
          s = s.substring(0, s.length() - 1);
       }
       return s;
    }
    
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
    @Override
    @SuppressWarnings("unchecked")
    public boolean ajouter(Object cle, Object element, int index) throws NullPointerException,
                                                                         IndexOutOfBoundsException{
        boolean confirmation = false;
        
        //Verification cle ou element null
        if(cle == null){
            throw new NullPointerException("La clé " + cle + " est null");
        }
        if(element == null){
            throw new NullPointerException("L'élément " + element + " est null");
        }
        
        //Cas si la cle n'existe pas
        if(!this.cleExiste(cle)){
            confirmation = this.ajouter(cle, element);
        }
        //Cas si la cle existe
        else{
            //Verification de l'index
            if(index < 0 || index > nbrCles){
                throw new IndexOutOfBoundsException("L'index n'est pas inclu entre 0 et " + nbrCles);
            }
            
            if(this.elementExiste(cle, element)){
                confirmation = false;
            } else{
                ArrayList arrayElements = this.obtenirElements(cle);
                
                arrayElements.add(index, element);
                
                this.modifier(cle, arrayElements);
                
                confirmation = true;
            }
        }
        
        return confirmation;
    }
    
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
    @Override
    @SuppressWarnings({ "unchecked", "cast" })
    public boolean ajouter(Object cle, Object element) throws NullPointerException {
        //Verification cle null
        if(cle == null){
            throw new NullPointerException("La clé " + cle + " est null");
        }
        //Verification element null
        if(element == null){
            throw new NullPointerException("L'élément " + element + " est null");
        }
        
        boolean confirmation = false;
        
        //Cas si la cle existe
        if(this.cleExiste(cle)){
            if(!this.elementExiste(cle, element)){
                /* Méthode à Arrays */
                ArrayList arrayElements = this.obtenirElements(cle);
                arrayElements.add(element);
                this.modifier(cle, arrayElements);
                
                confirmation = true;
            }
            else{
                confirmation = false;
            }
        }
        
        //Cas si la cle n'existe pas
        else{
            //Ajouter cle à la fin de la chaine
            Maillon<C> clesIterator = cles;
            Maillon<Maillon<E>> elementsIterator = elements;
            
            //Aller à la fin de la liste
            while(clesIterator.suivant() != null){
                clesIterator = clesIterator.suivant();
                elementsIterator = elementsIterator.suivant();
            }
            //Créer une nouvelle cle + nouveau elements à cette place
            if(clesIterator.info() == null){
                clesIterator.modifierInfo((C)cle);
                elementsIterator.modifierInfo(new Maillon<E>((E)element));
            } else{
                clesIterator.modifierSuivant(new Maillon<C>((C)cle));
                elementsIterator.modifierSuivant(new Maillon<Maillon<E>>(new Maillon<E>((E)element)));
            }
            nbrCles++;
            confirmation = true;
            this.ajouter(cle, element); //Récursif car on vient d'ajouter la clé à la chaine de clés
        }
        
        return confirmation;
    }
    
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
    @Override
    @SuppressWarnings({ "unchecked", "cast" })
    public boolean ajouter(Object cle, ArrayList listeElements, int index) throws NullPointerException,
                                                                                  IndexOutOfBoundsException{
        //Verification cle null
        if(cle == null){
            throw new NullPointerException("La clé " + cle + " est null");
        }
        //Verification listeElements null
        if(listeElements == null){
            throw new NullPointerException("La liste d'éléments" + listeElements + " est null");
        }else{
            listeElements = this.removeDuplicates(listeElements);
            listeElements = this.removeNulls(listeElements);
        }
        
        boolean confirmation = false;
        
        if(!listeElements.isEmpty()){
            //Cas si la clé n'existe pas
            if(!this.cleExiste(cle)){
                confirmation = this.ajouter(cle, listeElements);
            }
            else{
                //Cas si la clé existe
                //Verification index entre 0 et this.obtenirNbrElements
                if(index < 0 || index > this.obtenirNbrElements(cle)){
                    throw new IndexOutOfBoundsException("L'index " + index + " n'est pas inclu entre 0 et " + this.obtenirNbrElements(cle));
                }
                
                listeElements = this.removeExistingValues(cle, listeElements);
                
                if(!listeElements.isEmpty()){
                    
                    ArrayList arrayElements = this.obtenirElements(cle);
                    arrayElements.addAll(index, listeElements);
                    this.modifier(cle, arrayElements);
                    
                    confirmation = true;
                }
            }
        }
        
        return confirmation;
    }
    
    
    
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
    @Override
    @SuppressWarnings({ "unchecked", "cast" })
    public boolean ajouter(Object cle, ArrayList listeElements) throws NullPointerException {
        //Verification cle null
        if(cle == null){
            throw new NullPointerException("La clé " + cle + " est null");
        }
        //Verification listeElements null
        if(listeElements == null){
            throw new NullPointerException("L'ArrayList " + listeElements + " est null");
        }
        else{
            listeElements = this.removeDuplicates(listeElements);
            listeElements = this.removeNulls(listeElements);
        }
        
        boolean confirmation = false;
        
        if(!listeElements.isEmpty()){
        
            //Cas si la clé existe
            if(this.cleExiste(cle)){
                Maillon<C> clesIterator = cles;
                Maillon<Maillon<E>> elementsIterator = elements;
                Maillon<E> elementIterator;
                boolean endOfCles = false;
                
                while(!endOfCles /*clesIterator != null*/ && clesIterator.info() != cle){
                    if(clesIterator.suivant() == null){
                        endOfCles = true;
                    }
                    else{
                        clesIterator = clesIterator.suivant();
                        elementsIterator = elementsIterator.suivant();
                    }
                }
                
                if(clesIterator.info() == cle){
                    elementIterator = elementsIterator.info();
                    while(elementIterator.suivant() != null){
                        elementIterator = elementIterator.suivant();
                    }
                    
                    if(elementIterator.info() == null){
                        elementIterator.modifierInfo(this.arrayListToMaillon(listeElements).info());
                        elementIterator.modifierSuivant(this.arrayListToMaillon(listeElements).suivant());
                    }else{
                        elementIterator.modifierSuivant(this.arrayListToMaillon(listeElements));
                    }
                    
                    confirmation = true;
                }
                else{
                    confirmation = false;
                }
            }
            //Cas si la clé n'existe pas
            else{
                Maillon<C> clesIterator = cles;
                Maillon<Maillon<E>> elementsIterator = elements;
                
                while(clesIterator.suivant() != null){
                    clesIterator = clesIterator.suivant();
                    elementsIterator = elementsIterator.suivant();
                }
                
                if(clesIterator.info() == null){
                    clesIterator.modifierInfo((C)cle);
                    elementsIterator.modifierInfo(this.arrayListToMaillon(listeElements));
                } else{                    
                    clesIterator.modifierSuivant(new Maillon<C>((C)cle));
                    elementsIterator.modifierSuivant(new Maillon<Maillon<E>>(this.arrayListToMaillon(listeElements)));
                }
                
                nbrCles++;
                
                confirmation = true;
            }
        }
        
        return confirmation;
    }
    
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
    @Override
    public Object supprimer(Object cle, int index) throws NullPointerException,
                                                          IndexOutOfBoundsException{
        //Verification cle null
        if(cle == null){
            throw new NullPointerException("La clé " + cle + " est null");
        }
        
        Object elementSupprime = null;
        
        if(this.cleExiste(cle)){
            //Verification index
            if(index < 0 || index > this.obtenirNbrElements(cle)){
                throw new NullPointerException("L'index " + index + " n'est pas inclu entre 0 et " + this.obtenirNbrElements(cle));
            }
            
            ArrayList arrayElements = this.obtenirElements(cle);
            
            elementSupprime = this.obtenirElement(cle, index);
            
            arrayElements.remove(index);
            
            if(arrayElements.isEmpty()){
                this.supprimer(cle);
            }else{
                this.modifier(cle, arrayElements);
            }
        }
        
        return elementSupprime;
    }
    
    /**
     * Supprime la cle donnee et tous ses elements associes de cette liste
     * associative.
     * 
     * @param cle la cle de l'association a supprimer de cette liste associative.
     * @return la liste des elements associee a la cle donnee, avant le retrait 
     *         ou null si la cle donnee n'existe pas dans cette liste associative.
     * @throws NullPointerException si cle est null.
     */
    @Override
    public ArrayList supprimer(Object cle) throws NullPointerException {
        //Verification cle null
        if(cle == null){
            throw new NullPointerException("La clé " + cle + " est null");
        }
        
        ArrayList elementsSupprimes;
        
        if(!this.cleExiste(cle)){
            elementsSupprimes = null;
        }else{
            Maillon<C> clesIterator = cles;
            Maillon<Maillon<E>> elementsIterator = elements;
            boolean endOfCles = false;
            
            while(!endOfCles /*clesIterator != null*/){
                if(clesIterator.info() == cle){
                    endOfCles = true;
                }
                else{
                    if(clesIterator.suivant().info() == cle){
                        endOfCles = true;
                    }else{
                        clesIterator = clesIterator.suivant();
                        elementsIterator = elementsIterator.suivant();
                    }
                }
            }
            
            elementsSupprimes = this.obtenirElements(cle);
            
            if(clesIterator.suivant().info() == cle){
                clesIterator.modifierSuivant(clesIterator.suivant().suivant());
                elementsIterator.modifierSuivant(elementsIterator.suivant().suivant());
            } else{
                if(clesIterator.info() == cle){
                    clesIterator.modifierInfo(clesIterator.suivant().info());
                    clesIterator.modifierSuivant(clesIterator.suivant().suivant());
                    elementsIterator.modifierInfo(elementsIterator.suivant().info());
                    elementsIterator.modifierSuivant(elementsIterator.suivant().suivant());
                }
            }
            
            nbrCles--;
        }
        
        return elementsSupprimes;
    }
    
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
    @Override
    public boolean supprimer(Object cle, Object element) throws NullPointerException {
        //Verification cle null
        if(cle == null){
            throw new NullPointerException("La clé " + cle + " est null");
        }
        //Verification element null
        if(element == null){
            throw new NullPointerException("L'élément " + element + " est null");
        }
        
        boolean confirmation = false;
        
        if(!this.elementExiste(cle, element) && element instanceof Integer){
            supprimer(cle, ((Integer) element).intValue());
            confirmation = true;
        }else{
            if(this.cleExiste(cle)){
                ArrayList arrayElements = this.obtenirElements(cle);
                arrayElements.remove(element);
                confirmation = true;
                
                if(arrayElements.isEmpty()){
                    this.supprimer(cle);
                }
                else{
                    this.modifier(cle, arrayElements);
                }
            }
        }
        
        return confirmation;
    }
    
    /**
     * Permet d'obtenir la liste des elements associee a la cle donnee.
     * @param cle la cle pour laquelle on veut obtenir les elements.
     * @return la liste des elements associee a la cle donnee ou null si la cle
     *         n'existe pas.
     * @throws NullPointerException si cle est null.
     */
    @Override
    @SuppressWarnings("unchecked")
    public ArrayList obtenirElements(Object cle) throws NullPointerException {
        //Verification cle null
        if(cle == null){
            throw new NullPointerException("La clé " + cle + " est null");
        }
        
        ArrayList arrayElements = new ArrayList();
        Maillon<C> clesIterator = cles;
        Maillon<Maillon<E>> elementsIterator = elements;
        Maillon<E> elementIterator;
        boolean endOfCles = false;
        
        if(this.cleExiste(cle)){
            while(!endOfCles /*clesIterator != null*/){
                if(clesIterator.info() == cle){
                    elementIterator = elementsIterator.info();
                    while(elementIterator != null){
                        arrayElements.add(elementIterator.info());
                        if(elementIterator.suivant() == null){
                            elementIterator = null;
                        }
                        else{
                            elementIterator = elementIterator.suivant();
                        }
                    }
                }
                
                if(clesIterator.suivant() == null){
                    endOfCles = true;
                }
                else{
                    clesIterator = clesIterator.suivant();
                    elementsIterator = elementsIterator.suivant();
                }
            }
        }
        else{
            arrayElements = null;
        }
        
        return arrayElements;
    }
    
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
    @Override
    public Object obtenirElement(Object cle, int index) throws NullPointerException,
                                                               IndexOutOfBoundsException{
        //Verification cle null
        if(cle == null){
            throw new NullPointerException("La clé " + cle + " est null");
        }
        //Verification index entre 0 et nbrCles
        if(index < 0 || index > this.obtenirNbrElements(cle)){
            throw new IndexOutOfBoundsException("L'index " + index + "n'est pas inclu entre 0 et " + this.obtenirNbrElements(cle));
        }
        
        Object elementRecherche = new Object();
        Maillon<C> clesIterator = cles;
        Maillon<Maillon<E>> elementsIterator = elements;
        Maillon<E> elementIterator;
        boolean endOfCles = false;
        
        if(this.cleExiste(cle)){
            while(!endOfCles){
                if(clesIterator.info() == cle){
                    int indexIterator = 0;
                    elementIterator = elementsIterator.info();
                    while(elementIterator != null && indexIterator >= 0){
                        if(indexIterator == index){
                            elementRecherche = elementIterator.info();
                            indexIterator = -2;
                        }
                        
                        if(elementIterator.suivant() == null){
                            elementIterator = null;
                        }
                        else{
                            elementIterator = elementIterator.suivant();
                            indexIterator++;
                        }
                    }
                }
                
                if(clesIterator.suivant() == null){
                    endOfCles = true;
                }
                else{
                    clesIterator = clesIterator.suivant();
                    elementsIterator = elementsIterator.suivant();
                }
            }
        }
        else{
            elementRecherche = null;
        }
        
        return elementRecherche.toString();
    }
    
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
    @Override
    public int obtenirIndex(Object cle, Object element) throws NullPointerException {
        //Verification cle null
        if(cle == null){
            throw new NullPointerException("La clé " + cle + " est null");
        }
        //Verification element null
        if(element == null){
            throw new NullPointerException("L'élément " + element + " est null");
        }
        
        int indexRecherche = -1;
        Maillon<C> clesIterator = cles;
        Maillon<Maillon<E>> elementsIterator = elements;
        Maillon<E> elementIterator;
        boolean endOfCles = false;
        
        if(this.cleExiste(cle)){
            while(!endOfCles){
                if(clesIterator.info() == cle){
                    elementIterator = elementsIterator.info();
                    int indexOfElementIterator = 0;
                    while(elementIterator != null && indexRecherche == -1){
                        if(elementIterator.info().equals(element)){
                            indexRecherche = indexOfElementIterator;
                        }
                        if(elementIterator.suivant() == null){
                            elementIterator = null;
                        }
                        else{
                            elementIterator = elementIterator.suivant();
                            indexOfElementIterator++;
                        }
                    }
                }
                
                if(clesIterator.suivant() == null){
                    endOfCles = true;
                }
                else{
                    clesIterator = clesIterator.suivant();
                    elementsIterator = elementsIterator.suivant();
                }
            }
        }else{
            indexRecherche = -1;
        }
        
        return indexRecherche;
    }
    
    /**
     * Permet d'obtenir une NOUVELLE LISTE contenant toutes les cles de cette 
     * liste associative qui ont, parmi leurs elements associees, l'element donne.
     * 
     * @param element l'element dont on cherche toutes les cles d'association.
     * @return la liste des cles d'association pour l'element donne (la liste
     *         retournee est vide si aucune cle n'est trouvee).
     * @throws NullPointerException si element est null.
     */
    @Override
    @SuppressWarnings("unchecked")
    public ArrayList obtenirCles(Object element) throws NullPointerException {
        //Verification element null
        if(element == null){
            throw new NullPointerException("L'élément " + element + " est null");
        }
        
        ArrayList newCles = new ArrayList();
        Maillon<C> clesIterator = cles;
        Maillon<Maillon<E>> elementsIterator = elements;
        Maillon<E> elementIterator;
        boolean endOfCles = false;
        
        while(!endOfCles){
            boolean elementInCle = false;
            
            elementIterator = elementsIterator.info();
            
            while(elementIterator != null){
                if(elementIterator.info() == element){
                    elementInCle = true;
                }
                
                if(elementIterator.suivant() == null){
                    elementIterator = null;
                }
                else{
                    elementIterator = elementIterator.suivant();
                }
            }
            
            if(elementInCle == true){
                newCles.add(clesIterator.info());
            }
            
            if(clesIterator.suivant() == null){
                endOfCles = true;
            }
            else{
                clesIterator = clesIterator.suivant();
                elementsIterator = elementsIterator.suivant();
            }
        }
        
        return newCles;
    }
    
    /**
     * Permet d'obtenir une NOUVELLE liste de toutes les cles de cette liste 
     * associative. 
     *
     * @return la liste de toutes les cles de cette liste associative. 
     *         (La liste retournee est vide si cette liste associative
     *         est vide).
     */
    @Override
    @SuppressWarnings("unchecked")
    public ArrayList obtenirCles() {
        ArrayList newCles = new ArrayList();
        Maillon<C> clesIterator = cles;
        boolean endOfCles = false;
        
        while(!endOfCles){
            newCles.add(clesIterator.info());
            
            if(clesIterator.suivant() == null){
                endOfCles = true;
            }
            else{
                clesIterator = clesIterator.suivant();
            }
        }
        
        return newCles;
    }
    
    private int obtenirNbrElements(Object cle) throws NullPointerException{
        int nbrElements = 0;
        
        //Verification cle null
        if(cle == null){
            throw new NullPointerException("La clé " + cle + " est null");
        }
        
        Maillon<C> clesIterator = cles;
        Maillon<Maillon<E>> elementsIterator = elements;
        Maillon<E> elementIterator;
        boolean endOfCles = false;
        
        if(this.cleExiste(cle)){
            while(!endOfCles){
                
                if(clesIterator.info() == cle){
                    elementIterator = elementsIterator.info();
                    while(elementIterator != null){
                        nbrElements++;
                        
                        if(elementIterator.suivant() == null){
                            elementIterator = null;
                        }
                        else{
                            elementIterator = elementIterator.suivant();
                        }
                    }
                }
                
                if(clesIterator.suivant() == null){
                    endOfCles = true;
                }
                else{
                    clesIterator = clesIterator.suivant();
                    elementsIterator = elementsIterator.suivant();
                }
            }
        }
        
        return nbrElements;
    }
    
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
    @Override
    @SuppressWarnings("unchecked")
    public boolean modifier(Object cle, Object nouvelElement, int index) throws NullPointerException,
                                                                                IndexOutOfBoundsException {
        //Verification cle
        if(cle == null){
            throw new NullPointerException("La clé " + cle + " est null");
        }
        //Verification nouvelElement
        if(nouvelElement == null){
            throw new NullPointerException("Le nouvel element " + nouvelElement + " est null");
        }
        
        boolean confirmation = false;
        
        //Cas où la clé n'existe pas ou que le nouvel element existe déjà
        if(!this.cleExiste(cle)){
            confirmation = false;
        } else if(this.elementExiste(cle, nouvelElement)){
            confirmation = false;
        }else{
            //Verification index
            if(index < 0 || index > this.obtenirNbrElements(cle)){
                throw new IndexOutOfBoundsException("L'index " + index + " n'est pas inclu entre 0 et " + this.obtenirNbrElements(cle));
            }
            
            //Cas où la clé existe
            if(this.cleExiste(cle)){
                ArrayList arrayElements = this.obtenirElements(cle);
                arrayElements.set(index, (E)nouvelElement);

                confirmation = this.modifier(cle, arrayElements);
            }
        }
        
        
        return confirmation;
    }
    
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
    @Override
    public boolean modifier(Object cle, ArrayList nouveauxElements) throws NullPointerException {
        //Verification cle null
        if(cle == null){
            throw new NullPointerException("La clé " + cle + " est null");
        }
        //Verification nouveauElements null
        if(nouveauxElements == null){
            throw new NullPointerException("L'array de nouveaux éléments " + nouveauxElements + " sont null");
        }
        
        boolean confirmation = false;
        
        if(!this.cleExiste(cle)){
            confirmation = false;
        }else{
            //Roll to elements
            Maillon<C> clesIterator = cles;
            Maillon<Maillon<E>> elementsIterator = elements;
            boolean endOfCles = false;
            
            while(!endOfCles){
                if(clesIterator.info() == cle){
                    endOfCles = true;
                }else{
                    clesIterator = clesIterator.suivant();
                    elementsIterator = elementsIterator.suivant();
                }
            }
            
            elementsIterator.modifierInfo(this.arrayListToMaillon(nouveauxElements));
            confirmation = true;
        }
        
        return confirmation;
    }
    
    /**
     * Retire toutes les cles et leurs elements associees de cette liste
     * associative. Apres l'appel de cette methode, estVide() retourne true.
     */
    @Override
    public void vider() {
        cles = new Maillon<C>(null);
        elements = new Maillon<Maillon<E>>(null);
        nbrCles = 0;
    }
    
      /**
       * Verifie si cette liste associative est vide (ne contient aucune cle).
       * 
       * @return true si cette liste associative est vide, false sinon.
       */
    @Override
    public boolean estVide() {
          boolean controle = false;
          if(nbrCles == 0 || this.cles.info() == null){
              controle = true;
          }
          return controle;
    }
    
    /**
     * Permet d'obtenir la taille de cette liste associative. 
     * La taille correspond au nombre de cles dans cette liste associative.
     * 
     * @return la taille de cette liste associative.
     */
    @Override
    public int taille() {
        return nbrCles;
    }
    
    /**
     * Verifie si la cle donnee existe dans cette liste associative.
     * 
     * @param cle la cle dont on teste l'existence.
     * @return true si la cle existe, false sinon.
     * @throws NullPointerException si cle est null.
     */
    @Override
    public boolean cleExiste(Object cle) throws NullPointerException {
        //Verification cle null
        if(cle == null){
            throw new NullPointerException("La clé " + cle + " est null");
        }
        
        boolean cleExiste = false;
        
        Maillon<C> clesIterator = cles;
        boolean endOfCles = false;
        
        while(cleExiste == false && !endOfCles){
            if(clesIterator.info() == cle){
                cleExiste = true;
            }
            if(clesIterator.suivant() == null){
                endOfCles = true;
            }
            else{
                clesIterator = clesIterator.suivant();   
            }
        }
        
        return cleExiste;
    }
    
    private boolean elementExiste(Object cle, Object element) throws NullPointerException{
        //Verification de cle null
        if(cle == null){
            throw new NullPointerException("La clé " + cle + " est null");
        }
        //Verification d'element null
        if(element == null){
            throw new NullPointerException("L'élément " + element + " est null");
        }
        
        boolean elementExiste = false;
        
        Maillon<C> clesIterator = cles;
        Maillon<Maillon<E>> elementsIterator = elements;
        Maillon<E> elementIterator;
        boolean endOfCles = false;
        
        if(this.cleExiste(cle)){
            //Obtenir position cle
            while(clesIterator.info() != cle && !endOfCles){
                if(clesIterator.suivant() == null){
                    endOfCles = true;
                }
                else{
                    clesIterator = clesIterator.suivant();
                    elementsIterator = elementsIterator.suivant();
                }
            }
            
            elementIterator = elementsIterator.info();
            
            //Chercher l'element
            while(elementExiste == false && elementIterator != null){
                if(elementIterator.info() == element){
                    elementExiste = true;
                }
                
                if(elementIterator.suivant() == null){
                    elementIterator = null;
                }
                else{
                    elementIterator = elementIterator.suivant();
                }
            }
        }
        else{
            elementExiste = false;
        }
        
        return elementExiste;
    }

    @SuppressWarnings({ "unchecked", "cast" })
    private Maillon<E> arrayListToMaillon(ArrayList arrayList){
        Maillon<E> newMaillons = new Maillon<E>(null);
        Maillon<E> newMaillonsIterator = newMaillons;
        
        for(int i = 0; i < arrayList.size(); i++){
            newMaillonsIterator.modifierInfo((E)arrayList.get(i));
            if(i != arrayList.size() - 1){
                newMaillonsIterator.modifierSuivant(new Maillon<E>(null));
                newMaillonsIterator = newMaillonsIterator.suivant();
            }
        }
        
        return newMaillons;
    }

    @SuppressWarnings("unchecked")
    private ArrayList removeDuplicates(ArrayList arrayList) throws NullPointerException{
        //Verification arrayList null
        if(arrayList == null){
            throw new NullPointerException("L'ArrayList " + arrayList + " est null");
        }
        
        ArrayList newArrayList = new ArrayList();
        
        for(Object objet : arrayList){
            if(!newArrayList.contains(objet)){
                newArrayList.add(objet);
            }
        }
        
        return newArrayList;
    }

    @SuppressWarnings("unchecked")
    private ArrayList removeNulls(ArrayList arrayList) throws NullPointerException{
        //Verification arrayList null
        if(arrayList == null){
            throw new NullPointerException("L'ArrayList " + arrayList + " est null");
        }
        
        ArrayList newArrayList = new ArrayList();
        
        for(Object objet : arrayList){
            if(objet != null){
                newArrayList.add(objet);
            }
        }
        
        return newArrayList;
    }

    @SuppressWarnings("unchecked")
    private ArrayList removeExistingValues(Object cle, ArrayList arrayList){
        ArrayList newArrayList = new ArrayList();
        
        for(Object objet : arrayList){
            if(!this.elementExiste(cle, objet)){
                newArrayList.add(objet);
            }
        }
        
        return newArrayList;
    }
    
}
