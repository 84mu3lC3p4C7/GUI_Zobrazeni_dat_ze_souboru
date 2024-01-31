import java.util.ArrayList;
import java.util.List;

public class Model {
    private List<DeskovaHra> seznamDeskovychHer = new ArrayList<>();

    public Model(List<DeskovaHra> seznamDeskovychHer) {
        this.seznamDeskovychHer = seznamDeskovychHer;
    }

    public DeskovaHra getDeskovaHra(int index) {
        return seznamDeskovychHer.get(index);
    }

    public void setDeskovaHra(int index, DeskovaHra deskovaHra) {
        seznamDeskovychHer.set(index, deskovaHra);
    }

    public int getModelSize() {
        return seznamDeskovychHer.size();
    }
}
