/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication21;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Chaima
 */
public class Distributeur {

    Banque BK;
    Carte Tab2[];

    public Distributeur(Banque bank, Carte t2[]) throws IOException {
        this.BK = bank;
        this.Tab2 = t2;

    }
//validation(): se charge defaire la verification de la validité et la compatibilité de la carte 

    public boolean validation(long num) {
        for (int i = 0; i < Tab2.length; i++) {
            if (num == Tab2[i].numDeCarte) {  //verifier si la carte existe dans le tableau des cartes
                int cmp = 1;
                Carte c = Tab2[i];
                if (c.expirer()) {
                    System.out.println("saisissez votre code pin");
                    Scanner code = new Scanner(System.in);
                    int cc = code.nextInt();
                    while (cc != Tab2[i].code_pin) {
                        System.out.print("veuillez saisir un code correcte ");
                        Scanner cp = new Scanner(System.in);
                        cc = cp.nextInt();
                        cmp++;
                        if (cmp == 3) {
                            System.out.print("votre carte est avalée");
                            return false;
                        }
                    }
                    return true;
                } else {
                    System.out.println("votre carte est expirée");
                    return false;
                }
            } else {
                System.out.println("votre carte n'existe pas");
                return false;
            }
        }
        return true;

    }

    public void menu() {
        System.out.println("choisir numéro parmi ces choix");
        System.out.println("1.retirer de l'argent");
        System.out.println("2.consulter votre solde");
        System.out.println("3.transferer vers un autre compte");
        System.out.println("4.consulter les transactions");
    }

