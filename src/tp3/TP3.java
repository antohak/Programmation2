/**
 * Antoine Hakim HAKA24019407
 * Pierrick Michel MICP17049305
 * 
 * Sigle du cours : INF2120
 * Groupe : 30
 * Nom du professeur : Mélanie Lord
 *
 * @authors Antoine Hakim
 *          ant.hakim.stud@Gmail.com
 *          Pierrick Michel
 *          LePmnin@Gmail.Com
 *
 * @description : Travail Pratique 03
 *                  Création de programme de gestion de collections
 *                  avec interface graphique
 *
 * @class : TP3
 * @classDescription : Programme de gestion de collections avec interface graphique
 */

package tp3;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class TP3 extends WindowAdapter implements ActionListener {

   /**
    ***********************************
    * CONSTANTES DE CLASSE
    ***********************************
    */
   //largeur de l'ecran de l'ordinateur
   public final static int LARG_ECRAN = Toolkit.getDefaultToolkit().getScreenSize().width;

   //hauteur de l'ecran de l'ordinateur
   public final static int HAUT_ECRAN = Toolkit.getDefaultToolkit().getScreenSize().height;

   //Largeur de la fenetre principale
   public final static int LARG_FENETRE = 550;

   //hauteur de la fenetre principale
   public final static int HAUT_FENETRE = 530;

   //largeur du conteneur au milieu
   public final static int LARG_CONTENEUR = 500;
   
   //hauteur du conteneur au milieu
   public final static int HAUT_CONTENEUR = 300;

   //position X des labels
   public final static int X_POS_LABELS = LARG_FENETRE / 2 - LARG_CONTENEUR / 2;
   
   //Espace entre l'ecran et les panneau
   public final static int PADDING_X = 15;
   
   public final static int PADDING_Y = 15;
   
   //titre de la fenetre principale
   public final static String TITRE_FENETRE = "COLLECTION DE VIDEOS";
   
   //couleur de la fenetre principal et des champs non editable
   public final static Color GRIS = new Color(238, 238, 238);
   
   public final static Color GRIS_FONCE = new Color(171, 171, 171);

   //fichiers texte contentant la comboCollection de videos
   public final static String FIC_VIDEOS = "videos.txt";


   /**
    ***********************************
    * COMPOSANTS GRAPHIQUES
    ***********************************
    */
   
   //La fenetre principale
   private JFrame fenetre = new JFrame(TITRE_FENETRE);
   
   //Tableaux de Strings
   private String[] labels_text = new String [] {"Collection", "Mode", "Titre", "Annee", "Type", "Evaluation", "Commentaires", "Categorie(s)"};
   private String[] radio_text = new String [] {"Consultation", "Ajout", "Modification", "Recherche"};
   private String[] button_text = new String [] {"Precedent", "Suivant", "Ajouter", "Modifier", "Supprimer", "Rechercher"};
   //Panneaux
   private JPanel haut = new JPanel();
   private JPanel milieu = new JPanel();
   private JPanel bas = new JPanel();
   //Components du panneau de haut sauf pour labels qui sont pour haut et millieu
   private JLabel[] labels = new JLabel[8];
   private JRadioButton[] mode = new JRadioButton [4];
   //Collection de films
   private JComboBox comboCollection = new JComboBox();
   //Components du panneau milleu
   private JComboBox comboType = new JComboBox();
   private JComboBox comboEval = new JComboBox();
   private JTextArea textCommentaires = new JTextArea();
   private JTextArea textCategories = new JTextArea();
   private JTextField textTitre = new JTextField();
   private JTextField textAnnee = new JTextField();
   private JButton[] modeButton = new JButton [6];
   
   private JScrollPane scrollCom = new JScrollPane(textCommentaires, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
   private JScrollPane scrollCat = new JScrollPane(textCategories, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
   
   //Liste deroulante
   private JComboBox collection = new JComboBox();
   
   private JButton[] optionCategories = new JButton[2];
   private JLabel[] infos_film = new JLabel[4];
   
   JPanel popUpSelection = new JPanel();
   JTextField nouvelleCategorie = new JTextField();
   JComboBox selectionCategorie = new JComboBox();
   
   private IListeAssociative<String, Video> liste;
   
   public TP3() {
       //Init de Model
       initModele();
       
       //Init de View
       init();
   }
   
   //Savoir si la collection est vide
   public boolean listeEstVide() {
       return liste.estVide();
   }
   
   //Main init
   private void init() {
      fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      fenetre.setBounds(LARG_ECRAN / 2 - LARG_FENETRE / 2,
              HAUT_ECRAN / 2 - HAUT_FENETRE / 2,
              LARG_FENETRE, HAUT_FENETRE);
      fenetre.setResizable(false);
      fenetre.setLayout(null);
      
      //ajout d'un ecouteur a la fenetre
      fenetre.addWindowListener(this);

      initPanneauHaut();
      initPanneauMillieu();
      initPanneauBas();
      
      initLabels();
      
      initPanneauHautComponents();
      initPanneauMillieuComponents();
      initPanneauBasComponents();
      ajouterAFenetre();
      
      modeConsultation();
      
      ajouterActionListener();
      
      //derniere instruction
      fenetre.setVisible(true);
   }
   
   @Override
   public void actionPerformed(ActionEvent e) {
       if(e.getSource() instanceof JRadioButton) {
           gererModeChoisis(e);
       } else if(e.getSource() instanceof JButton) { 
           gererEventBouttons(e);
       } else if(e.getSource() instanceof JComboBox) {
           String titre = (String) comboCollection.getSelectedItem();
           if(titre != null) {
               afficherFilmChoisis(titre);
           }
       }
   }
   
   @Override
   public void windowClosing(WindowEvent e) {
       try {
           sauvegarder();
       } catch (Exception ex) {
           ex.printStackTrace();
       }
   }
   
/******************************************/
/* Methodes qui handle les different type */
/* de boutton, field, et mode choisis ou  */
/* cliquer                                */
/******************************************/
   
    /*public void afficherLabels(String titre, int annee, String type, String eval) {
        infos_film[0].setText(titre);
        infos_film[1].setText(String.valueOf(annee));
        infos_film[2].setText(type);
        infos_film[3].setText(eval);
    } */
    /**
     * Gere les differents types de mode choisis
     * @param e
     */
    public void gererModeChoisis(ActionEvent e) {
       if(e.getSource() == mode[0]) { //Mode consultation
           modeConsultation();
           mode[1].setSelected(false);
           mode[2].setSelected(false);
           mode[3].setSelected(false);
       } else if(e.getSource() == mode[1]) { //Mode ajout
           modeAjout();
           mode[0].setSelected(false);
           mode[2].setSelected(false);
           mode[3].setSelected(false);
       } else if(e.getSource() == mode[2]) { //Mode modification
           modeModification();
           mode[0].setSelected(false);
           mode[1].setSelected(false);
           mode[3].setSelected(false);
       } else if(e.getSource() == mode[3]) { //Mode recherche
           modeRecherche();
           mode[0].setSelected(false);
           mode[1].setSelected(false);
           mode[2].setSelected(false);
       }
   }
    public void resetLabels() {
        for(int k = 0; k < infos_film.length; k++) {
           infos_film[k].setText("");
        }
    }
    public void afficherFilmChoisis(String titre) {
        Video film = this.obtenirVideo(titre);
        String catos = this.obtenirCategoriesEnString(comboCollection.getItemAt(comboCollection.getSelectedIndex()).toString());
        if(mode[0].isSelected()) {
            String filmOuSerie = "SERIE TV";
            String eval = String.valueOf(film.getEval()+1) + " etoiles";
            if(film.isFilm()){
                filmOuSerie = "FILM";
            }
            infos_film[0].setText(titre);
            infos_film[1].setText(String.valueOf(film.getAnnee()));
            infos_film[2].setText(filmOuSerie);
            infos_film[3].setText(eval);
        } else {
            textTitre.setText(film.getTitre());
            textAnnee.setText(String.valueOf(film.getAnnee()));
            comboEval.setSelectedIndex(film.getEval() + 1);
            boolean isFilm = film.isFilm();
            if(isFilm) {
                comboType.setSelectedIndex(1);
            } else {
                comboType.setSelectedIndex(2);
            }
        }
        textCommentaires.setText(film.getCommentaires());
        textCategories.setText(catos);
    }
    
    public void gererEventBouttons(ActionEvent e) {
        if(e.getSource() == modeButton[0]) { //Boutton precedent
            //Verify
            if(comboCollection.getItemAt(comboCollection.getSelectedIndex() - 1) == null){
                //modeButton[0].setVisible(false);
                modeButton[0].setEnabled(false);
                //messageErreur("Il n'y a pas d'element precedent a la collection");
                
            } else {
                comboCollection.setSelectedIndex(comboCollection.getSelectedIndex() - 1);
                //Get
                Video video = this.obtenirVideo(comboCollection.getItemAt(comboCollection.getSelectedIndex()).toString()); //String titre
                String catos = this.obtenirCategoriesEnString(comboCollection.getItemAt(comboCollection.getSelectedIndex()).toString());
                afficherFilmChoisis(video.getTitre());
                //Place
                /*String filmOuSerie = "SERIE TV";
                String eval = String.valueOf(video.getEval()+1) + " etoiles";
                if(video.isFilm()){
                    filmOuSerie = "FILM";
                }*/
                //afficherLabels(video.getTitre(), video.getAnnee(), filmOuSerie, eval);
                //textCommentaires.setText(video.getCommentaires());
                //textCategories.setText(catos);
                modeButton[1].setVisible(true);
                modeButton[1].setEnabled(true);
                if(comboCollection.getItemAt(comboCollection.getSelectedIndex() - 1) == null){
                    modeButton[0].setEnabled(false);
                }
            }
            
        } else if(e.getSource() == modeButton[1]) { //Boutton suivant
            //Verify
            if(comboCollection.getItemAt(comboCollection.getSelectedIndex() + 1) == null){
                //modeButton[1].setVisible(false);
                modeButton[1].setEnabled(false);
                //messageErreur("Il n'y a pas d'element suivant a la collection");
            } else{
                comboCollection.setSelectedIndex(comboCollection.getSelectedIndex() + 1);
                //Get
                Video video = this.obtenirVideo(comboCollection.getItemAt(comboCollection.getSelectedIndex()).toString()); //String titre
                String catos = this.obtenirCategoriesEnString(comboCollection.getItemAt(comboCollection.getSelectedIndex()).toString());
                afficherFilmChoisis(video.getTitre());
                //Place
                /*String filmOuSerie = "SERIE TV";
                String eval = String.valueOf(video.getEval()+1) + " etoiles";
                if(video.isFilm()){
                    filmOuSerie = "FILM";
                }*/
                //afficherLabels(video.getTitre(), video.getAnnee(), filmOuSerie, eval);
                //textCommentaires.setText(video.getCommentaires());
                //textCategories.setText(catos);
                modeButton[0].setVisible(true);
                modeButton[0].setEnabled(true);
                if(comboCollection.getItemAt(comboCollection.getSelectedIndex() + 1) == null){
                    modeButton[1].setEnabled(false);
                }
            }
        } else if(e.getSource() == modeButton[2]) { //Boutton ajouter
            //Verify
            if(textTitre.getText() == null || textTitre.getText().equals("")){
                messageErreur("Pas de titre");
            } else if(textAnnee.getText() == null || textAnnee.getText().equals("") || !textAnnee.getText().matches("^[0-9]+$")){
                messageErreur("Pas d'annee ou annee NaN");
            } else if(textCommentaires.getText() == null){
                messageErreur("Champs commentaires null");
            } else if(textCategories.getText() == null){
                messageErreur("Champs categories null");
            } else{
                boolean type;
                if(comboType.getSelectedIndex() == 0){
                    messageErreur("Pas de type");
                    type = false;
                } else {
                    if(comboType.getSelectedIndex() == 1){
                    type = true;
                    } else{
                        type = false;
                    }
                    int eval;
                    if(comboEval.getSelectedIndex() == 0){
                        messageErreur("Pas d'evaluation");
                    } else{
                        eval = comboEval.getSelectedIndex();

                        //Place
                        this.ajouterVideo(textTitre.getText(), Integer.parseInt(textAnnee.getText()), type, eval, textCommentaires.getText(), textCategories.getText()); //String titre, int annee, boolean type, int eval, String comments, String categories
                    }
                }
            }
            
        } else if(e.getSource() == modeButton[3]) { //Boutton modifier
            //Verify
            System.out.println(textTitre.getText());
            System.out.println(textAnnee.getText());
            System.out.println(textCommentaires.getText());
            if(textTitre.getText() == null || textTitre.getText().equals("")){
                messageErreur("Pas de titre");
            } else if(textAnnee.getText() == null || textAnnee.getText().equals("") || !textAnnee.getText().matches("^[0-9]+$")){
                messageErreur("Pas d'annee ou annee NaN");
            } else if(textCommentaires.getText() == null){
                messageErreur("Champs commentaires null");
            } else if(textCategories.getText() == null){
                messageErreur("Champs categories null");
            } else{

                int eval;
                if(comboEval.getSelectedIndex() == 0){
                    messageErreur("Pas d'evaluation");
                } else{
                    eval = comboEval.getSelectedIndex();

                    boolean type;
                    if(comboType.getSelectedIndex() == 0){
                        messageErreur("Pas de type");
                        type = false;
                    } else if(comboType.getSelectedIndex() == 1){
                        type = true;
                    } else{
                        type = false;
                    }
                    //Place
                    if(this.modifierVideo(textTitre.getText(), Integer.parseInt(textAnnee.getText()), eval, type, textCommentaires.getText(), textCategories.getText())){ //String titre, int annee, int eval, boolean type, String comments, String categories
                        JOptionPane.showMessageDialog(fenetre, "Élément " + comboCollection.getSelectedItem().toString() + " modifié!");
                    }
                    else{
                        JOptionPane.showMessageDialog(fenetre, "Élément " + comboCollection.getSelectedItem().toString() + " n'a pas été modifié!");
                    }
                }
            }
            
        } else if(e.getSource() == modeButton[4]) { //Boutton supprimer
            //Verify ... nothing...
            
            //Place
            if(this.supprimerVideo(comboCollection.getSelectedItem().toString())) {
                JOptionPane.showMessageDialog(fenetre, comboCollection.getSelectedItem().toString() + " a ete retirer de la collection!");
            } else {
                messageErreur("Le film n'a pas ete supprimer!");
            }
            System.out.println(obtenirVideo(comboCollection.getSelectedItem().toString()));
            
             //String titre
            
        } else if(e.getSource() == modeButton[5]) { //Boutton rechercher
            
            int eval = comboEval.getSelectedIndex() - 1;
            /*
            -1:  Pas de recherche par evaluation
             0:  0 étoile
             1:  1 étoile
             2:  2 étoiles
             3:  3 étoiles
             4:  4 étoiles
             5:  5 étoiles
            */    
            
            /*
            -1: Pas de recherche par type
             0: FILM
             1: SERIE TV
            */
            int type = comboType.getSelectedIndex() - 1;
            
            ArrayList<Video> videosTrouver = new ArrayList<Video>();

            if(!textAnnee.getText().equals("")){
                videosTrouver = rechercherVideos(textTitre.getText(), Integer.parseInt(textAnnee.getText()), eval, type, textCategories.getText());
            }else{
                videosTrouver = rechercherVideos(textTitre.getText(), -1, eval, type, textCategories.getText());
            }

            comboCollection.removeAllItems();
            ArrayList itemsInComboCollection = new ArrayList();
            for(Video video : videosTrouver){
                if(!itemsInComboCollection.contains(video)){
                    itemsInComboCollection.add(video);
                    comboCollection.addItem(video.getTitre());
                }
            }
            comboCollection.setEnabled(true);
            
        } else if(e.getSource() == optionCategories[0]) { //Boutton ajouter categorie
            //Get     
                String [] categoriesDuFilm = textCategories.getText().split("\n");
                ArrayList listeDeCategories = ArrayToArrayList(categoriesDuFilm);
                if(listeDeCategories.get(0).equals("")) {
                    listeDeCategories.clear();
                }
                selectionCategorie.removeAllItems();
                ArrayList cater = new ArrayList();
                for(String categorie : liste.obtenirCles()){
                    if(!cater.contains(categorie)){
                        cater.add(categorie);
                        selectionCategorie.addItem(categorie);
                    }
                }
                popUpSelection.add(selectionCategorie);
                fenetre.getContentPane().add(popUpSelection);
                int action = JOptionPane.showConfirmDialog(null, popUpSelection, "Categorie", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

                switch(action) {
                    case 0 :
                        if(((String)selectionCategorie.getSelectedItem()).equals("Autres...")) {
                            popUpSelection.remove(selectionCategorie);
                            String categorieEntree = JOptionPane.showInputDialog(popUpSelection, "Ajouter une categorie");
                            if(categorieEntree != null) {
                                if(!categorieExiste(listeDeCategories, categorieEntree)) {
                                    System.out.println(listeDeCategories);
                                    if(listeDeCategories.isEmpty()) {
                                        textCategories.setText(categorieEntree);
                                    } else {
                                        System.out.println("ici");
                                        textCategories.setText(textCategories.getText() + "\n" + categorieEntree);
                                    }
                                    optionCategories[1].setEnabled(true);
                                } else {
                                    messageErreur("Categorie existe deja.");
                                }
                            } else {
                                messageErreur("Entree null ou vide!");
                            }
                        } else {
                            if(!categorieExiste(listeDeCategories, (String) selectionCategorie.getSelectedItem())) {
                                if(listeDeCategories.isEmpty()) {
                                    textCategories.setText(selectionCategorie.getSelectedItem().toString());
                                } else {
                                    textCategories.setText(textCategories.getText() + "\n" + (String) selectionCategorie.getSelectedItem());
                                }
                            } else {
                                messageErreur("Categorie existe deja.");
                            }
                        }
                        break;
                    case 1:
                        break;
                } 
        } else if(e.getSource() == optionCategories[1]) { //Boutton supprimer categorie
            //Get
            String [] categoriesDuFilm = textCategories.getText().trim().split("\\n");
            ArrayList listeDeCategories = ArrayToArrayList(categoriesDuFilm);
            selectionCategorie.removeAllItems();
            for(Object categorie : listeDeCategories){
                selectionCategorie.addItem(categorie.toString());
            }
            popUpSelection.add(selectionCategorie);
            fenetre.getContentPane().add(popUpSelection);
            int action = JOptionPane.showConfirmDialog(null, popUpSelection, "Categorie", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            switch(action) {
                case 0 :
                    String categorieASupprimer = (String) selectionCategorie.getSelectedItem();  
                    String output = "";
                    if(!listeDeCategories.isEmpty()) {
                        listeDeCategories.remove(categorieASupprimer);
                        for(int i = 0; i < listeDeCategories.size(); i++) {
                            output += (String) listeDeCategories.get(i);
                            if(i + 1 != listeDeCategories.size()) {
                                output += "\n";
                            }
                        }
                        textCategories.setText(output);
                    } else {
                        optionCategories[1].setEnabled(false);
                    }
                    break;
                case 1:
                    break;
            }            
        }
    }

    public JComboBox ajouterCategoriesDansListeDeroulante(JComboBox liste, ArrayList categories, int option) {
        //Option 0 c'est parce qu'on est sur sur l'option d'ajouter une categorie
        //L'option 1 c'est parce qu'on est sur l'option de supprimer une categorie
        if(option == 0) {
            liste.addItem("Autres...");
        }
        return liste;
    }
    public ArrayList ArrayToArrayList(String [] liste) {
        ArrayList listeTransformer = new ArrayList();
        for(Object element : liste) {
            listeTransformer.add( (String) element);
        }
        return listeTransformer;
    }
    
    public boolean categorieExiste(ArrayList liste, String categorie) {
        if(liste.contains(categorie)) {
            return true;
        }
        return false;
    }
    
    public void resetCategorie() {
        selectionCategorie.removeAllItems();
    }
    
    public void loadCategorie() {
        ArrayList<String> collectionCategorie = liste.obtenirCles();
        collectionCategorie = removeNulls(collectionCategorie);
        collectionCategorie = removeDuplicates(collectionCategorie);
        for(String categorie : collectionCategorie) {
            selectionCategorie.addItem(categorie);
            //System.out.println(categorie);
        }
        selectionCategorie.addItem("Autres...");
    }
/****************************************/
/* Les 5 type de mode: Depart,          */
/* Cosultation, Ajout, Modification et  */
/* Recherche.                           */
/****************************************/
   
   //Mode consultation
   public void modeConsultation() {
       resetCollection();
       loadCollection();
       comboCollection.setEnabled(true);
       
       textTitre.setVisible(false);
       textTitre.setEditable(false);
       textAnnee.setVisible(false);
       textAnnee.setEditable(false);
       comboType.setVisible(false);
       comboType.setEnabled(false);
       comboEval.setVisible(false);
       comboEval.setEnabled(false);
       
       //Juste au cas que ca foire
       scrollCom.setEnabled(true);
       scrollCat.setEnabled(true);
      
       textCommentaires.setEnabled(true);
       textCommentaires.setEditable(false);
       textCategories.setEnabled(true);
       textCategories.setEditable(false);
       
       textCommentaires.setBackground(GRIS);
       textCategories.setBackground(GRIS);
       
       for(int k = 0; k < infos_film.length; k++) {
           infos_film[k].setVisible(true);
       }
       
       //Place the first element of combobox in the grid DEPRECATED by Enoncé
       comboCollection.setSelectedIndex(comboCollection.getSelectedIndex());
       
       for(int l = 0; l < modeButton.length; l++) {
           if(l < 2) {
               modeButton[0].setEnabled(false);
               modeButton[0].setVisible(true);
               if(comboCollection.getItemCount() > 1){
                   modeButton[l].setEnabled(true);
                   modeButton[l].setVisible(true);
               }else{
                   modeButton[l].setEnabled(false);
                   modeButton[l].setVisible(true);
               }
               //modeButton[l].setEnabled(true);
               //modeButton[l].setVisible(true);
           } else {
               modeButton[l].setEnabled(false);
               modeButton[l].setVisible(false);
           }
       }
       optionCategories[0].setEnabled(false);
       optionCategories[1].setEnabled(false);
       
       //Get the collection and place in combobox
       //this.obtenirCollection();
       
       Video video = this.obtenirVideo(comboCollection.getSelectedItem().toString());
       String catos = this.obtenirCategoriesEnString(comboCollection.getSelectedItem().toString());
       afficherFilmChoisis(video.getTitre());
   }
   
   //Mode ajout
   public void modeAjout() {
       resetCollection();
       loadCollection();
       resetCategorie();
       loadCategorie();
       comboCollection.setEnabled(false);
       
       textTitre.setVisible(true);
       textTitre.setEnabled(true);
       textTitre.setEditable(true);
       textTitre.setText("");
       textTitre.requestFocusInWindow();
       
       textAnnee.setVisible(true);
       textAnnee.setEnabled(true);
       textAnnee.setEditable(true);
       textAnnee.setText("");
       
       comboType.setVisible(true);
       comboType.setEnabled(true);
       comboType.setSelectedIndex(1);
       
       comboEval.setVisible(true);
       comboEval.setEnabled(true);
       comboEval.setSelectedIndex(0);
       
       //Juste au cas que ca foire
       scrollCom.setEnabled(true);
       scrollCat.setEnabled(true);
       
       textCommentaires.setEnabled(true);
       textCommentaires.setEditable(true);
       textCommentaires.setBackground(Color.WHITE);
       textCommentaires.setText("");
       
       textCategories.setEnabled(true);
       textCategories.setEditable(false);
       textCategories.setBackground(GRIS);
       textCategories.setText("");
       
       optionCategories[0].setEnabled(true);
       optionCategories[1].setEnabled(false);
       
       for(int i = 0; i < infos_film.length; i++) {
           infos_film[i].setVisible(false);
       }

       for(int k = 0; k < modeButton.length; k++) {
           if(k == 2) {
               modeButton[k].setEnabled(true);
               modeButton[k].setVisible(true);
           } else {
               modeButton[k].setEnabled(false);
               modeButton[k].setVisible(false);
           }
       }      
   }
   
   //Mode modification
   public void modeModification() {
       resetCollection();
       loadCollection();
       resetCategorie();
       loadCategorie();
       comboCollection.setEnabled(true);
       
       textTitre.setVisible(true);
       textTitre.setEnabled(true);
       textTitre.setEditable(true);
       
       textAnnee.setVisible(true);
       textAnnee.setEnabled(true);
       textAnnee.setEditable(true);
       
       comboType.setVisible(true);
       comboType.setEnabled(true);
       
       comboEval.setVisible(true);
       comboEval.setEnabled(true);
       
       scrollCom.setEnabled(true);
       scrollCat.setEnabled(true);
       
       textCommentaires.setEnabled(true);
       textCommentaires.setEditable(true);
       textCommentaires.setBackground(Color.WHITE);
       textCommentaires.setText("");
       
       textCategories.setEnabled(true);
       textCategories.setEditable(false);
       textCategories.setBackground(GRIS);
       textCategories.setText("");
       
       for(int i = 0; i < infos_film.length; i++) {
           infos_film[i].setVisible(false);
       }
       
       for(int k = 0; k < modeButton.length; k++) {
            modeButton[k].setEnabled(false);
            modeButton[k].setVisible(false);
       }
       
       modeButton[3].setVisible(true);//Boutton Modifier
       modeButton[4].setVisible(true);//Boutton Supprimer
       boolean enabled = true;
       if(listeEstVide()) {
           enabled = false;
       } 
       modeButton[3].setEnabled(enabled);
       modeButton[4].setEnabled(enabled);
       
       //Get the collection and place in combobox
       //this.obtenirCollection();
 
       optionCategories[0].setEnabled(true); //Boutton ajouter categorie
       optionCategories[1].setEnabled(true);
       
        //Place the first element of combobox in the grid
       comboCollection.setSelectedIndex(0);
       Video video = this.obtenirVideo(comboCollection.getSelectedItem().toString());
       //String catos = this.obtenirCategoriesEnString(comboCollection.getSelectedItem().toString());
       afficherFilmChoisis(video.getTitre());
   }
   
   //Mode recherche
   public void modeRecherche() {
       resetCollection();
       //loadCollection();
       //resetCategorie();
       //loadCategorie();
       
       comboCollection.setEnabled(false);
       
       textTitre.setVisible(true);
       textTitre.setEnabled(true);
       textTitre.setEditable(true);
       textTitre.setText("");
       textTitre.requestFocusInWindow();
       
       textAnnee.setVisible(true);
       textAnnee.setEnabled(true);
       textAnnee.setEditable(true);
       textAnnee.setText("");
       
       comboType.setVisible(true);
       comboType.setEnabled(true);
       comboType.setSelectedIndex(0);
       
       comboEval.setVisible(true);
       comboEval.setEnabled(false);
       comboEval.setSelectedIndex(0);
       
       textCommentaires.setEnabled(true);
       textCommentaires.setEditable(false);
       textCommentaires.setBackground(GRIS);
       textCommentaires.setText("");
       
       textCategories.setEnabled(true);
       textCategories.setEditable(false);
       textCategories.setBackground(GRIS);
       textCategories.setText("");
       
       for(int i = 0; i < infos_film.length; i++) {
           infos_film[i].setVisible(false);
       }
       
       for(int k = 0; k < modeButton.length; k++) {
            modeButton[k].setEnabled(false);
            modeButton[k].setVisible(false);
       }
       modeButton[5].setVisible(true);
       
       boolean enabled = true;
       
       if(listeEstVide()) {
           enabled = false;
       }
       optionCategories[0].setEnabled(enabled);
       optionCategories[1].setEnabled(enabled);
       modeButton[5].setEnabled(enabled);   
   }

/****************************************/
/*    Initialization des panneaux       */
/****************************************/
   
   //initialize panneau du haut
   public void initPanneauHaut() {
       final int X = PADDING_X + 10;
       haut.setLayout(null);
       haut.setBounds(X, 15, LARG_FENETRE - (2*X), 80);
   }
   
   //initialize panneau du millieu
   public void initPanneauMillieu() {
       final int X = PADDING_X + 10;
       final int LARG_PANNEAU_MILLEU = LARG_FENETRE - (2*X);
       final int HAUT_PANNEAU_MILLEU = 300;
       milieu.setLayout(null);           
       milieu.setBounds(X, haut.getY() + haut.getHeight(), LARG_PANNEAU_MILLEU, HAUT_PANNEAU_MILLEU);
       milieu.setBorder(new LineBorder(GRIS_FONCE, 2));
   }
   
   //initialize panneau du bas
   public void initPanneauBas() {
       final int X = PADDING_X + 10;
       final int LARG_PANNEAU_BAS = LARG_FENETRE - (2*X);
       final int HAUT_PANNEAU_BAS = HAUT_FENETRE - haut.getHeight() - milieu.getHeight() - PADDING_Y - 50;
       final int VGAP = HAUT_PANNEAU_BAS / 2;
       
       FlowLayout flowlayout = new FlowLayout();
       flowlayout.setVgap(VGAP);
       
       bas.setLayout(flowlayout);
       bas.setBounds(X, milieu.getY() + milieu.getHeight(), LARG_PANNEAU_BAS, HAUT_PANNEAU_BAS);
   }

   /*********************************/
   /* Initialization des components */ 
   /*       des panneaux            */  
   /*********************************/
   
   //panneau du haut
   public void initPanneauHautComponents() {
       int y_Pos = labels[1].getY() - 5;
       
       comboCollection = new JComboBox();
       comboCollection.setBounds(labels[0].getX() + labels[0].getWidth(), labels[0].getY(), 
                            haut.getWidth() - (labels[0].getX() + labels[0].getWidth()), 20);

       mode[0] = new JRadioButton(radio_text[0]);
       mode[0].setBounds(labels[1].getX() + labels[1].getWidth(), y_Pos, 100, 30);
       mode[0].setSelected(true);

       mode[1] = new JRadioButton(radio_text[1]);
       mode[1].setBounds(mode[0].getX() + mode[0].getWidth() + 5, y_Pos, 60, 30);
       
       mode[2] = new JRadioButton(radio_text[2]);
       mode[2].setBounds(mode[1].getX() + mode[1].getWidth() + 5, y_Pos, 100, 30);
       
       mode[3] = new JRadioButton(radio_text[3]);
       mode[3].setBounds(mode[2].getX() + mode[2].getWidth() + 5, y_Pos, 100, 30);
       
       haut.add(comboCollection);
       
       for(int j = 0; j < mode.length; j++) {
           haut.add(mode[j]);
       }
   }   

   //panneau millieu
   public void initPanneauMillieuComponents() {
       int x_Pos = milieu.getX() + 100;
       
       //textTitre = new JTextField();
       textTitre.setBounds(x_Pos, labels[2].getY() - 5, 360, 25);
       infos_film[0] = new JLabel();
       infos_film[0].setBounds(x_Pos, labels[2].getY() - 5, 360, 25);
       
       //textAnnee = new JTextField();
       textAnnee.setBounds(x_Pos, labels[3].getY() - 5, 360, 25);
       infos_film[1] = new JLabel();
       infos_film[1].setBounds(x_Pos, labels[3].getY() - 5, 360, 25);
       
       //comboType = new JComboBox();
       comboType.addItem("");
       comboType.addItem("FILM");
       comboType.addItem("SERIE TV");
       comboType.setBounds(x_Pos, labels[4].getY(), 360, 20);
       infos_film[2] = new JLabel();
       infos_film[2].setBounds(x_Pos, labels[4].getY(), 360, 20);
       
       //comboEval = new JComboBox();
       comboEval.addItem("");
       comboEval.addItem("0 etoile");
       comboEval.addItem("1 etoile");
       comboEval.addItem("2 etoiles");
       comboEval.addItem("3 etoiles");
       comboEval.addItem("4 etoiles");
       comboEval.addItem("5 etoiles");
       comboEval.setBounds(x_Pos, labels[5].getY(), 360, 20);
       infos_film[3] = new JLabel();
       infos_film[3].setBounds(x_Pos, labels[5].getY(), 360, 20);

       scrollCom.setBounds(x_Pos, labels[6].getY(), 360, 65);
       scrollCom.setBorder(new LineBorder(GRIS_FONCE, 1));
       
       scrollCat.setBounds(x_Pos, labels[7].getY(), 180, 70);
       scrollCat.setBorder(new LineBorder(GRIS_FONCE, 1));
       
       textCommentaires.setLineWrap(true);
       textCommentaires.setWrapStyleWord(true);
       
       optionCategories[0] = new JButton("Ajouter categorie");
       optionCategories[0].setBounds(scrollCat.getX() + scrollCat.getWidth() + 15, scrollCat.getY() + 5, 155, 20);
       
       optionCategories[1] = new JButton("Supprimer categorie");
       optionCategories[1].setBounds(scrollCat.getX() + scrollCat.getWidth() + 15, scrollCat.getY() + 45, 155, 20);
       
       for(int i = 0; i < infos_film.length; i++) {
           milieu.add(infos_film[i]);
       }
       
       milieu.add(textTitre);
       milieu.add(textAnnee);
       milieu.add(comboType);
       milieu.add(comboEval);
       milieu.add(scrollCom);
       milieu.add(scrollCat);
       milieu.add(optionCategories[0]);
       milieu.add(optionCategories[1]);
   }
   
   //panneau du bas
   public void initPanneauBasComponents() {
       for(int i = 0; i < modeButton.length; i++) {
           modeButton[i] = new JButton(button_text[i]);
           bas.add(modeButton[i]);
       }
   }
   
   //labels
   public void initLabels() {
       int w = 90;
       int h = 20;
       for(int i = 0; i < labels.length; i++) {
           if(i == 0) {
               labels[i] = new JLabel(labels_text[i]);
               labels[i].setBounds(0, 20, w, h);
               haut.add(labels[i]);
           }
           else if(i < 2) {
               labels[i] = new JLabel(labels_text[i]);
               labels[i].setBounds(0, labels[i-1].getY() + 30, w, h);
               haut.add(labels[i]);
           } else {
               if(i == 2) {
                   labels[i] = new JLabel(labels_text[i]);
                   labels[i].setBounds(milieu.getX() - 10, milieu.getY() - 70, w, h);
               } else if(i == 7) {
                   labels[i] = new JLabel(labels_text[i]);
                   labels[i].setBounds(labels[i-1].getX(), labels[i-1].getY() + 75, w, h);
               } else {
                   labels[i] = new JLabel(labels_text[i]);
                   labels[i].setBounds(labels[i-1].getX(), labels[i-1].getY() + 30, w, h);
               }
               milieu.add(labels[i]);
           }
       }
   }  
   
   //MODEL
   private void initModele(){
       liste = new ListeAssociativeChainee<>();
       
       try {
           charger();
           System.out.println("Liste chargée");
       } catch (Exception ex) {
           System.out.println(ex.getMessage());
       }
   }
   
   public void resetCollection() {
       comboCollection.removeAllItems();
       //System.out.println();
   }
   
   public void loadCollection() {
       ArrayList<Video> collections = obtenirCollection();
       for(Video video : collections) {
           comboCollection.addItem(video.getTitre());
       }
   }
   
   //listeners 
   public void ajouterActionListener() {
       for(int i = 0; i < mode.length; i++) {
           mode[i].addActionListener(this);
       }
       for(int j = 0; j < modeButton.length; j++) {
           modeButton[j].addActionListener(this);
       }
       optionCategories[0].addActionListener(this);
       optionCategories[1].addActionListener(this);
       comboCollection.addActionListener(this);
   }
   
   //Ajout des panneaux a la fenetre principale
   public void ajouterAFenetre() {
       fenetre.getContentPane().add(haut);
       fenetre.getContentPane().add(milieu);
       fenetre.getContentPane().add(bas);
   }
   
   //main
   public static void main(String[] args) {
      new TP3();
   }
   
   //Big bunch of getters
   
   //Obtenir toutes les videos
   public ArrayList<Video> obtenirCollection(){
       //Obtenir toutes les videos en naviguant dans les categories
       ArrayList<Video> videos = new ArrayList<>();
       ArrayList<String> categories = liste.obtenirCles();
       
       for(String categorie : categories){
           ArrayList<Video> videosOfCategorie = liste.obtenirElements(categorie);
           for(Video vid : videosOfCategorie){
               if(!videos.contains(vid)){
                   videos.add(vid);
               }
           } 
       }
       
       //Return
       return videos;
   }
   
   //Obtenir une video
   public Video obtenirVideo(String titre){
       //Obtenir la video en naviguant dans les categories
       Video video = null;
       ArrayList categories = liste.obtenirCles();

       for(Object categorie : categories){
           ArrayList<Video> videosOfCategorie = liste.obtenirElements((String)categorie);
           
           for(Video vid : videosOfCategorie){
                Video temp = (Video)vid;
                if(temp.getTitre().equals(titre)){
                    video = temp;
                }
           }
       }
       
       //Return
       return video;
   }
   
   //Obtenir le titre d'une video
   public String obtenirTitre(String titre){
       //Obtenir la video
       //Obtenir le titre
       //Return
       return obtenirVideo(titre).getTitre();
   }
   
   //Obtenir l'année de sortie d'une video
   public int obtenirAnnee(String titre){
       //Obtenir la video
       //Obtenir l'annee de sortie
       //Return
       return obtenirVideo(titre).getAnnee();
   }
   
   //Obtenir le type d'une video
   public boolean obtenirType(String titre){
       //Obtenir la video
       //Obtenir le type de la video
       //Return
       return obtenirVideo(titre).isFilm();
   }
   
   //Obtenir l'evaluation d'une video
   public int obtenirEvalutation(String titre){
       //Obtenir la video
       //Obtenir l'evaluation de la video
       //Return
       return obtenirVideo(titre).getEval();
   }
   
   //Obtenir les commentaires d'une video
   public String obtenirCommentaires(String titre){
       //Obtenir la video
       //Obtenir les commentaires sur cette video
       //Return
       return obtenirVideo(titre).getCommentaires();
   }
   
   
   //Obtenir les categories d'une video en String
   public String obtenirCategoriesEnString(String titre){
       //Obtenir tous les <STRING,*> qui contiennent la video
       String categories = "";
       Video video = obtenirVideo(titre);
       ArrayList<String> arrayCategories = liste.obtenirCles();
       
       for(String categorie : arrayCategories){
           if(liste.obtenirElements(categorie).contains(video)){
               categories += categorie + "\n";
           }
       }
       
       //Remove last "\n"?
       if(!categories.equals("")){
           categories = categories.substring(0, categories.length() - 1);
       }
       
       //Return
       return categories;
   }
   
   //Obtenir les categories d'une video en ArrayList
   public ArrayList obtenirCategoriesEnArrayList(String titre){
       //Obtenir tous les <STRING,*> qui contiennent la video
       ArrayList categories = new ArrayList();
       Video video = obtenirVideo(titre);
       ArrayList<String> arrayCategories = liste.obtenirCles();
       
       for(String categorie : arrayCategories){
           if(liste.obtenirElements(categorie).contains(video)){
               categories.add(categorie);
           }
       }
       
       //Return
       return categories;
   }
   
   //Obtenir le nombre de categories
   public int obtenirNombreCategories(String titre){
       //Obtenir tous les <STRING,*> qui contiennent la video
       int categories = 0;
       Video video = obtenirVideo(titre);
       ArrayList<String> arrayCategories = liste.obtenirCles();
       
       for(String categorie : arrayCategories){
           if(liste.obtenirElements(categorie).contains(video)){
               categories++;
           }
       }
       return categories;
   }
   
   //Big bunch of AJOUTS
   public boolean ajouterVideo(String titre, int annee, boolean type, int eval, String comments, String categories){
       try {
           //Creer un objet video avec TITRE ANNEE TYPE EVAL COMMENTS
           Video video = new Video(titre, annee, eval, type);
           video.setCommentaires(comments);
           
           String[] arrayCategories = categories.split(System.getProperty("line.separator"));
           
           /*
           for (int i = 0; i < arrayCategories.length; i++) {
            arrayCategories[i] = arrayCategories[i].replaceAll("[^\\w]", "");
           }
           */
           
           boolean confirmation = false;
           
           //Ajouter cette video dans toutes les categories
           for(String categorie : arrayCategories){
               confirmation = liste.ajouter(categorie, video);
           }
           
           if(confirmation){
               JOptionPane.showMessageDialog(fenetre, video.getTitre() + " ajouté à la liste!");
               return true; 
           }else{
               JOptionPane.showMessageDialog(fenetre, video.getTitre() + " n'a pas été ajouté à la liste!");
               return false; 
           }
            
       } catch (Exception ex) {
           System.out.println(ex.getMessage());
           return false;
       }
   }
   
   public boolean ajouterCategorie(String categorie){
       
       return liste.ajouter(categorie, new ArrayList());
   }
   
   //Big bunch of SETTERS
   
   public boolean modifierVideo(String titre, int annee, int eval, boolean type, String comments, String categories){
       ArrayList<String> arrayCategories = liste.obtenirCles();
       
       Video video;
       try {
           video = new Video(titre, annee, eval, type);
       } catch (Exception ex) {
           System.out.println(ex.getMessage());
           return false;
       }
       
       video.setCommentaires(comments);
       Video oldVideo = obtenirVideo(titre);
       
       int indexOfVideo;
       
       boolean confirmation = false;
       
       for(String categorie : arrayCategories){
           indexOfVideo = liste.obtenirIndex(categorie, oldVideo);
           
           if(indexOfVideo >= 0){
               confirmation = liste.modifier(categorie, video, indexOfVideo);
           }
       }
       
       return confirmation;
   }
   
   //Big bunch of DELETES
   
   public boolean supprimerVideo(String titre){
       Video video = obtenirVideo(titre);
       boolean confirmation = false;
       
       for(String categorie : liste.obtenirCles()){
           if(!confirmation){
               liste.supprimer(categorie, video);
           }
       }
       
       return confirmation;
   }
   
   public boolean supprimerCategorie(String categorie){
       ArrayList list = liste.supprimer(categorie);
       
       if(list == null){
           return true;
       }else{
           return false;
       }
   }
           
   //Big bunch of QUERIES
   
   //Recherche avec les informations dans TITRE, ANNEE, TYPE, EVALUATION, COMMENTAIRES, CATEGORIES en &&
   //Considère les champs vides comme des */ALL
   //Donne un array de videos qui respecte la query
   public ArrayList<Video> rechercherVideos(String titre, int annee, int eval, int type, String categories){
       ArrayList<Video> collection = new ArrayList<Video>();
       ArrayList<Video> correctCollection = new ArrayList<Video>();
       
       for(String categorie : liste.obtenirCles()){
           for(Video video : liste.obtenirElements(categorie)){
               if(!collection.contains(video)){
                   collection.add(video);
               }
           }
       }
       
       correctCollection.addAll(collection);
       
       String[] arrayCategories = categories.split("\\r?\\n");
        /*
       for(int i = 0; i < arrayCategories.length; i++) {
           arrayCategories[i] = arrayCategories[i].replaceAll("[^\\w]", "");
       }
       */
       
       if(!titre.equals("") && titre != null){
           for(Video video : collection){
               if(!video.getTitre().contains(titre)){
                   //correctCollection.add(video);
                   correctCollection.remove(video);
               }
           }
       }
       if(annee != -1){
           for(Video video : collection){
               if(video.getAnnee() != annee){
                   //correctCollection.add(video);
                   correctCollection.remove(video);
               }
           }
       }
       /*
       if(eval != -1){
           for(Video video : collection){
               if(video.getEval() != eval){
                   //correctCollection.add(video);
                   correctCollection.remove(video);
               }
           }
       }
       */
       if(type != -1){
           boolean controle;
           if(type == 0){
               controle = false;
           }else{
               controle = true;
           }
           for(Video video : collection){
               if(video.isFilm() != controle){
                   //correctCollection.add(video);
                   correctCollection.remove(video);
               }
           }
       }
       
       //System.out.println("" + categories);
       
       ArrayList<Video> categoriesCollection = new ArrayList<Video>();
       
       if(!categories.equals("")){
           for(String categorie : arrayCategories){
               for(String cato :  liste.obtenirCles()){
                   System.out.println(cato);
               }
               
               ArrayList<Video> fuck = liste.obtenirElements(liste.obtenirCles().get(liste.obtenirCles().indexOf(categorie)));
               //for(Video video : liste.obtenirElements(liste.obtenirCles().get(liste.obtenirCles().indexOf(categorie)))){
               //for(Video video : liste.obtenirElements(categorie)){
               for(Video video : fuck){
                   if(!categoriesCollection.contains(video)){
                       categoriesCollection.add(video);
                   }
               }
           }
       }
       
       for(Video video : collection){
           if(!categoriesCollection.contains(video)){
               correctCollection.remove(video);
           }
       }
       
       
       return correctCollection;
   }
   
   //TEXT SAVE & LOAD
   
   public void sauvegarder() {
       final String PATH = "./videos.txt";
       File fichier = new File(PATH);
       String formatDeSortie = "";
       
       for(Video video : this.obtenirCollection()){
           String catos = this.obtenirCategoriesEnString(video.getTitre());
           catos = catos.replace("\n", "::");
           String comments = video.getCommentaires();
           //System.out.println(comments);
           comments = comments.replace("\n", "[*]");
           //System.out.println(comments);
           
           formatDeSortie += video.getTitre() + "::";
           formatDeSortie += video.getAnnee() + "::";
           if(video.isFilm()){
               formatDeSortie += "FILM" + "::";
           } else{
               formatDeSortie += "SÉRIE TV" + "::";
           }
           formatDeSortie += video.getEval() + "::";
           
           formatDeSortie += comments; //Comments
           
           formatDeSortie += "::";
           
           formatDeSortie += catos; //Catos
           formatDeSortie += "\r\n"; //Petit fix pour que les new lines append au fichier txt
       }
       
       formatDeSortie = formatDeSortie.substring(0, formatDeSortie.length() - 1);
   
       try {
            FileWriter ecrivain = new FileWriter(fichier);
            ecrivain.write(formatDeSortie);
            ecrivain.close();
       } catch(IOException io) {
            io.getMessage();
       }
   }
   
   public void charger() throws Exception{
       String PATH = "./videos.txt";
       
       ArrayList<String> stringVideos = new ArrayList();
       
       //Regex
       String pattern = "\\[(.*?)\\]";
       String backupPattern = "/\\\\[(.*?)\\\\]/";
       
       Pattern r = Pattern.compile(pattern);
       
       String line = null;
       
       try {
           BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH));
           
           while((line = bufferedReader.readLine()) != null){
               line = line.replace("[*]", "\n");
               stringVideos.add(line);
           }
           bufferedReader.close();
       } catch (FileNotFoundException ex) {
           System.out.println(ex.getMessage());  
       } catch (IOException ex) {
           System.out.println(ex.getMessage());
       }
       
       for(String stringer : stringVideos){
           String[] elements = stringer.split("::");
           String titre = elements[0];
           int annee = Integer.parseInt(elements[1]);   
           boolean type = false;
           
           if(elements[2]/*.toString()*/.equals("FILM")){
               type = true;
           }else if(elements[2]/*.toString()*/.equals("SÉRIE TV")){
               type = false;
           }else{
               System.out.println("Erreur TYPE");
           }
           int eval = Integer.parseInt(elements[3]);
           String commentaires = elements[4];         
           Video video = new Video(titre, annee, eval, type);         
           video.setCommentaires(commentaires);       
           for(int i = 5; i < elements.length; i++){
               //System.out.println("element" + elements[i]);
               liste.ajouter(elements[i], video);
           }
       }
   }
   
   static ArrayList removeDuplicates(ArrayList arrayList) throws NullPointerException{
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
    
   private ArrayList removeNulls(ArrayList arrayList) throws NullPointerException{
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
   
   //Méthode personnelle pour montrer les erreurs graphiquement
    private void messageErreur(String message) {
        JOptionPane.showMessageDialog(null, message, "ERREUR", JOptionPane.ERROR_MESSAGE, UIManager.getIcon("OptionPane.errorIcon"));
    }
    
    //Méthode personnelle pour montrer les erreurs fatales graphiquement
    private void messageErreurFatale(String message) {
        JOptionPane.showMessageDialog(null, message, "ERREUR", JOptionPane.ERROR_MESSAGE, UIManager.getIcon("OptionPane.errorIcon"));
        System.exit(1);
    }
    
    private void messageSuccess(String message) {
        JOptionPane.showMessageDialog(null, message, "SUCCESS", JOptionPane.ERROR_MESSAGE, UIManager.getIcon("OptionPane.errorIcon"));
    }
}