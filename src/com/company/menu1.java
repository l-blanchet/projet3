package com.company;

import java.util.Scanner;

public class menu1 {
    public void specialMenu1 (){
        System.out.println("Choisissez le mode de jeu voulu");
        System.out.println("1- Mode Challenger");
        System.out.println("2- Mode Défenseur ");
        System.out.println("3- Mode Hybride");
        System.out.println("4- Retour");
        Scanner sc = new Scanner(System.in);
        int nbMenu1 = sc.nextInt();
        if (nbMenu1 == 1){
            System.out.println("test1");
        }
        if (nbMenu1 == 2){
            System.out.println("test2");
        }
        if (nbMenu1 == 3){
            System.out.println("test3");
        }
        if (nbMenu1 == 4){
            mainMenu main = new mainMenu();
            main.displayMenu();
        }
        if (nbMenu1 <= 0 || nbMenu1 >= 5) {
            System.out.println("vous n'avez pas sélectionné de valeur acceptée");
            specialMenu1();
        }
    }
}