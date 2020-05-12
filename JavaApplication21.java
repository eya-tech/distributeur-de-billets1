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
import java.util.Scanner;

/**
 *
 * @author Chaima
 */
public class JavaApplication21 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        File f=new File("C:\\Users\\Chaima\\Documents\\NetBeansProjects\\JavaApplication21\\transaction1.txt");
        File f1=new File("C:\\Users\\Chaima\\Documents\\NetBeansProjects\\JavaApplication21\\transactions2.txt");
        DateFormat ajour = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date(2021/05/06);
        Banque b=new Banque("BH",2);
        Carte c=new Carte(123445,b,4597,date,f,25000);
        Carte c2= new Carte(458963,b,7458,date,f1,10000);
        Banque b2=new Banque("atb",1);
        Carte Tab2[]={c,c2};
        Distributeur d=new Distributeur(b2,Tab2);
        System.out.println("entrez votre numéreo de carte");
        Scanner num= new Scanner(System.in);
        long n=num.nextLong();
        d.liaison(n);
        
    }
}
       
