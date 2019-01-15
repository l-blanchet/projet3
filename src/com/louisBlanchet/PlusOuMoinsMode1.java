package com.louisBlanchet;

import org.apache.log4j.Logger;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * cette classe joue le plus ou moins en mode attaquant
 */
public class PlusOuMoinsMode1 extends Game {
    protected Logger logger = Logger.getLogger(PlusOuMoinsMode1.class);
    protected String[] nbMystereDecoupe;
    protected String[] nbMysteredecoupe1;
    int compteur = 0;
    int lenght;
    boolean devMod;
    private boolean mode3;
    public PlusOuMoinsMode1(boolean mode3) {
        this.mode3 = mode3;
    }

    /**
     * méthode servant pour récuperer les valeurs présentes dans le fichier de configuration et relancer une partie
     *
     * @return : retourne la valeur obtenue dans messageVictoire
     */
    public Result Initialisation() {
        Config configuration = new Config();
        lenght = configuration.getLength();
        devMod = configuration.isDevMod();

        Result jouerEncore;
        do {
            jouerEncore = this.mode1();
            logger.info("lancement du tour " + compteur);
        } while (jouerEncore == Result.REJOUER);
        return jouerEncore;
    }

    /**
     * Méthode principale de cette classe c'est la méthode chargée de relancer chaque tour et de lancer le Game quand le besoin est
     *
     * @return : retourne la valeur obtenue dans messageVictoire
     */
    public Result mode1() {
        PlusOuMoinsMode2 g = null;
        Result jouerEncore = Result.REJOUER;
        logger.info("lancement du jeu ou relance d'un tour");
        int nbEssais = 7;
        if (mode3 == true) {
            g = new PlusOuMoinsMode2(mode3);
            nbEssais = 99;
        }
        if (mode3 == false) {
            System.out.println("Vous avez sélectionné le mode 1, vous avez " + nbEssais + " essais, bonne chance !");
        }

        do {
            if ((compteur == 0 && jouerEncore == Result.RELANCER) || (jouerEncore == Result.REJOUER && compteur == 0)) {
                randomizer();
            }
            jouerEncore = this.essais(compteur);
            if (mode3 == true && 1 == compteur) {
                jouerEncore = g.main();
            }
            if (mode3 && compteur > 1) {

                jouerEncore = g.mode2();
            }
            if (jouerEncore == Result.RELANCER) {
                compteur = 0;
            }
        } while (jouerEncore == Result.REJOUER || jouerEncore == Result.RELANCER);
        return jouerEncore;

    }

    /**
     * lance la classe Game pour obtenir un nombre de longueur = lenght
     *
     * @return : retourne true pour continuer le do/while de mode1
     */
    public boolean randomizer() {
        logger.info("lancement de randomizer");
        Game test = new Game();
        nbMystereDecoupe = test.getRandomized(lenght);
        nbMysteredecoupe1 = nbMystereDecoupe;
        logger.info("randomizer vient de renvoyer le nombre mystere" + nbMystereDecoupe);
        return true;
    }

    /**
     * cette méthode effectue un essai
     *
     * @param compteur1 : utilisé en tant qu'argument à la place de compteur, il sert aussi d'affichage du nombre de tours
     * @return : sert à indiquer ce que l'utilisateur veut faire en renvoyant le booléen obtenu dans messageVictoire
     */
    private Result essais(int compteur1) {
        compteur1 = compteur;
        if (devMod == true) {
            System.out.println(Arrays.toString(nbMystereDecoupe));
        }
        compteur = compteur + 1;
        System.out.println("tour effectué: " + compteur1);
        String[] propositionDecoupe = essai();
        boolean verif = false;
        logger.info("verification de rencontre des conditions de victoire ou défaite");
        if (Arrays.equals(propositionDecoupe, nbMysteredecoupe1) || compteur == 6) {
            verif = true;
        }
        System.out.println(Arrays.toString(propositionDecoupe));
        if (verif == true) {
            Result jouerEncore;
            jouerEncore = this.messageVictoire(compteur, propositionDecoupe);
            return jouerEncore;
        }
        return Result.REJOUER;
    }

