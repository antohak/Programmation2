package tp3;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
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
   private JLabel[] infos_film = new JLabel[4];
   private JComponent[] components = new JComponent [8];
   private JRadioButton[] mode = new JRadioButton [4];
   private JButton[] modeButton = new JButton [6];
   
   //Liste deroulante
   private JComboBox collection = new JComboBox();
   
   private IListeAssociative liste = new ListeAssociativeChainee();
   
   public TP3() {
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
            modeButton[k].setEnabled(false);
            modeButton[k].setVisible(false);
       }
       modeButton[0].setEnabled(false);
       modeButton[0].setVisible(false);
       
       modeButton[1].setEnabled(false);
       modeButton[1].setVisible(false);
       if(listeEstVide()) {
           
       }
   }
   
   //Mode recherche
   public void modeRecherche() {
       //A faire
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
       infos_film[2] = new JLabel();
       infos_film[2].setBounds(x_Pos, labels[4].getY(), 360, 20);
       
       components[3] = new JComboBox();
       components[3].setBounds(x_Pos, labels[5].getY(), 360, 20);
       infos_film[3] = new JLabel();
       infos_film[3].setBounds(x_Pos, labels[5].getY(), 360, 20);
       
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
}