    public void operation_effectue(long num) throws IOException {

        menu();
        Scanner g = new Scanner(System.in);
        int choix = g.nextInt();
        for (int i = 0; i < Tab2.length; i++) {
            if (Tab2[i].numDeCarte == num) {
                Carte c = Tab2[i];
                switch (choix) {
                    case 1:
                        existance(c);
                        retrait_argent(c);
                        try {
                            if (c.operat.exists()) {
                                c.operat.createNewFile();
                            }
                            FileWriter ecrire = new FileWriter(c.operat);
                            BufferedWriter bw = new BufferedWriter(ecrire);
                            try {
                                bw.write("retrait d'argent a eu lieu le ");
                                DateFormat ajour = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                Date date = new Date();
                                bw.write(ajour.format(date));
                            } finally {
                                ecrire.close();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;

                    //modification du fichier operat en ajoutant le montant la date et le temps de l'operationn
                    case 2:
                        existance(c);
                        consultation_solde(c);
                        try {
                            if (c.operat.exists()) {
                                c.operat.createNewFile();
                            }
                            FileWriter ecrire = new FileWriter(c.operat);
                            BufferedWriter bw = new BufferedWriter(ecrire);
                            try {
                                bw.write("vous aver consulté votre compte le ");
                                DateFormat ajour = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                Date date = new Date();
                                bw.write(ajour.format(date));
                            } finally {
                                ecrire.close();
                            }

                        } catch (IOException e) {
                        }
                        break;
                    //modification du fichier operat en ajoutant l'operaton de consultatio la date et le temps de l'operation
                    case 3:
                        existance(c);
                        System.out.println("saisir le numéro de carte où vous voulez transferé votre argent");
                        Scanner num2 = new Scanner(System.in);
                        long numC = num2.nextLong();
                        for (int j = 0; j < Tab2.length; j++) {
                            if (Tab2[j].numDeCarte == numC) {
                                Carte c2 = Tab2[j];
                                transfert_vers_compte2(c, c2);
                                existance(c);
                            }

                        }
                        try {
                            if (c.operat.exists()) {
                                c.operat.createNewFile();
                            }
                            FileWriter ecrire = new FileWriter(c.operat);
                            try (BufferedWriter bw = new BufferedWriter(ecrire)) {
                                bw.write("il y a eu transfert vers le compte sous l'identifiant ");
                                DateFormat ajour = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                                Date date = new Date();
                                bw.write(ajour.format(date));
                            }

                        } catch (IOException e) {
                        }
                        break;
                    case 4:
                        existance(c);
                        transaction(c);
                        break;
                    default:
                        System.out.println("votre choix est invalide");
                }

            }
        }

    }

    public void existance(Carte c) throws IOException {

        if (BK.Nom != c.bank.Nom) {
            System.out.print("nous avons retiré de votre solde ");
            System.out.println(BK.commission + " car vous n'etes pas un client");
            FileWriter ecrire = new FileWriter(c.operat, true);
            try (BufferedWriter bw = new BufferedWriter(ecrire)) {
                bw.write("vous avez payé une commission de :");
                bw.write((int) BK.commission);
                bw.write("le");
                DateFormat ajour = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                bw.write(ajour.format(date));
            } catch (IOException e) {
            }
            c.SetSolde1(BK.commission);
        }
    }

    public void consultation_solde(Carte c) throws IOException {
        System.out.println(c.getSOLDE(c.solde));
        FileWriter ecrire = new FileWriter(c.operat, true);
            try (BufferedWriter bw = new BufferedWriter(ecrire)) {
                bw.write("vous avez consulté votre solde le:");
                DateFormat ajour = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                bw.write(ajour.format(date));
            } catch (IOException e) {
            }
        
    }

    public void transfert_vers_compte2(Carte c, Carte c2) throws IOException {
        System.out.println("saisir le montant à transferer");
        Scanner k = new Scanner(System.in);
        double montant = k.nextFloat();
        c2.SetSolde2(montant);
        c.SetSolde1(montant);
        System.out.println("votre transfert est fait avec succées");
        FileWriter ecrire = new FileWriter(c.operat, true);
            try (BufferedWriter bw = new BufferedWriter(ecrire)) {
                bw.write("vous avez fait un transfert de votre carte vers la carte de numéro :");
                bw.write((int) (long) c2.numDeCarte);
                bw.write("le");
                DateFormat ajour = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                bw.write(ajour.format(date));
            } catch (IOException e) {
            }

    }

//modification du compte de l'opérateur et celui auquel on va transferer 
    public void transaction(Carte c) throws IOException {
        BufferedReader buff = null;
        FileReader lecteur = null;
        try {
            lecteur = new FileReader(c.operat);
            buff = new BufferedReader(lecteur);
            String line;
            while ((line = buff.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
        } finally {
            try {
                if (buff != null) {
                    buff.close();
                }
                if (lecteur != null) {
                    lecteur.close();
                }
            } catch (IOException e) {
            }
        }
    }

    public void retrait_argent(Carte c) throws IOException {
        System.out.println("veuillez entrer le montant que vous voulez débiter");
        Scanner b = new Scanner(System.in);
        double mon = b.nextDouble();
        if(c.solde>mon){
            System.out.println("votre retrait est effectué avec succés votre solde est " +c.SetSolde1(mon));
            FileWriter ecrire = new FileWriter(c.operat, true);
            try (BufferedWriter bw = new BufferedWriter(ecrire)) {
                bw.write("vous avez fait un retrait d'argent de :");
                bw.write((int) (double) mon);
                bw.write("le");
                DateFormat ajour = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                bw.write(ajour.format(date));
            } catch (IOException e) {
            }
        Imprimer();
        }
        else System.out.println("votre solde est insuffisant");
    }

    public void Imprimer() throws IOException {
        System.out.println("Voulez vous imprimer un réçu?");
        Scanner g = new Scanner(System.in);
        int y = g.nextInt();
        switch (y) {
            case 1:
                //System.out.print("Veuillez patienter pendant l'impression de votre réçu");
                File f = new File("C:\\Users\\Chaima\\Documents\\NetBeansProjects\\JavaApplication21\\reçu.txt");
                try {

                    FileWriter ecrire = new FileWriter(f);
                    try (BufferedWriter bw = new BufferedWriter(ecrire)) {
                        bw.newLine();
                        bw.write("retrait d'argent a eu lieu le ");
                        DateFormat ajour = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Date date = new Date();
                        bw.write(ajour.format(date));
                    }
                } catch (IOException e) {
                }
                BufferedReader buff = null;
                FileReader lecteur = null;
                try {
                    lecteur = new FileReader(f);
                    buff = new BufferedReader(lecteur);
                    String line;
                    while ((line = buff.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                } finally {
                    try {
                        if (buff != null) {
                            buff.close();
                        }
                        if (lecteur != null) {
                            lecteur.close();
                        }
                    } catch (IOException e) {
                    }
                }

                break;

            case 0:
                System.out.print("Merci de nous avoir choisi !");
                break;
        }
    }

    public void liaison(long num) throws IOException {
        if (validation(num)) {
            for (int i = 0; i < 4; i++) { // on a le droit de faire seulement 4 essais successifs
                System.out.println("voulez vous effectuer une opération ?");
                Scanner b = new Scanner(System.in);
                int s = b.nextInt();
                if (s == 1) {
                    operation_effectue(num);
                } else if (s == 0) {
                    System.out.println("vous avez quitté le menu des choix");
                    break;
                } else {
                    System.out.println("votre choix est incorrect");
                }
            }
        }
    }
}
