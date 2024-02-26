import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final String NAZEV_SOUBORU = "deskovky.txt";
    private static DataDeskovychHer dataDeskovychHer;

    public static void main(String[] args) {
        dataDeskovychHer = new DataDeskovychHer();
        dataDeskovychHer.setVisible(true);
    }

    public static List<DeskovaHra> nactiDataZeSouboru(String nazevSouboru) {
        List<DeskovaHra> seznamDeskovychHer = new ArrayList<>();
        try (Scanner scn = new Scanner(new BufferedReader(new FileReader(nazevSouboru)))) {
            int cisloRadku = 1;
            while (scn.hasNextLine()) {
                String radek = scn.nextLine();
                String chybneData = "";
                String nazevHry;
                boolean zakoupeno;
                int oblibenostHry;

                try {
                    String[] atributy = radek.split("; ");
                    nazevHry = atributy[0];
                    zakoupeno = Boolean.parseBoolean(atributy[1]);
                    try {
                        oblibenostHry = Integer.parseInt(atributy[2]);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(dataDeskovychHer, "Oblíbenost na řádku č. " + cisloRadku + " není v rozsahu 1-3, byla proto nastavena na 2!\n" + "Error: " +  e.getLocalizedMessage());
                        oblibenostHry = 2;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    JOptionPane.showMessageDialog(dataDeskovychHer, "Data hry na řádku č. " + cisloRadku + " nejsou ve správném formátu, tato data nebude možné upravovat!\n" + "Error: " +  e.getLocalizedMessage());
                    nazevHry = "Chybné data!";
                    zakoupeno = false;
                    oblibenostHry = 2;
                    chybneData = radek ;
                }
                try {
                    seznamDeskovychHer.add(new DeskovaHra(nazevHry, zakoupeno, oblibenostHry, chybneData));
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(dataDeskovychHer, "Oblíbenost na řádku č. " + cisloRadku + " není v rozsahu 1-3, byla proto nastavena na 2!\n" + "Error: " +  e.getLocalizedMessage());
                    seznamDeskovychHer.add(new DeskovaHra(nazevHry, zakoupeno, 2, chybneData));
                }
                cisloRadku++;
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(dataDeskovychHer, "Soubor \"" + nazevSouboru + "\" nebyl nalezen!\n" + "Error: " +  e.getLocalizedMessage());
        }
        return seznamDeskovychHer;
    }

    public static void zapisDataDoSouboru(String nazevSouboru, Model model) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(nazevSouboru)))) {
            for (int i = 0; i < model.getModelSize(); i++) {
                DeskovaHra deskovaHra = model.getDeskovaHra(i);
                if (deskovaHra.getChybneData().isEmpty()) {
                    pw.println(deskovaHra.getNazev() + "; " + deskovaHra.isZakoupeno() + "; " + deskovaHra.getOblibenost());
                } else {
                    pw.println(deskovaHra.getChybneData());
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(dataDeskovychHer, "Zápis do souboru \"" + nazevSouboru + "\" nebyl úspěšný!\n" + "Error: " + e.getLocalizedMessage());
        }
    }
}