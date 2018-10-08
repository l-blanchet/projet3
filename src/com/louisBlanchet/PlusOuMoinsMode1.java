package com.louisBlanchet;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PlusOuMoinsMode1 extends Randomizer {
    public String[] getRandomized() {

        super.getRandomized();
        mode1();

        return new String[]{"test"};
    }
    public void mode1(){
        Randomizer test = new Randomizer();
        String nbMystereDecoupe[] = test.getRandomized();
        String nbMysteredecoupe1[]= nbMystereDecoupe;
        int nbEssais = 7;
        System.out.println("Vous avez sélectionné le mode 1, vous avez "+nbEssais+" essais, bonne chance !");
        for (int compteur =0 ; compteur<nbEssais;compteur++) {
            System.out.println(Arrays.toString(nbMystereDecoupe));
            int exlegeek = 1;
            exlegeek = exlegeek +1 ;
            System.out.println("proposition:");
            Scanner sc = new Scanner(System.in);
            int essai = 0;
            try {
                essai = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("veillez à ne rentrer que des chiffres !");
                mode1();
            }

            String proposition = Integer.toString(essai);
            if (proposition.length()<nbMystereDecoupe.length|| proposition.length()>nbMystereDecoupe.length){
                System.out.println("vous n'avez pas rentré le bon nombre de chiffres réesayez");
                mode1();
            }
            String[] propositionDecoupe = new String[proposition.length()];

            for (int compteurtest = 0; compteurtest < proposition.length(); compteurtest++) {
                String decoupage = proposition.substring(compteurtest, compteurtest + 1);
                propositionDecoupe[compteurtest] = decoupage;
            }

            String [] reponse = new String[proposition.length()];

            for (int verificateur = 0 ; verificateur<proposition.length(); verificateur++){
                String nbMystere;
                nbMystere = nbMystereDecoupe[verificateur] ;
                int test1 = Integer.parseInt(nbMystere);
                String proposition1 ;
                proposition1 = propositionDecoupe[verificateur];
                int test2 = Integer.parseInt(proposition1);

                if (test2==test1){
                    reponse[verificateur] = "=";
                }
                else if (test2<test1){
                    reponse[verificateur] = "-";
                }
                 else if (test2>test1 ){
                    reponse[verificateur] = "+";
                }

            }
            System.out.println("réponse" + Arrays.toString(reponse));

            if (Arrays.equals(propositionDecoupe, nbMysteredecoupe1) || compteur == 6){
                if (Arrays.equals(propositionDecoupe, nbMysteredecoupe1)) {
                    System.out.println("Vous avez gagné en " + exlegeek + " essais, Bravo!");
                }
                if (compteur == 6){
                    System.out.println("Vous avez perdu");
                }
                System.out.println("veuillez sélectionner dans quelle section voulez vous aller");
                System.out.println("1- Rejouer");
                System.out.println("2- Menu Principal");
                System.out.println("3- Quitter");
                 sc = new Scanner(System.in);
                int selection = 0;
                try {
                    selection = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("veillez à ne rentrer que des chiffres !");
                }
                if (selection == 1){
                    mode1();
                }
                else if (selection == 2){
                    MainMenu main = new MainMenu();
                    main.displayMenu();
                }
                else if (selection == 3){
                    System.out.println("fermeture du programme ");
                    System.exit(0);
                }
                else{
                     sc = new Scanner(System.in);
                    selection = 0;
                    try {
                        selection = sc.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("veillez à ne rentrer que des chiffres !");
                    }
                }
            }


            System.out.println(Arrays.toString(nbMystereDecoupe));
            System.out.println(Arrays.toString(propositionDecoupe));
        }
    }
}