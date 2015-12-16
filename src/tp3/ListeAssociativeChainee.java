/************************************* ***/
/*  Nom: Hakim                           */
/*  Prenom: Antoine                      */
/*  Code Permanant: HAKA24019407         */ 
/*  Professeur: Melanie Lord             */
/*  TP: Numero 2                         */
/*****************************************/
package tp3;
import java.util.ArrayList;

public class ListeAssociativeChainee<C, E> implements IListeAssociative <C, E> {
    
    private Maillon<C> cles;
    private Maillon<Maillon<E>> elements;
    private int nbrCles;
    
    public ListeAssociativeChainee () {
        cles = new Maillon<>(null);
        elements = new Maillon<>(null);
        nbrCles = 0;
    }
    
    @Override
    public boolean ajouter (C cle, E element, int index) throws ArrayIndexOutOfBoundsException, NullPointerException { 
        
        exceptionCle(cle);
        
        exceptionElem(element);
        
        if(!estVide()) {
            if(cleExiste(cle)) {

                Maillon<Maillon<E>> listeMaillon = obtenirListeElements(indexCle(cle));
                
                exceptionIndex(index, tailleListeElements(listeMaillon));
                
                if(!elementExiste(listeMaillon, element)) {
                    Maillon<E> prec = listeMaillon.info();
                    Maillon<E> pres = prec.suivant();
                    Maillon<E> nouveau;
                    if(index == 0) {
                        ajoutDebut(listeMaillon, new Maillon(element));
                    } else {
                        int i = 0;
                        while(i < index && pres != null) {
                            if(i + 1 != index) {
                                prec = pres;
                                pres = pres.suivant();
                            }
                            i++;
                        }
                        nouveau = new Maillon(element);
                        prec.modifierSuivant(nouveau);
                        nouveau.modifierSuivant(pres);
                    }
                } else {
                    return false;
                }
            } else {
                ajoutFin(cle, element); 
                nbrCles++;
            }
        } else {
            ajouterQdVide(cle, element);
            nbrCles++;
        }
        return true;
    }
    
    @Override
    public boolean ajouter (C cle, E element) throws NullPointerException { 
        exceptionCle(cle);
        
        exceptionElem(element);
        
        if(!estVide()) {
            if(cleExiste(cle)) {
                
                Maillon<Maillon<E>> listeMaillon = obtenirListeElements(indexCle(cle));
                
                if(!elementExiste(listeMaillon, element)) {
                    Maillon<E> tmp = listeMaillon.info();
                    while(tmp.suivant() != null) {
                        tmp = tmp.suivant();
                    }
                    tmp.modifierSuivant(new Maillon(element, null));
                } else {
                    return false;
                }
            } else {
                ajoutFin(cle, element);
                nbrCles++;
            }
        } else {
            ajouterQdVide(cle, element);
            nbrCles++;
        }
        return true;
    }
    
