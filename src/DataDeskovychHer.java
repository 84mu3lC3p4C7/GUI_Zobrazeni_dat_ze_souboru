import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class DataDeskovychHer extends JFrame {
    private JTextField txtNazevHry;
    private JCheckBox cbZakoupeno;
    private JRadioButton rbOblibenost1;
    private JRadioButton rbOblibenost2;
    private JRadioButton rbOblibenost3;
    private JButton btnPredchozi;
    private JButton btnDalsi;
    private JButton btnUlozit;
    private JPanel MainPanel;

    private JMenuBar menuBar = new JMenuBar();
    private JMenu maSoubor = new JMenu("Soubor", true);
    private JMenu maHra = new JMenu("Hry", true);
    private JMenu maSouhrn = new JMenu("Souhrn", true);
    private JMenuItem miUlozit = new JMenuItem("Uložit");
    private JMenuItem miNacist = new JMenuItem("Načíst");
    private JMenuItem miPridat = new JMenuItem("Přidat");
    private JMenuItem miOdebrat = new JMenuItem("Odebrat");
    private JMenuItem miSeradit = new JMenuItem("Seřadit");
    private JMenuItem miStatistika = new JMenuItem("Statistika");

    private Model model;
    private int indexNacteneHry = 0;

    public DataDeskovychHer() {
        this.setContentPane(MainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 350);
        this.setTitle("Data deskových her");
        vytvoritMenu();

        prepnoutInput(false);
        btnPredchozi.setEnabled(false);
        btnDalsi.setEnabled(false);
        btnUlozit.setEnabled(false);

        ButtonGroup oblibenostGroup = new ButtonGroup();
        oblibenostGroup.add(rbOblibenost1);
        oblibenostGroup.add(rbOblibenost2);
        oblibenostGroup.add(rbOblibenost3);

        btnDalsi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                indexNacteneHry++;
                if (indexNacteneHry == model.getModelSize() - 1) {
                    btnDalsi.setEnabled(false);
                }
                if (model.getModelSize() > 1) {
                    btnPredchozi.setEnabled(true);
                }
                zobrazitDataDeskoveHry();
            }
        });

        btnPredchozi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                indexNacteneHry--;
                if (indexNacteneHry == 0) {
                    btnPredchozi.setEnabled(false);
                }
                if (model.getModelSize() > 1) {
                    btnDalsi.setEnabled(true);
                }
                zobrazitDataDeskoveHry();
            }
        });

        btnUlozit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ulozitDataDeskoveHry();
            }
        });
    }

    private void vytvoritMenu() {
        miUlozit.addActionListener(e -> Main.zapisDataDoSouboru(Main.NAZEV_SOUBORU, model));
        miUlozit.setEnabled(false);
        miNacist.addActionListener(e -> nacistDataDeskovychHer());

        miPridat.addActionListener(e -> vytvoritDataDeskoveHry());
        miOdebrat.addActionListener(e -> odebratDataDeskoveHry());
        miOdebrat.setEnabled(false);
        miSeradit.addActionListener(e -> seraditHryPodleNazvu());
        miSeradit.setEnabled(false);

        miStatistika.addActionListener(e -> souhrnDatDeskovychHer());
        miStatistika.setEnabled(false);

        maSoubor.add(miUlozit);
        maSoubor.add(miNacist);

        maHra.add(miPridat);
        maHra.add(miOdebrat);
        maHra.add(miSeradit);

        maSouhrn.add(miStatistika);

        menuBar.add(maSoubor);
        menuBar.add(maHra);
        menuBar.add(maSouhrn);
        setJMenuBar(menuBar);
    }

    public void nacistDataDeskovychHer() {
        model = new Model(Main.nactiDataZeSouboru(Main.NAZEV_SOUBORU));

        if (model.getModelSize() != 0) {
            btnDalsi.setEnabled(true);
            btnUlozit.setEnabled(true);
            miUlozit.setEnabled(true);
            miOdebrat.setEnabled(true);
            miSeradit.setEnabled(true);
            miStatistika.setEnabled(true);
            zobrazitDataDeskoveHry();
        } else {
            JOptionPane.showMessageDialog(this, "Soubor s daty deskových her je prázdný!");
        }
    }

    public void zobrazitDataDeskoveHry() {
        DeskovaHra deskovaHra = model.getDeskovaHra(indexNacteneHry);
        prepnoutInput(deskovaHra.getChybneData().isEmpty());
        txtNazevHry.setText(deskovaHra.getNazev());
        cbZakoupeno.setSelected(deskovaHra.isZakoupeno());
        switch (deskovaHra.getOblibenost()) {
            case 1:
                rbOblibenost1.setSelected(true);
                break;
            case 2:
                rbOblibenost2.setSelected(true);
                break;
            case 3:
                rbOblibenost3.setSelected(true);
                break;
        }
    }

    public void ulozitDataDeskoveHry() {
        DeskovaHra deskovaHra = model.getDeskovaHra(indexNacteneHry);
        deskovaHra.setNazev(txtNazevHry.getText());
        deskovaHra.setZakoupeno(cbZakoupeno.isSelected());
        if (rbOblibenost1.isSelected()) {
            deskovaHra.setOblibenost(1);
        } else if (rbOblibenost2.isSelected()){
            deskovaHra.setOblibenost(2);
        } else {
            deskovaHra.setOblibenost(3);
        }
        model.setDeskovaHra(indexNacteneHry, deskovaHra);
    }

    public void vytvoritDataDeskoveHry() {
        DeskovaHra deskovaHra = new DeskovaHra("Název deskové hry", false, 2, "");
        if (model != null) {
            indexNacteneHry = model.getModelSize();
            model.pridatDeskovouHru(deskovaHra);
            btnPredchozi.setEnabled(model.getModelSize() > 1);
            btnDalsi.setEnabled(false);
            btnUlozit.setEnabled(true);
            miUlozit.setEnabled(true);
            miOdebrat.setEnabled(true);
            miSeradit.setEnabled(true);
            miStatistika.setEnabled(true);
            zobrazitDataDeskoveHry();
        } else {
            if(JOptionPane.showOptionDialog(this, "Přejete si vytvořit nový seznam deskových her? Tato operace přepíše dosavadní seznam, který nebyl načten!", "Vytvořit nový seznam deskových her", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null) == 0) {
                List<DeskovaHra> seznamDeskovych = new ArrayList<>();
                seznamDeskovych.add(deskovaHra);
                indexNacteneHry = 0;
                model = new Model(seznamDeskovych);
                btnUlozit.setEnabled(true);
                miUlozit.setEnabled(true);
                miOdebrat.setEnabled(true);
                miSeradit.setEnabled(true);
                miStatistika.setEnabled(true);
                zobrazitDataDeskoveHry();
            }
        }
    }

    public void odebratDataDeskoveHry() {
        model.odebratDeskovouHru(indexNacteneHry);
        btnPredchozi.setEnabled(false);
        if (indexNacteneHry != 0) {
            indexNacteneHry--;
            if (indexNacteneHry != 0) {
                btnPredchozi.setEnabled(true);
            }
            zobrazitDataDeskoveHry();
        }
        else {
            btnDalsi.setEnabled(false);
            if (model.getModelSize() == 0) {
                prepnoutInput(false);
                txtNazevHry.setText("Prázdný seznam!");
                cbZakoupeno.setSelected(false);
                rbOblibenost2.setSelected(true);
                btnUlozit.setEnabled(false);
                miUlozit.setEnabled(false);
                miOdebrat.setEnabled(false);
                miSeradit.setEnabled(false);
                miStatistika.setEnabled(false);
            } else {
                if (model.getModelSize() > 1) {
                    btnDalsi.setEnabled(true);
                }
                miUlozit.setEnabled(true);
                miOdebrat.setEnabled(true);
                zobrazitDataDeskoveHry();
            }
        }
    }

    public void seraditHryPodleNazvu() {
        Model novyModel = new Model(new ArrayList<>());
        List<String> serazeneNazvy = new ArrayList<>();
        for (int i = 0; i < model.getModelSize(); i++) {
            serazeneNazvy.add(model.getDeskovaHra(i).getNazev());
        }
        Collections.sort(serazeneNazvy);
        while (!serazeneNazvy.isEmpty()) {
            for (int i = 0; i < model.getModelSize(); i++) {
                if (serazeneNazvy.get(0).equals(model.getDeskovaHra(i).getNazev())) {
                    novyModel.pridatDeskovouHru(model.getDeskovaHra(i));
                    model.odebratDeskovouHru(i);
                    serazeneNazvy.remove(0);
                }
            }
        }
        model = novyModel;
        btnPredchozi.setEnabled(false);
        btnDalsi.setEnabled(model.getModelSize() > 1);
        indexNacteneHry = 0;
        zobrazitDataDeskoveHry();
    }

    public void souhrnDatDeskovychHer() {
        int pocetZakoupenychDeskovychHer = 0;
        String oblibeneDeskoveHry = "";
        for (int i = 0; i < model.getModelSize(); i++) {
            if (model.getDeskovaHra(i).isZakoupeno()) {
                pocetZakoupenychDeskovychHer++;
            }
            if (model.getDeskovaHra(i).getOblibenost() == 3) {
                oblibeneDeskoveHry += model.getDeskovaHra(i).getNazev() + ", ";
            }
        }
        if (!oblibeneDeskoveHry.isEmpty()) {
            oblibeneDeskoveHry = oblibeneDeskoveHry.substring(0, oblibeneDeskoveHry.length() - 2);
            JOptionPane.showMessageDialog(this, "Máte zakoupeno " + pocetZakoupenychDeskovychHer + " z " + model.getModelSize() + " deskových her v seznamu.\nMezi vaše oblíbené deskové hry patří: " + oblibeneDeskoveHry);
        } else {
            JOptionPane.showMessageDialog(this, "Máte zakoupeno " + pocetZakoupenychDeskovychHer + " z " + model.getModelSize() + " deskových her v seznamu.\nNemáte žádné oblíbené deskové hry.");
        }
    }

    public void prepnoutInput(boolean b) {
        txtNazevHry.setEnabled(b);
        cbZakoupeno.setEnabled(b);
        rbOblibenost1.setEnabled(b);
        rbOblibenost2.setEnabled(b);
        rbOblibenost3.setEnabled(b);
    }
}
