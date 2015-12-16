package tp3;

import java.io.Serializable;

/**
 * Cette classe modelise un video.
 * 
 * @author Melanie Lord
 * @version novembre 2015
 */
public class Video implements Serializable {
   
   //constantes
   public static final int ANNEE_MIN = 1930;
   public static final int ANNEE_MAX = 2050;
   
   public static final String ERR_TITRE_NULL_OU_VIDE = 
           "Erreur. Il manque une valeur pour le titre !";
   
   public static final String ERR_GENRE_NULL_OU_VIDE = 
           "Erreur. Il manque une valeur pour le genre !";
   
   public static final String ERR_INTERVALLE_ANNEE = 
           "Erreur. L'annÃ©e doit Ãªtre un entier entre " + ANNEE_MIN  + " et " 
           + ANNEE_MAX + " !";
   
   public static final String ERR_EVAL_INVALIDE = "Erreur. Évaluation invalide !";
   
   //attributs
   private String titre = "";        //titre du film
   private int annee = 2013;         //l'annee de sortie du video
   private int eval = 0;             //0 = aucune eval, 1 Ã  5 (nbr etoiles)
   private boolean film = true;      //sinon c'est une serie tv
   private String commentaires = ""; //commentaires divers sur le video
   
   /**
    * Construit une video dont les attributs sont initialises avec les valeurs
    * des parametres donnes.
    * @param titre le titre de cette video.
    * @param annee l'annee de sortie de cette video
    * @param eval  l'evaluation de l'appreciation de cette video.
    * @param film vrai si c'est un film, faux si c'est une serie televisee.
    * @throws Exception si le titre est null ou vide, si l'annee n'est 
    *         pas entre ANNEE_MIN et ANNEE_MAX inclus., si eval n'est pas 
    *         entre 0 et 5 inclusivement.
    */
   public Video (String titre, int annee, int eval, boolean film)
            throws Exception {
      if (titre == null || titre.isEmpty()) {
         throw new Exception (ERR_TITRE_NULL_OU_VIDE);
      } else if (annee < ANNEE_MIN || annee > ANNEE_MAX) {
         throw new Exception (ERR_INTERVALLE_ANNEE);
      } else if (eval < 0 || eval > 5) {
         throw new Exception (ERR_EVAL_INVALIDE);
      }
      this.titre = titre;
      this.annee = annee;
      this.eval = eval;
      this.film = film;
   }

   /**
    * Retourne le titre de cette video.
    * @return le titre de cette video.
    */
   public String getTitre() {
      return titre;
   }
   
   /**
    * Permet de modifier le titre de cette video avec celui donne en parametre.
    * @param titre le nouveau titre.
    * @throws Exception si le titre donne en parametre est null ou vide.
    */
   public void setTitre(String titre) throws Exception {
      if (titre == null || titre.isEmpty()) {
         throw new Exception(ERR_TITRE_NULL_OU_VIDE);
      }
      this.titre = titre;
   }

   /**
    * Retourne l'annee de sortie de cette video.
    * @return  l'annee de sortie de cette video.
    */
   public int getAnnee() {
      return annee;
   }

   /**
    * Permet de modifier l'annee de cette video par l'annee passee en parametre.
    * @param annee la nouvelle annee.
    * @throws Exception si le parametre annee n'est pas entre ANNEE_MIN et 
    *         ANNEE_MAX inclusivement.
    */
   public void setAnnee(int annee) throws Exception {
      if (annee < ANNEE_MIN || annee > ANNEE_MAX) {
         throw new Exception (ERR_INTERVALLE_ANNEE);
      }
      this.annee = annee;
   }

   /**
    * Retourne l'evaluation de cette video.
    * @return  l'evaluation de cette video.
    */
   public int getEval() {
      return eval;
   }

   /**
    * Permet de modifier l'evaluation de cette video. Les valeurs
    * permises sont : 
    *    0 : aucune evaluation
    *    1 : 1 etoile
    *    2 : 2 etoiles
    *    3 : 3 etoiles
    *    4 : 4 etoiles
    *    5 : 5 etoiles
    * @param eval la nouvelle evaluation.
    * @throws Exception si le parametre eval n'est pas entre 0 et 5 inclusivement. 
    */
   public void setEval(int eval) throws Exception {
      if (eval < 0 || eval > 5) {
         throw new Exception(ERR_EVAL_INVALIDE);
      }
      this.eval = eval;
   }

   /**
    * Retourne vrai si cette video est un film, faux sinon (c'est une serie).
    * @return true si cette video est un film, faux sinon.
    */
   public boolean isFilm() {
      return film;
   }

   /**
    * Permet de modifier l'attribut film.
    * @param film la nouvelle valeur pour l'attribut film.
    */
   public void setFilm(boolean film) {
      this.film = film;
   }

   /**
    * Retourne les commentaires sur cette video.
    * @return  les commentaires sur cette video.
    */
   public String getCommentaires() {
      return commentaires;
   }

   /**
    * Permet de modifier les commentaires de cette video.
    * Si la chaine donnee est null, les commentaires de cette video
    * sont initialises a la chaine vide.
    * @param commentaires 
    */
   public void setCommentaires(String commentaires) {
      if (commentaires == null) {
         this.commentaires = "";
      } else {
         this.commentaires = commentaires;
      }
   }

   /**
    * Retourne une representation sous forme de chaine de caracteres de cette
    * Video.
    * @return une representation sous forme de chaine de caracteres de cette 
    *         Video.
    */
   @Override
   public String toString() {
      return  titre + " (" + annee + ")";
   }
   
   /**
    * Retourne vrai si la video passee en parametre est egale a cette Video, 
    * faux sinon. Deux videos sont egales si elles ont le meme titre 
    * (sans tenir compte de la casse) et la meme annee de sortie.
    * 
    * @param autreFilm la video a comparer avec cette video
    * @return true si v est egal a cette Video, faux sinon.
    */
   @Override
   public boolean equals (Object autreFilm) {
      return this == autreFilm ||
              (autreFilm != null && 
              this.getClass().equals(autreFilm.getClass()) &&
              this.titre.equalsIgnoreCase(((Video)autreFilm).titre) && 
              this.annee == ((Video)autreFilm).annee); 
   }
   
}