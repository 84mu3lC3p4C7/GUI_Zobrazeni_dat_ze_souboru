public class DeskovaHra {
    private String nazev;
    private boolean zakoupeno;
    private int oblibenost;
    private String chybneData;

    public DeskovaHra(String nazev, boolean zakoupeno, int oblibenost, String chybneData) throws NumberFormatException {
        this.nazev = nazev;
        this.zakoupeno = zakoupeno;
        if (oblibenost > 0 && oblibenost < 4) {
            this.oblibenost = oblibenost;
        }
        else {
            throw new NumberFormatException();
        }
        this.chybneData = chybneData;
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

    public void setOblibenost(int oblibenost) throws NumberFormatException {
        if (oblibenost > 0 && oblibenost < 4) {
            this.oblibenost = oblibenost;
        }
        else {
            throw new NumberFormatException();
        }
    }

    public String getChybneData() {
        return chybneData;
    }
}