    /**
     * cette méthode affiche le message de victoire/défaite et affiche le menu de sélection
     *
     * @param compteur2          : équivalent du compteur  utilisé pour faire fonctionner la methode
     * @param propositionDecoupe : la proposition de l'utilisateur decoupé dans une String[]
     * @return : sert à indiquer ce que veux faire l'utilisateur après la partie; true pour relancer un niveau et false pour revenir au menu principal
     */
    private Result messageVictoire(int compteur2, String[] propositionDecoupe) {

        compteur2 = compteur;
        boolean rejouer;
        if (Arrays.equals(propositionDecoupe, nbMysteredecoupe1)) {
            System.out.println("Vous avez gagné en " + compteur + " essais, Bravo!");
            compteur = 0;
        }
        if (compteur == 6) {
            System.out.println("Vous avez perdu");
            compteur = 0;
        }
        return rejouer();
    }

    /**
     * cette méthode est le déclencheur du tour il appelle toutes les fonctions utiles pour le tour
     *
     * @return : retourne la proposition de l'utilisateur en String[]
     */
    private String[] essai() {
        String proposition = proposition();
        String[] propositionDecoupe = decoupage(proposition);

        verification(proposition, propositionDecoupe);
        return propositionDecoupe;
    }

    /**
     * cette méthode est là où l'ordinateur compare le nombre mystère et la proposition et affiche une String composé de "+-=" correspondant
     *
     * @param proposition        : sert à obtenir la longueur de la proposition
     * @param propositionDecoupe : pour récuperer le nombre proposé par l'utilisateur
     * @return :renvoie true pour continuer le tour
     */
    private boolean verification(String proposition, String[] propositionDecoupe) {
        logger.info("vérification de la proposition de l'utilisateur");
        String[] reponse = new String[proposition.length()];

        for (int verificateur = 0; verificateur < proposition.length(); verificateur++) {
            String nbMystere;
            nbMystere = nbMystereDecoupe[verificateur];
            int nbDecoupe = Integer.parseInt(nbMystere);
            String proposition1;
            proposition1 = propositionDecoupe[verificateur];
            int propositioncut = Integer.parseInt(proposition1);

            if (propositioncut == nbDecoupe) {
                reponse[verificateur] = "=";
            } else if (propositioncut < nbDecoupe) {
                reponse[verificateur] = "-";
            } else if (propositioncut > nbDecoupe) {
                reponse[verificateur] = "+";
            }

        }
        System.out.println("réponse" + Arrays.toString(reponse));
        return true;
    }

    /**
     * cette méthode sert à decouper la proposition et à la transformer en String[]
     *
     * @param proposition : obligatoire pour la découper
     * @return :retourne la proposition de l'utilisateur en String[]
     */
    private String[] decoupage(String proposition) {
        logger.info("découpage de la proposition de l'utilisateur");
        String[] propositionDecoupe = new String[proposition.length()];

        for (int compteurtest = 0; compteurtest < proposition.length(); compteurtest++) {
            String decoupage = proposition.substring(compteurtest, compteurtest + 1);
            propositionDecoupe[compteurtest] = decoupage;
        }
        return propositionDecoupe;
    }

    /**
     * cette méthode demande à l'utilisateur d'entrer une proposition
     *
     * @return : retourne la proposition saisi par l'utilisateur
     */
    private String proposition() {
        boolean checkeur = false;
        String proposition = null;
        int essai;


        while (!checkeur) {
            Scanner sc = new Scanner(System.in);
            System.out.println("proposition:");

            try {
                essai = sc.nextInt();
                proposition = Integer.toString(essai);

                if (proposition.length() < nbMysteredecoupe1.length || proposition.length() > nbMysteredecoupe1.length) {
                    System.out.println("vous n'avez pas rentré le bon nombre de chiffres réesayez");
                    System.out.println(Arrays.toString(nbMystereDecoupe));
                    logger.debug("la proposition ne comportait pas le bon nombre de chiffre");
                } else {
                    checkeur = true;
                }
            } catch (InputMismatchException e) {
                logger.debug("l'utilisateur n'a pas rentré de chiffre");
                System.out.println("veillez à ne rentrer que des chiffres !");
                checkeur = false;

            }

        }
        return proposition;

    }
}
