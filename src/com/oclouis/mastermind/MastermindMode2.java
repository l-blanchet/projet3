package com.oclouis.mastermind;

import com.oclouis.Config;
import com.oclouis.Game;
import com.oclouis.Result;
import org.apache.log4j.Logger;

import java.util.*;

import static java.lang.Math.pow;

//todo Vérifier la saisie de l'utilisateur (nb de bien/mal placés)
//todo Essayer de découper la fonction principale du mastermind en fonctions plus petites
//todo Implémenter le mode duel du MasterMind
//todo Commencer à réfléchir à comment et ou ajouter une (ou plusieurs) Interfaces Java
//todo Essayer d'utiliser d'autres type de Collections Java (ArrayList par exemple à la place des tableaux)
public class MastermindMode2 extends Game {

    private final Config configuration;
    boolean mode3 ;
    HashSet solutionPossible = new HashSet();
    String nbMystere;
    String nbPropose;
    private Logger logger = Logger.getLogger(MastermindMode2.class);
    int lenght;
    int nbCouleur;
    int compteur = 0;
    int bienPlaces;
    int malPlaces;
    int bienEtMalPlaces;
    int nbEssais;

    public MastermindMode2(boolean mode3, Config config) {
        this.mode3 = mode3;
        compteur = 0;
        this.configuration = config;
        nbCouleur = configuration.getNbCouleur();
        lenght = configuration.getLength();
        nbEssais = configuration.getNbEssai();
    }

    public Result main() {
        String[] propositionDecoupe = null;
        Result jouerEncore;
        do {
            if (compteur == 0) {
                selectionNbMystere();
            }
            proposition();
            verification(nbPropose, propositionDecoupe);
            jouerEncore = victoireVerif();
            if (bienEtMalPlaces > 0) {
                eliminationProposition();
            }
            if (mode3 == true ){
                return jouerEncore;
            }


        } while (jouerEncore == Result.REJOUER || jouerEncore == Result.RELANCER);
        return Result.REJOUER;
    }

    public void selectionNbMystere() {
        int valeurMax = (int) (pow(10, lenght) - 1);
        int valeurMin = (int) (pow(10, lenght - 1));
        int nbMystereRentre = 0;
        do {
            logger.info("sélection du nombre mystère");
            System.out.println("vous avez sélectionné le mode 2 veuillez proposer un nombre compris entre " + valeurMin + " et " + valeurMax);
            Scanner sc = new Scanner(System.in);
            nbMystereRentre = 0;
            try {
                nbMystereRentre = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("veillez à ne rentrer que des chiffres !");
            }
        } while (valeurMin >= nbMystereRentre && nbMystereRentre > valeurMax);
        nbMystere = String.valueOf(nbMystereRentre);
        generateurDeHash();
    }

    public void generateurDeHash() {


        if (lenght <= 7) {
            for (int i = (int) pow(nbCouleur, (lenght - 1)); i < (pow(nbCouleur, lenght) - 1); i++) {

                String convert = Integer.toString(i, nbCouleur);
                solutionPossible.add(convert);
            }
            int n;
            n = solutionPossible.size();
            logger.debug("longueur de la hash set" +n);
        }
        if (lenght <= 8) {
            //todo ajouter le comportement pour une lenght superieure à 8
        }
    }

    public String proposition() {

        int Min = 1;
        int Max = solutionPossible.size();
        int nbAleatoire = Min + (int) (Math.random() * ((Max - Min) + 1));

        Iterator<String> test = solutionPossible.iterator();
        for (int i = 0; i <= nbAleatoire; i++) {
            boolean check = test.hasNext();
            if (check == true){
                nbPropose = test.next();
            }else{
                for (int z = 1;z<pow(10,lenght)-1;z++){
                    if ( solutionPossible.contains(z))
                    nbPropose = String.valueOf(z);
                }
            }

        }

        logger.debug("nb Proposé" +nbPropose);
        logger.debug("nb Mystere" +nbMystere);
        return nbPropose;
    }

    public boolean verification(String proposition, String[] propositionDecoupe) {
        System.out.println("le nombre proposé par l'ordinateur est : "+nbPropose);
        System.out.println("Pour rappel le nombre que vous avez proposé est : "+nbMystere);
        Scanner scbp = new Scanner(System.in);
        Scanner scmp = new Scanner(System.in);
        do {
            System.out.println("veuillez entrez le nombre de chiffres bien placés");
            bienPlaces = 0;
            try {
                bienPlaces = scbp.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("veillez à ne rentrer que des chiffres !");
            }
        } while (bienPlaces < 0 || bienPlaces > lenght);

        do {
            System.out.println("veuillez entrez le nombre de chiffres mal placés");
            malPlaces = 0;
            try {
                malPlaces = scmp.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("veillez à ne rentrer que des chiffres !");
            }
        } while (malPlaces < 0 || malPlaces > lenght);
        bienEtMalPlaces = bienPlaces + malPlaces;
        return false;
    }

