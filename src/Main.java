import javax.swing.*;
import javax.xml.crypto.Data;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final String NAZEV_SOUBORU = "deskovky.txt";
    private static Model model;
    private static DataDeskovychHer dataDeskovychHer;

    public static void main(String[] args) {
        model = new Model(nactiDataZeSouboru(NAZEV_SOUBORU));

        dataDeskovychHer = new DataDeskovychHer(model);
        dataDeskovychHer.setContentPane(dataDeskovychHer.getMainPanel());
        dataDeskovychHer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dataDeskovychHer.setSize(500, 350);
        dataDeskovychHer.setTitle("Data deskových her");
        dataDeskovychHer.setVisible(true);

        if (model.getModelSize() != 0) {
            dataDeskovychHer.zobrazitDataDeskoveHry();
        }
    }

    public static List<DeskovaHra> nactiDataZeSouboru(String nazevSouboru) {
        List<DeskovaHra> seznamDeskovychHer = new ArrayList<>();
        int cisloRadku = 1;
        try (Scanner scn = new Scanner(new BufferedReader(new FileReader(nazevSouboru)))) {
            while (scn.hasNextLine()) {
                String[] atributy = scn.nextLine().split("; ");
                seznamDeskovychHer.add(new DeskovaHra(
                        atributy[0], Boolean.parseBoolean(atributy[1]), Integer.parseInt(atributy[2])
                ));
                cisloRadku++;
            }
        } catch (FileNotFoundException e) {
            System.err.println("Soubor \"" + nazevSouboru + "\" nebyl nalezen!\n" + e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            System.err.println("Číselná data nejsou ve správném formátu na řádku č. " + cisloRadku + "\n" + e.getLocalizedMessage());
        }
        return seznamDeskovychHer;
    }

    public static void zapisDataDoSouboru(String nazevSouboru, Model model) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(nazevSouboru)))) {
            for (int i = 0; i < model.getModelSize(); i++) {
                DeskovaHra deskovaHra = model.getDeskovaHra(i);
                pw.println(deskovaHra.getNazev() + "; " + deskovaHra.isZakoupeno() + "; " + deskovaHra.getOblibenost());
            }
        } catch (IOException e) {
            System.err.println("Zápis do souboru \"" + nazevSouboru + "\" nebyl úspěšný!\n" + e.getLocalizedMessage());
        }
    }
}