/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2013oktober18.jaror;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author András Gazdag <gazdag.andras@gmail.com>
 */
public class Jaror {

    public static String nullaval(int szam) {
        if (szam < 10) {
            return "0" + szam;
        } else {
            return "" + szam;
        }
    }

    public static int masodpercben(Meres meres) {
        return meres.masodperc + meres.perc * 60 + meres.ora * 60 * 60;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnsupportedEncodingException {

        File inputFile = new File("src/pkg2013oktober18/jaror/data/jarmu.txt");
        ArrayList<Meres> meresek = new ArrayList<>();

        try {
            Scanner fileScanner = new Scanner(inputFile);
            while (fileScanner.hasNextLine()) {
                String[] sor = fileScanner.nextLine().split(" ");
                meresek.add(new Meres(
                        Integer.parseInt(sor[0]),
                        Integer.parseInt(sor[1]),
                        Integer.parseInt(sor[2]),
                        sor[3]
                ));
            }
            System.out.println("Beolvasott mérések száma: " + meresek.size());

            /* 2. Határozza meg, hogy aznap legalább hány óra hosszat dolgoztak az ellenőrzést végzők, ha
             munkaidejük egész órakor kezdődik, és pontosan egész órakor végződik! (Minden óra 0
             perc 0 másodperckor kezdődik, és 59 perc 59 másodperccel végződik.) Az eredményt
             jelenítse meg a képernyőn! */
            int munkaorak = meresek.get(meresek.size() - 1).ora - meresek.get(0).ora + 1;
            System.out.println("2. feladat: " + munkaorak);

            /* 3. Műszaki ellenőrzésre minden órában egy járművet választanak ki. Azt, amelyik abban az
             órában először halad arra. Az ellenőrzés óráját és az ellenőrzött jármű rendszámát jelenítse
             meg a képernyőn a következő formában: 9 óra: AB-1234! Minden óra adata külön
             sorba kerüljön! Csak azon órák adatai jelenjenek meg, amikor volt ellenőrizhető jármű! */
            System.out.println("3. feladat:");
            int utolsoOra = -1;
            for (int i = 0; i < meresek.size(); i++) {
                if (meresek.get(i).ora > utolsoOra) {
                    System.out.println(meresek.get(i).ora + " óra: " + meresek.get(i).rendszam);
                    utolsoOra = meresek.get(i).ora;
                }
            }

            /* 4. A rendszám első karaktere külön jelentéssel bír. Az egyes betűk közül a „B” autóbuszt,
             a „K” kamiont, az „M” motort jelöl, a többi rendszámhoz személygépkocsi tartozik.
             Jelenítse meg a képernyőn, hogy az egyes kategóriákból hány jármű haladt el az ellenőrző
             pont előtt! */
            int busz = 0;
            int kamion = 0;
            int motor = 0;
            int auto = 0;
            for (Meres meres : meresek) {
                switch (meres.rendszam.charAt(0)) {
                    case 'B':
                        busz++;
                        break;
                    case 'K':
                        kamion++;
                        break;
                    case 'M':
                        motor++;
                        break;
                    default:
                        auto++;
                }
            }
            System.out.println("4. feladat: ");
            System.out.println("Autóbuszok: " + busz + "db");
            System.out.println("Kamionok: " + kamion + "db");
            System.out.println("Motorok: " + motor + "db");
            System.out.println("Személyautók: " + auto + "db");

            /* 5. Mettől meddig tartott a leghosszabb forgalommentes időszak? A választ jelenítse meg
             a képernyőn a következő formában: 9:9:13 - 9:15:3!  */
            int hossz = 0;
            int vege = 0;
            for (int i = 1; i < meresek.size(); i++) {
                int elozoMp = masodpercben(meresek.get(i - 1));
                int iMp = masodpercben(meresek.get(i));
                if (iMp - elozoMp > hossz) {
                    hossz = iMp - elozoMp;
                    vege = i;
                }
            }
            System.out.println("5. feladat: "
                    + nullaval(meresek.get(vege - 1).ora) + ":"
                    + nullaval(meresek.get(vege - 1).perc) + ":"
                    + nullaval(meresek.get(vege - 1).masodperc) + " - "
                    + nullaval(meresek.get(vege).ora) + ":"
                    + nullaval(meresek.get(vege).perc) + ":"
                    + nullaval(meresek.get(vege).masodperc)
            );

            /* 6. A rendőrök egy baleset közelében látott járművet keresnek rendszám alapján.
             A szemtanúk csak a rendszám bizonyos karaktereire emlékeztek, így a rendszám
             ismeretlen karaktereit a * karakterrel helyettesítve keresik a nyilvántartásban. Kérjen be
             a felhasználótól egy ilyen rendszámot, majd jelenítse meg a képernyőn az arra illeszthető
             rendszámokat! */
            Scanner input = new Scanner(System.in);
            System.out.println("6. feladat:");
            System.out.print("A keresett rendszám: ");
            String keresettRendszam = input.nextLine();
            for (Meres meres : meresek) {
                boolean jo = true;
                for (int i = 0; i < 7; i++) {
                    if (meres.rendszam.charAt(i) != keresettRendszam.charAt(i)
                            && keresettRendszam.charAt(i) != '*') {
                        jo = false;
                    }
                }
                if (jo) {
                    System.out.println(meres.rendszam);
                }
            }

            /* 7. Egy közúti ellenőrzés pontosan 5 percig tart. Amíg az ellenőrzés folyik, a járművek
             szabadon elhaladhatnak, a következő megállítására csak az ellenőrzés befejezése után
             kerül sor. Ha a rendőrök a legelső járművet ellenőrizték, akkor mely járműveket tudták
             ellenőrizni a szolgálat végéig? Írja az ellenőrzött járművek áthaladási idejét és rendszámát
             a vizsgalt.txt állományba az áthaladás sorrendjében, a bemenettel egyező formában!
             Ügyeljen arra, hogy az időadatokhoz tartozó számok a bevezető nullákat tartalmazzák! */
            System.out.println("7. feladat: ");
            PrintWriter writer = new PrintWriter("src/pkg2013oktober18/jaror/data/vizsgalt.txt", "UTF-8");
            int eltelt = 5 * 60;
            for (int i = 0; i < meresek.size(); i++) {
                if (i > 0) {
                    eltelt += masodpercben(meresek.get(i)) - masodpercben(meresek.get(i - 1));
                }
                if (eltelt >= 5 * 60) {
                    System.out.println(
                            meresek.get(i).ora + " "
                            + meresek.get(i).perc + " "
                            + meresek.get(i).masodperc + " "
                            + meresek.get(i).rendszam
                    );
                    writer.println(
                            meresek.get(i).ora + " "
                            + meresek.get(i).perc + " "
                            + meresek.get(i).masodperc + " "
                            + meresek.get(i).rendszam
                    );
                    eltelt = 0;
                }
            }
            writer.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Jaror.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