    public Result victoireVerif() {
        compteur = compteur + 1;
        if (bienPlaces == lenght) {
            System.out.println("Vous avez perdu en "+ compteur + " coups ");
            compteur = 0;
            bienEtMalPlaces = 0;
            Result jouerEncore;
            jouerEncore = rejouer();
            return jouerEncore;

        }
        if (compteur == nbEssais && mode3 == false) {
            System.out.println("Vous avez gagné");
            compteur = 0;
            bienEtMalPlaces = 0;
            Result jouerEncore;
            jouerEncore = rejouer();
            return jouerEncore;
        }
        return Result.REJOUER;
    }

    public void eliminationProposition() {


        String nbProposeBoucle;
        String nbProposeDecoupet[] = new String[lenght];
        String nbMysteredecoupet[] = new String[lenght];
        int compteurSpe = 0;
        int compteurNbPropose = 0;
        int compteurproposition = 0;
        int bienPlace = 0;
        String essai;
        boolean existence;
        int malPlace = 0;
        int bienEtMalPlace = 0;
        int test = 0;
        for (int compteur = 0; compteur < nbMystere.length(); compteur++) {
            String decoupage = nbMystere.substring(compteur, compteur + 1);
            nbMysteredecoupet[compteur] = decoupage;
        }
        for (int i = (int) pow(nbCouleur, (lenght - 1)); i < (pow(nbCouleur, lenght) - 1); i++) {
            essai = String.valueOf(i);
            bienPlace = 0;
            malPlace = 0;
            existence = solutionPossible.contains(essai);
            if (essai.equals(nbPropose) ){
                solutionPossible.remove(essai);
                existence = false;
            }
            if (existence == true) {
                nbProposeBoucle = String.valueOf(i);
                for (int compteur = 0; compteur < nbProposeBoucle.length(); compteur++) {
                    String decoupage = nbProposeBoucle.substring(compteur, compteur + 1);
                    nbProposeDecoupet[compteur] = decoupage;
                }
                for (int verificateur = 0; verificateur < nbMysteredecoupet.length; verificateur++) {

                    String nbTest = nbMysteredecoupet[verificateur];
                    int nbDecoupe = Integer.parseInt(nbTest);
                    String proposition1;
                    proposition1 = nbProposeDecoupet[verificateur];
                    int propositioncut = Integer.parseInt(proposition1);

                    if (propositioncut == nbDecoupe) {
                        bienPlace = bienPlace + 1;
                    }


                }
                if (bienPlace >= bienPlaces && bienPlace <=lenght) {

                    test = test + 1;

                    int nvlenght = lenght - bienPlace;
                    String nvNbMystereDecoupe[] = new String[nvlenght];
                    String nvPropositionDecoupe[] = new String[nvlenght];
                    for (int verificateur = 0; verificateur < nbMysteredecoupet.length; verificateur++) {

                        String nbTest = nbMysteredecoupet[verificateur];
                        int nbDecoupe = Integer.parseInt(nbTest);
                        String proposition1;
                        proposition1 = nbProposeDecoupet[verificateur];
                        int propositioncut = Integer.parseInt(proposition1);

                        if (propositioncut != nbDecoupe) {
                            try {
                                nvNbMystereDecoupe[compteurNbPropose] = String.valueOf(nbDecoupe);
                            }catch (java.lang.ArrayIndexOutOfBoundsException e){
                                verificateur =nbMysteredecoupet.length;
                            }
                            try {
                                nvPropositionDecoupe[compteurproposition] = String.valueOf(propositioncut);

                            }catch (java.lang.ArrayIndexOutOfBoundsException e){
                                verificateur =nbMysteredecoupet.length;
                            }
                            compteurNbPropose = compteurNbPropose + 1;
                            compteurproposition = compteurproposition + 1;

                        }


                    }
                    compteurNbPropose = 0;
                    compteurproposition = 0;
                    nvlenght =0;
                    int nvllePropositionDecoupe;
                    int nvNbMystDecoupe;

                    for (int compteur = 0; compteur < nvNbMystereDecoupe.length; compteur++) {
                        nvllePropositionDecoupe = Integer.parseInt(nvPropositionDecoupe[compteur]);
                        int test3 = 0;
                        do {
                            nvNbMystDecoupe = Integer.parseInt(nvNbMystereDecoupe[compteurSpe]);
                            if (nvllePropositionDecoupe == nvNbMystDecoupe) {
                                malPlace++;
                                test3 = nvlenght;
                            } else {
                                compteurSpe++;
                                test3++;
                            }
                        } while (test3 < nvlenght);
                        compteurSpe = 0;
                    }
                    bienEtMalPlace = bienPlace + malPlace;
                    if (bienEtMalPlace < bienEtMalPlaces) {
                        if (essai.equals(nbMystere)){
                            System.out.println("attention");
                        }
                        solutionPossible.remove(essai);
                    }


                }else{
                    solutionPossible.remove(essai);
                }

            }

        }
        boolean verif = solutionPossible.contains(nbMystere);
        logger.debug("verification si le nombre mysere est toujours dans l hash set " +verif);
        logger.debug("nombre de solutions dans la hash set " +solutionPossible.size());

    }
}
