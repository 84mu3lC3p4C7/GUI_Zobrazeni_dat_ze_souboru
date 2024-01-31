import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private Model model;
    private int indexNacteneHry = 0;

    public DataDeskovychHer(Model model) {
        this.model = model;

        if (model.getModelSize() == 0) {
            JOptionPane.showMessageDialog(this, "Data deskových her jsou prázdná!");
            btnPredchozi.setEnabled(false);
            btnDalsi.setEnabled(false);
            btnUlozit.setEnabled(false);
        }

        ButtonGroup oblibenostGroup = new ButtonGroup();
        oblibenostGroup.add(rbOblibenost1);
        oblibenostGroup.add(rbOblibenost2);
        oblibenostGroup.add(rbOblibenost3);

        btnPredchozi.setEnabled(false);
        if (model.getModelSize() < 2) {
            btnDalsi.setEnabled(false);
        }

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
                ulozDataDeskoveHry();
            }
        });
    }

    public JPanel getMainPanel() {
        return MainPanel;
    }

    public void zobrazitDataDeskoveHry() {
        DeskovaHra deskovaHra = model.getDeskovaHra(indexNacteneHry);
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

    public void ulozDataDeskoveHry() {
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
}
