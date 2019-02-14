package com.oclouis.mastermind;

import com.oclouis.*;
import com.oclouis.plusoumoins.PlusOuMoinsMode1;
import org.apache.log4j.Logger;

import java.util.Arrays;

public class MastermindMode1 extends Game implements Mode1 {
    private final Config configuration;
    protected Logger logger = Logger.getLogger(PlusOuMoinsMode1.class);
    int compteur = 0;
    int bienPlace;
    int malPlace;
    int lenght;
    boolean devMod;
    int nbEssais;
    private boolean mode3;

    public MastermindMode1(boolean mode3, Config config) {
        this.mode3 = mode3;
        this.configuration = config;
    }

    /**
     * méthode servant pour récuperer les valeurs présentes dans le fichier de configuration et relancer une partie
     *
     * @return : retourne la valeur obtenue dans messageVictoire
     */
    public Result initialisation() {
        lenght = configuration.getLength();
        devMod = configuration.isDevMod();
        nbEssais = configuration.getNbEssai();

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
        Mode2 mode3Initializer = null;
        Result jouerEncore = Result.REJOUER;
        logger.info("lancement du jeu ou relance d'un tour");
        if (mode3 == true) {
            mode3Initializer = new MastermindMode2(mode3, configuration);
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
                jouerEncore = mode3Initializer.main();
            }
            if (mode3 && compteur > 1) {

                jouerEncore = mode3Initializer.main();
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

        nbMystereDecoupe = getRandomized(lenght);
        nbMysteredecoupePrecedent = nbMystereDecoupe;
        logger.info("randomizer vient de renvoyer le nombre mystere" + Arrays.toString(nbMystereDecoupe));
        return true;
    }

    /**
     * cette méthode effectue un essai
     *
     * @param afficheurTour : utilisé en tant qu'argument à la place de compteur, il sert aussi d'affichage du nombre de tours
     * @return : sert à indiquer ce que l'utilisateur veut faire en renvoyant le booléen obtenu dans messageVictoire
     */
    private Result essais(int afficheurTour) {
        afficheurTour = compteur;
        if (devMod == true) {
            System.out.println(Arrays.toString(nbMystereDecoupe));
        }
        compteur = compteur + 1;
        System.out.println("tour effectué: " + afficheurTour);
        String[] propositionDecoupe = essai();
        boolean verif = false;
        logger.info("verification de rencontre des conditions de victoire ou défaite");
        if (Arrays.equals(propositionDecoupe, nbMysteredecoupePrecedent)) {
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
     * @param propositionDecoupe : la proposition de l'utilisateur decoupé dans une String[]
     * @return : sert à indiquer ce que veux faire l'utilisateur après la partie; true pour relancer un niveau et false pour revenir au menu principal
     */
    private Result messageVictoire(int compteur2, String[] propositionDecoupe) {

        if (bienPlace == lenght) {
            System.out.println("Vous avez gagné en " + compteur + " essais, Bravo!");
            compteur = 0;
        }
        if (compteur == 10 && mode3 == false) {
            System.out.println("Vous avez perdu");
            compteur = 0;
        }
        return rejouer();
    }


    /**
     * cette méthode est là où l'ordinateur compare le nombre mystère et la proposition et affiche une String composé de "+-=" correspondant
     *
     * @param proposition        : sert à obtenir la longueur de la proposition
     * @param propositionDecoupe : pour récuperer le nombre proposé par l'utilisateur
     * @return :renvoie true pour continuer le tour
     */
    protected boolean verification(String proposition, String[] propositionDecoupe) {

        logger.info("vérification de la proposition de l'utilisateur");
        String nbMystere;
        int compteurSpe = 0;
        int compteurNbPropose = 0;
        int compteurproposition = 0;

        for (int verificateur = 0; verificateur < proposition.length(); verificateur++) {

            nbMystere = nbMystereDecoupe[verificateur];
            int nbDecoupe = Integer.parseInt(nbMystere);
            String proposition1;
            proposition1 = propositionDecoupe[verificateur];
            int propositioncut = Integer.parseInt(proposition1);

            if (propositioncut == nbDecoupe) {
                bienPlace = bienPlace + 1;
            }
        }
        int nvlenght = lenght - bienPlace;
        String nvNbMystereDecoupe[] = new String[nvlenght];
        String nvPropositionDecoupe[] = new String[nvlenght];
        for (int verificateur = 0; verificateur < proposition.length(); verificateur++) {

            nbMystere = nbMystereDecoupe[verificateur];
            int nbDecoupe = Integer.parseInt(nbMystere);
            String proposition1;
            proposition1 = propositionDecoupe[verificateur];
            int propositioncut = Integer.parseInt(proposition1);

            if (propositioncut != nbDecoupe) {
                nvNbMystereDecoupe[compteurNbPropose] = String.valueOf(nbDecoupe);
                nvPropositionDecoupe[compteurproposition] = String.valueOf(propositioncut);
                compteurNbPropose++;
                compteurproposition++;

            }
        }

        int nvllePropositionDecoupe;
        int nvNbMystDecoupe;

        for (int compteur = 0; compteur < nvNbMystereDecoupe.length; compteur++) {
            nvllePropositionDecoupe = Integer.parseInt(nvPropositionDecoupe[compteur]);
            int test = 0;
            do {
                nvNbMystDecoupe = Integer.parseInt(nvNbMystereDecoupe[compteurSpe]);
                if (nvllePropositionDecoupe == nvNbMystDecoupe) {
                    malPlace++;
                    test = nvlenght;
                } else {
                    compteurSpe++;
                    test++;
                }
            } while (test < nvlenght);
            compteurSpe = 0;
        }
        String mascBien = " bien placé";
        String plurBien = " bien placés";
        String mascMal = " mal placé";
        String plurMal = " mal placés";
        if (bienPlace <= 1) {
            System.out.println("réponse: " + bienPlace + mascBien);
        } else {
            System.out.println("réponse: " + bienPlace + plurBien);
        }
        if (malPlace <= 1) {
            System.out.println("         " + malPlace + mascMal);
        } else {
            System.out.println("         " + malPlace + plurMal);
        }
        bienPlace = 0;
        malPlace = 0;
        return true;
    }

}


