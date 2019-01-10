package com.louisBlanchet;


import org.apache.log4j.Logger;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * classe affichant le menu principal
 */
public class MainMenu {
    /**
     * méthode affichant le menu principal
     */
    private static Logger logger = Logger.getLogger(MainMenu.class);
    public void displayMenu() {
        Result jouerEncore;
        logger.info("affichage du menu");
       do {
           int nbMenu;
           do {

               System.out.println("bienvenue sur mon premier projet");
               System.out.println("--------MENU---------");
               System.out.println("1- Plus ou Moins");
               System.out.println("2- MasterMind");
               System.out.println("3- Options");
               System.out.println("4- Quitter");

               Scanner sc = new Scanner(System.in);
               nbMenu = 0;
               try {
                   nbMenu = sc.nextInt();
               } catch (InputMismatchException e) {
                   System.out.println("veillez à ne rentrer que des chiffres !");
               }
           } while (nbMenu < 1 || nbMenu > 4);

           jouerEncore = this.displaySelected(nbMenu);
       }while (jouerEncore ==Result.QUITTER);
    }

    public Result displaySelected(int nbMenu) {
        /**
         * méthode récuperant le valeur correspondant au menu où l'utilisateur souhaite se rendre
         */
        Result jouerEncore = Result.QUITTER;
        logger.info("choix du menu " +nbMenu);
        if (nbMenu == 1) {
            Menu1 menuSpe = new Menu1();
            jouerEncore = menuSpe.specialMenu1();
        } else if (nbMenu == 2) {
            Menu2 menuSpe = new Menu2();
            jouerEncore = menuSpe.specialMenu2();
        } else if (nbMenu == 3) {
            Menu3 menuSpe = new Menu3();
            jouerEncore =menuSpe.specialMenu3();
        } else if (nbMenu == 4) {
            System.out.println("fermeture du programme ");
            System.exit(0);
        }
        return jouerEncore ;
    }


}