    @Override
    public boolean ajouter (C cle, ArrayList<E> listeElements) throws NullPointerException{
        
        exceptionCle(cle);
        
        exceptionList(listeElements);
        
        listeElements = enleverDoublons(listeElements);
        
        if(!listeContientTousNull(listeElements)) {
            if(!estVide()) {
                if(cleExiste(cle)) {
                    Maillon<Maillon<E>> listeMaillon = obtenirListeElements(indexCle(cle));
                    int dernierIndex = tailleListeElements(listeMaillon);
                    return ajoutDeList(listeMaillon, listeElements, dernierIndex);
                } else {
                    ajoutFin(cle, listeElements);
                    nbrCles++;
                }
            } else {
                ajouterQdVide(cle, listeElements); 
                nbrCles++;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public boolean ajouter (C cle, ArrayList<E> listeElements, int index) throws IndexOutOfBoundsException, NullPointerException{
        exceptionCle(cle);
        
        exceptionList(listeElements);
        
        listeElements = enleverDoublons(listeElements);
        
        if(!listeContientTousNull(listeElements)) {
            
            if(!estVide()) {
                Maillon<Maillon<E>> lElems = obtenirListeElements(indexCle(cle));

                exceptionIndex(index, tailleListeElements(lElems) );

                if(cleExiste(cle)) {
                    return ajoutDeList(lElems, listeElements, index);
                } else {
                    ajoutFin(cle, listeElements);
                    nbrCles++;
                }
            } else {
                ajouterQdVide(cle, listeElements);
                nbrCles++;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public E supprimer (C cle, int index) throws NullPointerException, IndexOutOfBoundsException { 
        E e = null;
        
        exceptionCle(cle);
        
        if(!estVide() && cleExiste(cle)) {       
            int indexCle = indexCle(cle);
            Maillon<Maillon<E>> lElems = obtenirListeElements(indexCle);
            
            exceptionIndex(index, tailleListeElements(lElems));
            
            if(index == 0) {
                Maillon<E> m = lElems.info();
                lElems.modifierInfo(m.suivant());
                e = m.info();
                
                if(lElems.info() == null) {
                    if(indexCle == 0) {
                        cles = cles.suivant();
                        elements = lElems.suivant(); 
                    } else {
                        int i = 0;
                        Maillon<Maillon<E>> elemPrec = listeElementPrecedent(indexCle);
                        Maillon<Maillon<E>> elemPres = listeElementPresent(elemPrec);
                        
                        Maillon<C> cPrec = clePrecedente(indexCle);
                        Maillon<C> cPres = clePresente(cPrec);

                        if( cPrec == null && elemPrec == null) {
                            elemPres = elemPres.suivant();
                            cPres = cPres.suivant();
                        } else {
                            elemPrec.modifierSuivant(elemPres.suivant());
                            cPrec.modifierSuivant(cPres.suivant());
                        }
                    }
                    nbrCles--;
                }
            } else {
                Maillon<E> prec = elementPrecedent(lElems, index);
                Maillon<E> pres = elementPresent(prec);

                prec.modifierSuivant(pres.suivant());
                e = pres.info();
            }
        }
        return e;
    }
    
    @Override
    public ArrayList<E> supprimer (C cle) throws NullPointerException{ 
        exceptionCle(cle);
        if(!estVide()) {
            if(cleExiste(cle)) {
                int indexCle = indexCle(cle);
                Maillon<Maillon<E>> lElems = obtenirListeElements(indexCle);
                ArrayList<E> aElems = new ArrayList<>();
                if(indexCle == 0) {
                    Maillon<E> eTmp = lElems.info();
                    while(eTmp != null) {
                        aElems.add(eTmp.info());
                        eTmp = eTmp.suivant();
                        lElems.modifierInfo(eTmp);
                    }
                    elements = elements.suivant();
                    cles = cles.suivant();
                    nbrCles--;
                } else {
                    Maillon<E> e = lElems.info();
                    while(e != null) {
                        aElems.add(e.info());
                        e = e.suivant();
                        lElems.modifierInfo(e);
                    }
                    Maillon<C> cPrec = clePrecedente(indexCle);
                    Maillon<C> cPres = clePresente(cPrec);
                    
                    Maillon<Maillon<E>> ePrec = listeElementPrecedent(indexCle);
                    Maillon<Maillon<E>> ePres = listeElementPresent(elements);

                    ePrec.modifierSuivant(ePres.suivant());
                    cPrec.modifierSuivant(cPres.suivant());

                    nbrCles--;
                }               
                return aElems;
            }
        }
        return null;
    }
    
    @Override
    public boolean supprimer (C cle, E element) throws NullPointerException{ 
        exceptionCle(cle);
        exceptionElem(element);
        if(!estVide() && cleExiste(cle)) {
            int indexCle = indexCle(cle);
            Maillon<Maillon<E>> lElems = obtenirListeElements(indexCle);
            if(elementExiste(lElems, element)) {
                int indexElement = obtenirIndex(cle, element);
                int i = 0;
                if(indexElement == 0) {
                    Maillon<E> m = lElems.info();
                    lElems.modifierInfo(m.suivant());
                    if(lElems.info() == null) {
                        if(indexCle == 0) {
                            cles = cles.suivant();
                            elements = elements.suivant();
                        } else {
                           Maillon<Maillon<E>> lElemPrec = listeElementPrecedent(indexCle);
                           Maillon<Maillon<E>> lElemPres = listeElementPresent(lElemPrec);
                           
                           Maillon<C> lClesPrec = clePrecedente(indexCle);
                           Maillon<C> lClesPres = clePresente(lClesPrec);

                           lElemPrec.modifierSuivant(lElemPres.suivant());
                           lClesPrec.modifierSuivant(lClesPres.suivant());
                        }
                        nbrCles--;
                    }
                } else {
                    
                    Maillon<E> ePrec = elementPrecedent(lElems, indexElement);
                    Maillon<E> ePres = elementPresent(ePrec);
                    
                    ePrec.modifierSuivant(ePres.suivant());
                }
                return true;
            }
        }
        return false;
    }
    
    @Override
    public ArrayList<E> obtenirElements (C cle) throws NullPointerException { 
        exceptionCle(cle);
        if(!estVide()) {
            if(cleExiste(cle)) {
                Maillon<Maillon<E>> lElems = obtenirListeElements(indexCle(cle));
                Maillon<E> eTmp = lElems.info();
                ArrayList<E> listElements = new ArrayList();
                while(eTmp != null) {
                    listElements.add(eTmp.info());
                    eTmp = eTmp.suivant();
                }
                return listElements;
            }
        }
        return null;
    }
    
    @Override
    public E obtenirElement (C cle, int index) throws IndexOutOfBoundsException, NullPointerException{ 
        exceptionCle(cle);
        if(estVide()) {
            if(cleExiste(cle)) {
                Maillon<Maillon<E>> lElems = obtenirListeElements(indexCle(cle));

                exceptionIndex(index, tailleListeElements(lElems));

                return elementPresent(elementPrecedent(lElems, index)).info();
            }
        }
        return null;
    }
    
    @Override
    public int obtenirIndex (C cle, E element) throws NullPointerException{ 
        exceptionCle(cle);
        
        exceptionElem(element);
        
        if(!estVide() && cleExiste(cle)) {
            Maillon<Maillon<E>> lElems = obtenirListeElements(indexCle(cle));
            
            if(elementExiste(lElems, element)) {
                Maillon<E> eTmp = lElems.info();
                int index = 0;
                while(eTmp.info() != element) {
                    eTmp = eTmp.suivant();
                    index++;
                }
                return index;
            }
        }
        return -1;
    }
    
    @Override
    public ArrayList<C> obtenirCles (E element) throws NullPointerException { 
        exceptionElem(element);
        
        ArrayList<C> listeCles = new ArrayList();
        
        if(!estVide()) {
            Maillon<C> cTmp = cles;
            Maillon<Maillon<E>> lElems = elements;
            
            while(cTmp != null) {
                if(elementExiste(lElems, element)) {
                    listeCles.add(cTmp.info());
                }
                cTmp = cTmp.suivant();
                lElems = lElems.suivant();
            }
        }
        return listeCles;
    }
    
    @Override
    public ArrayList<C> obtenirCles () { 
        ArrayList<C> listeCles = new ArrayList();
        
        if(!estVide()) {
            Maillon<C> cTmp = cles;

            while(cTmp != null) {
                listeCles.add(cTmp.info());
                cTmp = cTmp.suivant();
            }
        } 
        return listeCles;
    }
    
    @Override
    public boolean modifier (C cle, E nouvelElement, int index) throws NullPointerException, IndexOutOfBoundsException{ 
        exceptionCle(cle);
        
        exceptionElem(nouvelElement);
        
        if(!estVide() && cleExiste(cle)) {
            int indexCle = indexCle(cle);
            Maillon<Maillon<E>> lElems = obtenirListeElements(indexCle);
                
            exceptionIndex(index, tailleListeElements(lElems));
                
            if(!elementExiste(lElems, nouvelElement)) {
                Maillon<E> pres;
                if(index == 0) {
                    pres = elementPrecedent(lElems, index);
                } else {
                    pres = elementPresent(elementPrecedent(lElems, index));
                }                
                pres.modifierInfo(nouvelElement);
                    
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean modifier (C cle, ArrayList<E> nouveauxElements) throws NullPointerException { 
        exceptionCle(cle);
        
        exceptionList(nouveauxElements);
        
        if(!estVide() && cleExiste(cle) && !listeContientTousNull(nouveauxElements)) {
            Maillon<Maillon<E>> lElems = obtenirListeElements(indexCle(cle));
            Maillon<E> eTmp = lElems.info();

            supprimerTousLesElements(lElems);
            
            nouveauxElements = enleverDoublons(nouveauxElements);
                
            ajoutDeList(lElems, nouveauxElements, 0);
            
            return true;
        }
        return false;
    }
    
    @Override
    public void vider () {
        if(!estVide()) {
            Maillon<C> cTmp = cles;
            Maillon<Maillon<E>> lElemTmp = elements;
            while(cTmp != null) {
                lElemTmp = lElemTmp.suivant();
                cTmp = cTmp.suivant();
                nbrCles--;
            }
        }
    }
    
    @Override
    public boolean estVide () { 
        return nbrCles == 0;
    }
    
    @Override
    public int taille () { 
        return nbrCles;
    }
    
    @Override
    public boolean cleExiste(C cle) throws NullPointerException{
        if(cle != null) {
            Maillon<C> c = cles;
            while(c != null) {
                if(c.info().equals(cle)) {
                    return true;
                }
                c = c.suivant();
            }
        }
        return false;
    }
    
    @Override
    public boolean equals (Object obj) { 
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof IListeAssociative)) {
            return false;
        }
        if(!(obj instanceof ListeAssociativeChainee)) {
            return false;
        }
        if(this.getClass() != obj.getClass()) {
            return false;
        }
        
        ListeAssociativeChainee<C, E> l1 = (ListeAssociativeChainee) obj;
        ListeAssociativeChainee<C, E> l2 = this;
        
        if(l1.nbrCles != l2.nbrCles) {
            return false;
        }
        
        boolean listeAssociativePareil = false;
        boolean souMaillonPareil = false;
        
        Maillon<C> cTmpL1 = l1.cles;
        Maillon<C> cTmpL2 = l2.cles;
       
        int i = 0;

        while(cTmpL1 != null && cTmpL2 != null) { 
            Maillon<Maillon<E>> curseurL1 = l1.obtenirListeElements(i);
            int j = 0;  
            boolean cleTrouver = false;
            while(cTmpL2 != null && cleTrouver == false) { 
                if(cTmpL2.info().equals(cTmpL1.info())) {
                    Maillon<Maillon<E>> curseurL2 = l2.obtenirListeElements(j);    
                    if(tailleListeElements(curseurL1) == tailleListeElements(curseurL2)) {
                        if(curseurL2 != null) {                   
                            Maillon<E> infoL1 = curseurL1.info();
                            Maillon<E> infoL2 = curseurL2.info();
                            while( infoL1.info().equals(infoL2.info()) &&
                                  (infoL1.suivant() != null && infoL2.suivant() != null ) ) {
                                infoL1 = infoL1.suivant();
                                infoL2 = infoL2.suivant();
                            }
                            if( infoL1.info().equals( infoL2.info() ) ) {
                                souMaillonPareil = true;
                            }
                        }
                    }
                    cleTrouver = true;
                } else {
                    souMaillonPareil = false;
                    cTmpL2 = cTmpL2.suivant();
                    j++;
                }
            }
            cTmpL1 = cTmpL1.suivant();
            cTmpL2 = l2.cles;
            i++;
        }
        if(souMaillonPareil == true) { 
            listeAssociativePareil = true;
        }      
        return listeAssociativePareil;
    }

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
    
    
    /********************************/
    /*       METHODEs PRIVEES       */
    /********************************/

    /******************************************************************/
    /* Methodes qui gerent les exception de chaque truc: cle, element */
    /* , arrayList<E> ou l'index                                      */
    /******************************************************************/
    private void exceptionCle(C cle) throws NullPointerException {
        if(cle == null) {
            throw new NullPointerException();
        }
    }
    private void exceptionElem(E element) throws NullPointerException {
        if(element == null) {
            throw new NullPointerException();
        }
    }
    private void exceptionList(ArrayList<E> liste) throws NullPointerException {
        if(liste == null) {
            throw new NullPointerException();
        }
    }
    private void exceptionIndex(int index, int taille) throws IndexOutOfBoundsException {
        if(index < 0 || index > taille ) {
            throw new IndexOutOfBoundsException();
        }
    }
    
    /* Retourne boolean qui indique si les elements de l'array list sont tous null */
    private boolean listeContientTousNull(ArrayList<E> listeElements) {
        for(E element : listeElements) {
            if(element != null) {
                return false;
            }
        }
        return true;
    }

    /* Retourne un boolean qui indique si l'element existe dans la liste d'element passer */
    private boolean elementExiste(Maillon<Maillon<E>> listeMaillon, E element) {
        Maillon<Maillon<E>> m = listeMaillon;
        Maillon<E> tmp = m.info();
        boolean existe = false;
        while(tmp != null && !tmp.info().equals(element)) {
            tmp = tmp.suivant();
        }
        if(tmp != null && tmp.info().equals(element)) {
            existe = true;
        }
        return existe;
    }
    
    /* Methode qui supprime tous les elements d'une liste d'element passer en parametre */
    private void supprimerTousLesElements(Maillon<Maillon<E>> lElems) {
        while(lElems.info() != null) {
            lElems.modifierInfo(lElems.info().suivant());
        }
    }
    
    /* Methode qui ajoute un maillon contenant un element au debut d'une liste d'elemen */
    private void ajoutDebut(Maillon<Maillon<E>> liste, Maillon<E> nouv) {
        Maillon<E> ancienDebut = liste.info();
        liste.modifierInfo(nouv);
        nouv.modifierSuivant(ancienDebut);
    }
    
    /******************************************************/
    /* Ajoute une cle et. un element ou une array liste   */ 
    /* d'elements, a la fin de la liste associative.      */
    /******************************************************/

    private void ajoutFin(C cle, E element) {
        Maillon<C> finCles = cles;
        Maillon<Maillon<E>> finElem = elements;
        while(finCles.suivant() != null && finElem.suivant() != null) {
            finCles = finCles.suivant();
            finElem = finElem.suivant();
        }
        finCles.modifierSuivant(new Maillon(cle, null));
        finElem.modifierSuivant(new Maillon(new Maillon(element, null)));
    }
    private void ajoutFin(C cle, ArrayList<E> listeElements) {
        Maillon<C> lCles = cles;
        Maillon<Maillon<E>> lElems = elements;
        while(lCles.suivant() != null && lElems.suivant() != null) {
            lCles = lCles.suivant();
            lElems = lElems.suivant();
        }
        lCles.modifierSuivant(new Maillon(cle));//modifie le suivant de cle avec la nouvelle cle
        lElems.modifierSuivant(new Maillon(null));//meme chose
        
        lElems = lElems.suivant();
        
        lElems.modifierInfo(new Maillon(null));
        
        Maillon<E> tmp = lElems.info(); 
   
        int i = 0;

        while(!listeElements.isEmpty()) {
            
            E e = listeElements.remove(0);
            
            if(i == 0 && e != null) {
                tmp.modifierInfo(e);
                i++;
            } else if( e != null) {
                tmp.modifierSuivant(new Maillon(e));
                tmp = tmp.suivant();
                i++;
            }
        }
    }

    /* Ajoute une array liste d'element dans une liste d'element maillon */
    /* a l'index specifier en paramatre.                                 */
    private boolean ajoutDeList(Maillon<Maillon<E>> lElems, ArrayList<E> liste, int index) {
        boolean ajouter = false;
        
        int i = 0;
        
        Maillon<E> tmp = lElems.info();
        Maillon<E> prec = tmp;
        
        if(index == 0) {
            while(!liste.isEmpty()) {
                E e = liste.remove(0);
                if(!elementExiste(lElems, e) && e != null) {
                    Maillon<E> nouveau = new Maillon(e, tmp);
                    tmp = nouveau;
                    ajouter = true;
                }
            }
            lElems.modifierInfo(tmp);
        } else {
            while(i < index) {
                prec = tmp;
                tmp = tmp.suivant();
                i++;
            }   
            while(!liste.isEmpty()) {
                E e = liste.remove(0);
                if(!elementExiste(lElems, e) && e != null) {
                    Maillon<E> nouveau = new Maillon(e, null);
                    prec.modifierSuivant(nouveau);
                    nouveau.modifierSuivant(tmp);
                    prec = nouveau;
                    ajouter = true;
                }
            }
        }
        return ajouter;
    }
    
    /*****************ajouterQdVide***************************/
    /* Ajoute la cle passer en parametre avec soit l'element */
    /* ou l'array liste d'element associer a cette cle a la  */
    /* fin de la liste associative.                          */
    /*********************************************************/
    private void ajouterQdVide(C c, E e){
        Maillon<C> lCles = cles;
        lCles.modifierInfo(c);
        Maillon<Maillon<E>> lElems = elements;
        lElems.modifierInfo(new Maillon(e, null));
    }
    private void ajouterQdVide(C c, ArrayList<E> arrLst) {
        Maillon<C> lCles = cles;
        Maillon<Maillon<E>> lElems = elements;
            
        lCles.modifierInfo(c);
        lElems.modifierInfo(new Maillon(null));
            
        Maillon<E> tmp = lElems.info();
        int i = 0;
        int taille = arrLst.size();
        while(!arrLst.isEmpty() && i < taille) {
            E e = arrLst.remove(0);
            if(i + 1 == taille - 1) {
                if(e != null) {
                    tmp.modifierInfo(e);
                    E eLast = arrLst.remove(0);
                    tmp.modifierSuivant(new Maillon(eLast));
                    tmp = tmp.suivant();
                    i = taille;
                }
            } else {
                if(e != null) {
                    tmp.modifierInfo(e);
                    tmp.modifierSuivant(new Maillon(null));
                    tmp = tmp.suivant();
                    i++;
                }
            }
        }
    }
    
    /* Nous retourn l'index de a cle passer en parametre */
    public int indexCle(C cle) {
        Maillon<C> c = cles;
        int index = -1;
        int i = 0;
        while(c.suivant() != null && !c.info().equals(cle)) {
            c = c.suivant();
            i++;
        }
        if(c.info().equals(cle)) {
            index = i;
        }
        return index;
    }
    
    /* Nous retourn la liste d'element associer a la cle de l'index donner en parametre */
    private Maillon<Maillon<E>> obtenirListeElements(int indexCle) {
        if(elements != null) {
            Maillon<Maillon<E>> m = elements;
            int i = 0;
            while(i < indexCle) {
                i++;
                m = m.suivant();
            }
            return m;
        }
        return null;
    }
    
    /*******************************************************************/
    /* Les methodes avec le mot precedent(e) dedans retourn le maillon */
    /* contenant soit: la cle, l'element, ou la liste d'elements       */
    /* a l'index specifier en parametre. L'index indique la position   */
    /* du maillon present mais nous on retourn le precent a la place.  */
    /*                                                                 */
    /* Les methode avec le mot present(e) dedans retourne le maillon   */
    /* present et prend en parametre le maillon precedent.             */
    /*******************************************************************/
    private Maillon<C> clePrecedente(int index) {
        Maillon<C> pres = cles;
        Maillon<C> prec = pres;
        int i = 0;
        while(i < index) {
            prec = pres;
            pres = pres.suivant();
            i++;
        }
        return prec;
    }  
    private Maillon<C> clePresente(Maillon<C> clePrecedente) {
        return clePrecedente.suivant();
    }  
   
    private Maillon<E> elementPrecedent(Maillon<Maillon<E>> lElems, int index) {
        Maillon<E> pres = lElems.info();
        Maillon<E> prec = pres;
        int i = 0;
        while(i < index) {
            prec = pres;
            pres = pres.suivant();
            i++;
        }
        return prec;
    }
    private Maillon<E> elementPresent(Maillon<E> elementPrecedent) {
        return elementPrecedent.suivant();
    }   
    
    private Maillon<Maillon<E>> listeElementPrecedent(int index) {
        Maillon<Maillon<E>> pres = elements;
        Maillon<Maillon<E>> prec = pres;
        int i =0;
        while(i < index) {
            prec = pres;
            pres = pres.suivant();
            i++;
        }
        return prec;
    }  
    private Maillon<Maillon<E>> listeElementPresent(Maillon<Maillon<E>> listeElementPrecedent) {
        return listeElementPrecedent.suivant();
    }
    
    /* Nous retourn la taille de la liste d'element */
    /* Retourn -1 si la liste passer est null       */
    private int tailleListeElements(Maillon<Maillon<E>> listeMaillon) {
        if(listeMaillon != null) {
            Maillon<E> m = listeMaillon.info();
            int compteur = 0;
            while(m != null) {
                compteur++;
                m = m.suivant();
            }
            return compteur;
        }
        return -1;
    }
    
    /* Retourne la arrayList d'element avec aucun doublons. */
    private ArrayList<E> enleverDoublons(ArrayList<E> listeElements) {
        for(int i = 0; i < listeElements.size(); i++) {
            E e1 = listeElements.get(i);
            for(int j = i + 1; j < listeElements.size(); j++) {
                E e2 = listeElements.get(j);
                if(e1 == e2) {
                    listeElements.remove(j);
                }
            }
        }
        return listeElements;
    }
}
