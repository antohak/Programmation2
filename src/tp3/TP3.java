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
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class TP3 extends WindowAdapter implements ActionListener {

   /**
    ***********************************
    * CONSTANTES DE CLASSE
    ***********************************
    */
   //largeur de l'ecran de l'ordinateur
   public final static int LARG_ECRAN
           = Toolkit.getDefaultToolkit().getScreenSize().width;

   //hauteur de l'ecran de l'ordinateur
   public final static int HAUT_ECRAN
           = Toolkit.getDefaultToolkit().getScreenSize().height;

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

   //fichiers texte contentant la collection de videos
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
   private JPanel millieu = new JPanel();
   private JPanel bas = new JPanel();
   
   //Tableaux de components
   private JLabel[] labels = new JLabel[8];
   private JLabel[] infos_film = new JLabel[2];
   private JComponent[] components = new JComponent [8];
   private JRadioButton[] mode = new JRadioButton [4];
   private JButton[] modeButton = new JButton [6];
   
   //Liste deroulante
   private JComboBox collection = new JComboBox();
   
   private IListeAssociative<String, Video> liste;
   
   public TP3() {
       
       //Init de Model
       initModele();
       
       //Init de View
       init();
   }
   
   //Savoir si la liste est vide
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
       gererModeChoisis(e);
   }
   
   @Override
   public void windowClosing(WindowEvent e) {
   }
   
   /******************************************/
   /* Methodes qui handle les different type */
   /* de boutton, field, et mode choisis ou  */
   /* cliquer                                */
   /******************************************/
   
    /**
     * Gere les differents type de mmode choisis
     * @param e
     */
    public void gererModeChoisis(ActionEvent e) {
       if(e.getSource() == mode[0]) {
           modeConsultation();
           mode[1].setSelected(false);
           mode[2].setSelected(false);
           mode[3].setSelected(false);
       } else if(e.getSource() == mode[1]) {
           modeAjout();
           mode[0].setSelected(false);
           mode[2].setSelected(false);
           mode[3].setSelected(false);
       } else if(e.getSource() == mode[2]) {
           modeModification();
           mode[0].setSelected(false);
           mode[1].setSelected(false);
           mode[3].setSelected(false);
       } else if(e.getSource() == mode[3]) {
           modeRecherche();
           mode[0].setSelected(false);
           mode[1].setSelected(false);
           mode[2].setSelected(false);
       }
   }
    
    
    
   /****************************************/
   /* Les 5 type de mode: Depart,          */
   /* Cosultation, Ajout, Modification et  */
   /* Recherche.                           */
   /****************************************/
   
   //Mode consultation
   public void modeConsultation() {
       collection.setEnabled(true);
       for(int i = 0; i < components.length; i++) {
           if( i < 4) {
               components[i].setVisible(false);
           } else {
               components[i].setEnabled(false);
           }
       }
       for(int k = 0; k < infos_film.length; k++) {
           infos_film[k].setVisible(true);
       }
       components[4].setBackground(GRIS);
       components[5].setBackground(GRIS);
       
       for(int l = 0; l < modeButton.length; l++) {
           if(l < 2) {
               modeButton[l].setEnabled(false);
               modeButton[l].setVisible(true);
           } else {
               modeButton[l].setEnabled(false);
               modeButton[l].setVisible(false);
           }
       }
   }
   
   //Mode ajout
   public void modeAjout() {
       collection.setEnabled(false);
       for(int i = 0; i < infos_film.length; i++) {
           infos_film[i].setVisible(false);
       }
       for(int j = 0; j < components.length; j++) { 
           if(j < 4) {
               components[j].setVisible(true);
               components[j].setEnabled(true);
           } else if(j != 5 && j != 7){
               components[j].setEnabled(true);
           }
       }
       components[4].setBackground(Color.WHITE);
       
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
       collection.setEnabled(true);
       for(int i = 0; i < infos_film.length; i++) {
           infos_film[i].setVisible(false);
       }
       for(int j = 0; j < components.length; j++) { 
           if(j < 4) {
               components[j].setVisible(true);
               components[j].setEnabled(true);
           } else if(j != 5){
               components[j].setEnabled(true);
           }
       }
       components[4].setBackground(Color.WHITE);
       
       for(int k = 0; k < modeButton.length; k++) {
            modeButton[k].setEnabled(false);
            modeButton[k].setVisible(false);
       }
       
       modeButton[3].setVisible(true);//Boutton Modifier
       modeButton[4].setVisible(true);//Boutton Modifier
       if(listeEstVide()) {
           modeButton[3].setEnabled(false);
           modeButton[4].setEnabled(false);
       } else {
           modeButton[3].setEnabled(true);
           modeButton[4].setEnabled(true);
       }
   }
   
   //Mode recherche
   public void modeRecherche() {
       collection.setEnabled(false);
       for(int j = 0; j < components.length; j++) { 
           if(j < 3) {
               components[j].setVisible(true);
               components[j].setEnabled(true);
           } else {
               components[j].setEnabled(false);
           }
       }
   }
       
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
       millieu.setLayout(null);           
       millieu.setBounds(X, haut.getY() + haut.getHeight(), LARG_PANNEAU_MILLEU, HAUT_PANNEAU_MILLEU);
       millieu.setBorder(new LineBorder(GRIS_FONCE, 2));
   }
   
   //initialize panneau du bas
   public void initPanneauBas() {
       final int X = PADDING_X + 10;
       final int LARG_PANNEAU_BAS = LARG_FENETRE - (2*X);
       final int HAUT_PANNEAU_BAS = HAUT_FENETRE - haut.getHeight() - millieu.getHeight() - PADDING_Y - 50;
       final int VGAP = HAUT_PANNEAU_BAS / 2;
       
       FlowLayout flowlayout = new FlowLayout();
       flowlayout.setVgap(VGAP);
       
       bas.setLayout(flowlayout);
       bas.setBounds(X, millieu.getY() + millieu.getHeight(), LARG_PANNEAU_BAS, HAUT_PANNEAU_BAS);
   }

   //Initialize les components du panneau du haut
   public void initPanneauHautComponents() {
       int y_Pos = labels[1].getY() - 5;
       
       collection = new JComboBox();
       collection.setBounds(labels[0].getX() + labels[0].getWidth(), labels[0].getY(), 
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
       
       haut.add(collection);
       
       for(int j = 0; j < mode.length; j++) {
           haut.add(mode[j]);
       }
   }   

   //initialize les components du panneau millieu
   public void initPanneauMillieuComponents() {
       int x_Pos = millieu.getX() + 100;
       
       components[0] = new JTextField();
       components[0].setBounds(x_Pos, labels[2].getY() - 5, 360, 25);
       infos_film[0] = new JLabel();
       infos_film[0].setBounds(x_Pos, labels[2].getY() - 5, 360, 25);
       
       components[1] = new JTextField();
       components[1].setBounds(x_Pos, labels[3].getY() - 5, 360, 25);
       infos_film[1] = new JLabel();
       infos_film[1].setBounds(x_Pos, labels[3].getY() - 5, 360, 25);
       
       components[2] = new JComboBox();
       components[2].setBounds(x_Pos, labels[4].getY(), 360, 20);
       //infos_film[2] = new JLabel();
       //infos_film[2].setBounds(x_Pos, labels[4].getY(), 360, 20);
       
       components[3] = new JComboBox();
       components[3].setBounds(x_Pos, labels[5].getY(), 360, 20);
       //infos_film[3] = new JLabel();
       //infos_film[3].setBounds(x_Pos, labels[5].getY(), 360, 20);
       
       components[4] = new JTextArea();
       components[4].setBounds(x_Pos, labels[6].getY(), 360, 65);
       components[4].setBorder(new LineBorder(GRIS_FONCE, 1));
       
       components[5] = new JTextArea();
       components[5].setBounds(x_Pos, labels[7].getY(), 180, 70);
       components[5].setBorder(new LineBorder(GRIS_FONCE, 1));
       
       components[6] = new JButton("Ajouter categorie");
       components[6].setBounds(components[5].getX() + components[5].getWidth() + 15, components[5].getY() + 5, 155, 20);
       
       components[7] = new JButton("Supprimer categorie");
       components[7].setBounds(components[5].getX() + components[5].getWidth() + 15, components[5].getY() + 45, 155, 20);
       
       for(int i = 0; i < components.length; i++){
           millieu.add(components[i]);
       }
       /*for(int j = 0; j < components.length; j++){
           millieu.add(infos_film[j]);
       }*/
   }
   
   //initialization des components du panneau du bas
   public void initPanneauBasComponents() {
       for(int i = 0; i < modeButton.length; i++) {
           modeButton[i] = new JButton(button_text[i]);
           bas.add(modeButton[i]);
       }
   }
   
   //initialization de tous les titres de fields
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
                   labels[i].setBounds(millieu.getX() - 10, millieu.getY() - 70, w, h);
               } else if(i == 7) {
                   labels[i] = new JLabel(labels_text[i]);
                   labels[i].setBounds(labels[i-1].getX(), labels[i-1].getY() + 75, w, h);
               } else {
                   labels[i] = new JLabel(labels_text[i]);
                   labels[i].setBounds(labels[i-1].getX(), labels[i-1].getY() + 30, w, h);
               }
               millieu.add(labels[i]);
           }
       }
   }      
   
   //Ajoute un ActionListener a chaque boutton 
   public void ajouterActionListener() {
       for(int i = 0; i < mode.length; i++) {
           mode[i].addActionListener(this);
       }
       for(int j = 0; j < modeButton.length; j++) {
           modeButton[j].addActionListener(this);
       }
   }
   
   //Ajoute les 3 panneau a la fenetre
   public void ajouterAFenetre() {
       fenetre.getContentPane().add(haut);
       fenetre.getContentPane().add(millieu);
       fenetre.getContentPane().add(bas);
   }
   

   public static void main(String[] args) {
      new TP3();
   }
   
   
   //MODEL
   private void initModele(){
       liste = new ListeAssociativeChainee<String, Video>();
   }
   
   //Big bunch of getters
   
   //Obtenir toutes les videos
   public ArrayList obtenirCollection(){
       //Obtenir toutes les videos en naviguant dans les categories
       ArrayList videos = new ArrayList<Video>();
       ArrayList categories = liste.obtenirCles();
       
       for(Object categorie : categories){
           ArrayList videosOfCategorie = liste.obtenirElements((String)categorie);;
           for(Object vid : videosOfCategorie){
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
           ArrayList videosOfCategorie = liste.obtenirElements((String)categorie);
           
           for(Object vid : videosOfCategorie){
               if(vid instanceof Video){
                   Video temp = (Video)vid;
                   if(temp.getTitre().equals(titre)){
                       video = temp;
                   }
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
   
   
   //Obtenir les categories d'une video
   public String obtenirCategories(String titre){
       //Obtenir tous les <STRING,*> qui contiennent la video
       String categories = "";
       Video video = obtenirVideo(titre);
       ArrayList arrayCategories = liste.obtenirCles();
       
       for(Object categorie : arrayCategories){
           if(liste.obtenirElements((String)categorie).contains(video)){
               categories += categorie + "\n";
           }
       }
       
       //Remove last "\n"?
       if(!categories.equals("")){
           categories = categories.substring(0, categories.length() - 2);
       }
       
       //Return
       return categories;
   }
   
   //Obtenir le nombre de categories
   public int obtenirNombreCategories(String titre){
       //Obtenir tous les <STRING,*> qui contiennent la video
       int categories = 0;
       Video video = obtenirVideo(titre);
       ArrayList arrayCategories = liste.obtenirCles();
       
       for(Object categorie : arrayCategories){
           if(liste.obtenirElements((String)categorie).contains(video)){
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
           
           //Convert categories to array and place in arrayCategories
           String[] arrayCategories = categories.split("\\s+");
           
           //Little StackOverflow fix
           for (int i = 0; i < arrayCategories.length; i++) {
            // You may want to check for a non-word character before blindly
            // performing a replacement
            // It may also be necessary to adjust the character class
            arrayCategories[i] = arrayCategories[i].replaceAll("[^\\w]", "");
           }
           
           //Ajouter cette video dans toutes les categories
           for(Object categorie : arrayCategories){
               liste.ajouter((String)categorie, video);
           }
           
           //Return Success
           return true;
           
       } catch (Exception ex) {
           System.out.println(ex.getMessage());
           return false;
       }
   }
   
   public boolean ajouterCategorie(String categorie){
       //Ajouter la categorie
       //Return Success
       return liste.ajouter(categorie, new ArrayList());
   }
   
   //Big bunch of SETTERS
   
   public boolean modifierVideo(String titre, int annee, int eval, boolean type, String comments, String categories){
       //Convert categories to array and place in arrayCategories
        String[] arrayCategories = categories.split("\\s+");
        //Little StackOverflow fix
        for (int i = 0; i < arrayCategories.length; i++) {
         // You may want to check for a non-word character before blindly
         // performing a replacement
         // It may also be necessary to adjust the character class
         arrayCategories[i] = arrayCategories[i].replaceAll("[^\\w]", "");
        }
        
       //ArrayList categorie;
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
       
       for(Object categorie : arrayCategories){
           //liste.obtenirIndex();
           indexOfVideo = liste.obtenirIndex((String)categorie, oldVideo);
           if(indexOfVideo >= 0){
               confirmation = liste.modifier((String)categorie, video, indexOfVideo);
           }
       }
       
       return confirmation;
   }
   
   //Big bunch of DELETES
   
   public boolean supprimerVideo(String titre){
       Video video = obtenirVideo(titre);
       boolean confirmation = false;
       
       for(Object categorie : liste.obtenirCles()){
           if(!confirmation){
               confirmation = liste.supprimer((String)categorie, video);
           }
       }
       
       return confirmation;
   }
   
   public boolean supprimerCategorie(String categorie){
       ArrayList list = liste.supprimer(categorie);
       return list.isEmpty();
   }
           
   //Big bunch of QUERIES
   
   //Recherche avec les informations dans TITRE, ANNEE, TYPE, EVALUATION, COMMENTAIRES, CATEGORIES en &&
   //Considère les champs vides comme des */ALL
   //Donne un array de videos qui respecte la query
   public ArrayList rechercherVideos(String titre, int annee, int eval, int type, String categories){
       ArrayList collection = new ArrayList();
       for(Object categorie : liste.obtenirCles()){
           for(Object video : liste.obtenirElements((String)categorie)){
               if(!collection.contains(video)){
                   collection.add(video);
               }
           }
       }
       //Convert categories to array and place in arrayCategories
       String[] arrayCategories = categories.split("\\s+");
       //Little StackOverflow fix
       for(int i = 0; i < arrayCategories.length; i++) {
        // You may want to check for a non-word character before blindly
        // performing a replacement
        // It may also be necessary to adjust the character class
        arrayCategories[i] = arrayCategories[i].replaceAll("[^\\w]", "");
       }
       
       if(!titre.equals("") && titre != null){
           for(Object video : collection){
               Video temp = (Video) video;
               if(!temp.getTitre().equals(titre)){
                   collection.remove(video);
               }
           }
       }
       if(annee != -1){
           for(Object video : collection){
               Video temp = (Video) video;
               if(temp.getAnnee() != annee){
                   collection.remove(video);
               }
           }
       }
       if(eval != -1){
           for(Object video : collection){
               Video temp = (Video) video;
               if(temp.getEval() != eval){
                   collection.remove(video);
               }
           }
       }
       if(type != -1){
           boolean controle;
           if(type == 0){
               controle = false;
           }else{
               controle = true;
           }
           for(Object video : collection){
               Video temp = (Video) video;
               if(temp.isFilm() != controle){
                   collection.remove(video);
               }
           }
       }
       
       for(Object video : collection){
            for(Object categorie : arrayCategories){
                ArrayList categorieToCheck = liste.obtenirElements((String)categorie);
                if(!categorieToCheck.contains(video)){
                    collection.remove(video);
                }
            }
       }
       
       return collection;
   }
   
   //TEXT SAVE & LOAD
   
   public void sauvegarder(){
       
   }
   
   public void charger() throws Exception{
       String PATH = "./videos.txt";
       
       ArrayList stringVideos = new ArrayList();
       ArrayList videos = new ArrayList();
       ArrayList categories = new ArrayList();
       
       //Regex
       String pattern = "\\[(.*?)\\]";
       String backupPattern = "/\\\\[(.*?)\\\\]/";
       
       Pattern r = Pattern.compile(pattern);
       
       String line = null;
       
       try {
           BufferedReader bufferedReader = new BufferedReader(new FileReader(PATH));
           
           while((line = bufferedReader.readLine()) != null){
               
               line.replaceAll(pattern, "\n");
               
               stringVideos.add(line);
           }
           
           bufferedReader.close();
       } catch (FileNotFoundException ex) {
           System.out.println(ex.getMessage());
           
       } catch (IOException ex) {
           System.out.println(ex.getMessage());
       }
       
       for(Object stringer : stringVideos){
           String temp = (String)stringer;
           String[] elements = temp.split("::");
           
           String titre = elements[0];
           int annee = Integer.parseInt(elements[1]);
           boolean type = false;
           if(elements[2].equals("FILM")){
               type = true;
           }else if(elements[2].equals("TV")){
               type = false;
           }else{
               System.out.println("Erreur TYPE");
           }
           int eval = Integer.parseInt(elements[3]);
           String commentaires = elements[4];
           
           
           Video video = new Video(titre, annee, eval, type);
           
           //elements[0]; titre
           //elements[1]; annee
           //elements[2]; type
           //elements[3]; eval
           //elements[4]; commentaires
           //elements[5+]; categories
           
           for(int i = 5; i < elements.length; i++){
               liste.ajouter(elements[i], video);
           }
       }
   }
}
