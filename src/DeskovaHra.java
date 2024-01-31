public class DeskovaHra {
    private String nazev;
    private boolean zakoupeno;
    private int oblibenost;

    public DeskovaHra(String nazev, boolean zakoupeno, int oblibenost) {
        this.nazev = nazev;
        this.zakoupeno = zakoupeno;
        if (oblibenost > 0 && oblibenost < 4) {
            this.oblibenost = oblibenost;
        }
        else {
            System.err.println("Oblíbenost musí být v rozsahu 1-3, oblíbenost byla nastavena na 1!");
            this.oblibenost = 1;
        }
    }

    public String getNazev() {
        return nazev;
    }

    public void setNazev(String nazev) {
        this.nazev = nazev;
    }

    public boolean isZakoupeno() {
        return zakoupeno;
    }

    public void setZakoupeno(boolean zakoupeno) {
        this.zakoupeno = zakoupeno;
    }

    public int getOblibenost() {
        return oblibenost;
    }

    public void setOblibenost(int oblibenost) {
        if (oblibenost > 0 && oblibenost < 4) {
            this.oblibenost = oblibenost;
        }
        else {
            System.err.println("Oblíbenost musí být v rozsahu 1-3!");
        }
    }
}
