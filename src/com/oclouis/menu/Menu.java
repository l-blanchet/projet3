package com.oclouis.menu;

import java.util.Scanner;

class Menu {
    int getNbMenu() {
        int nbMenu1;
        System.out.println("Choisissez le mode de jeu voulu");
        System.out.println("1- Mode Challenger");
        System.out.println("2- Mode Défenseur ");
        System.out.println("3- Mode Duel");
        System.out.println("4- Retour");
        Scanner sc = new Scanner(System.in);
        nbMenu1 = sc.nextInt();
        return nbMenu1;
    }
}
