package algebra.polynomial;

import algebra.fields.Field;
import algebra.fields.FieldFabric;
import algebra.linear.Matrix;

import java.util.Map;
import java.util.TreeMap;

public class CharacteristicPolinomial {

    private int dim;
    private Polynomial[][] pmap;
    private Polynomial p;

    FieldFabric fc;

    public CharacteristicPolinomial(Matrix m, FieldFabric fc){
        this.fc = fc;
        dim = m.getDim();
        pmap = new Polynomial[dim][dim];
        Field[][] fm = m.getM();
        Field ne = fm[0][0].getNewO();
        ne.dif(ne.getNewO(),ne.getNewE());
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                Map<Integer, Field> map = new TreeMap<>();
                map.put(0, fm[i][j]);
                if(i == j){
                    map.put(1, ne);
                }
                pmap[i][j] = new Polynomial(map, fc);
            }
        }
        p = pmap[0][0].getNewO();
    }

    public CharacteristicPolinomial(Polynomial[][] pm, FieldFabric fc){
        this.fc = fc;
        dim = pm[0].length;
        pmap = new Polynomial[dim][dim];
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++) {
                pmap[i][j] = pm[i][j].copy();
            }
        }
        p = pmap[0][0].getNewO();
    }

    private CharacteristicPolinomial minor(int a, int b){
        Polynomial[][] pmap1 = new Polynomial[dim-1][dim-1];
        for(int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if(i < a) {
                    if(j < b)
                        pmap1[i][j] = pmap[i][j];
                    if(j > b)
                        pmap1[i][j-1] = pmap[i][j];
                }
                if(i > a) {
                    if(j < b)
                        pmap1[i-1][j] = pmap[i][j];
                    if(j > b)
                        pmap1[i-1][j-1] = pmap[i][j];
                }
            }
        }
        return new CharacteristicPolinomial(pmap1, fc);
    }

    public Polynomial characteristic(){
        if(dim == 1)
            return pmap[0][0];
        Polynomial res = p.getNewO();
        for(int i = 0; i < dim; i++){
            Polynomial g = p.getNewO();
            g.mul(pmap[0][i], minor(0,i).characteristic());
            if(i % 2 == 0)
                res.sum(res.copy(),g);
            else
                res.dif(res.copy(),g);
        }
        return res;
    }

    public int getDim() {
        return dim;
    }

    public Polynomial[][] getPmap() {
        return pmap;
    }

    public String toString() {
        String res = "";
        for (int i = 0; i < dim; i++){
            res += "[";
            for (int j = 0; j < dim; j++){
               res += pmap[i][j];
               if(i < dim - 1)
                   res += ", ";
            }
            res += "]\n";
        }
        return res;
    }
}
