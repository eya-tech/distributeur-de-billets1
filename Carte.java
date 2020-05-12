/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication21;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Chaima
 */
public class Carte {
    double solde;
    Banque bank;
    int code_pin;
    public long numDeCarte;
    Date date_expiration;
    File operat;

     public Carte(long numero, Banque nbank, int CodPin,  Date DtExp, File transaction,double solde) throws IOException {
        this.solde=solde;
        this.numDeCarte = numero;
        this.bank = nbank;
        this.code_pin = CodPin;
        DateFormat ajour = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        this.date_expiration = date;
        this.operat = new File("transaction.txt");// je ne sais pas si c'est logique d'ecrire ça (this.operat= new File("f.txt");)

    }

    public double getSOLDE(double v) {
        System.out.print(v);
        return 0;
    }

    /**
     *
     * @param montant
     */
    public double SetSolde1(double montant) {
        this.solde -= montant;
        return this.solde;
    }
    public double SetSolde2(double x){
        this.solde+=x;
        return this.solde;
    }
    public boolean expirer() {
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return date.compareTo(date_expiration) > 0;
    }
    
}