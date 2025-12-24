package algebra.polynomial;

import algebra.fields.Field;
import algebra.fields.FieldEnum;
import algebra.fields.FieldFabric;
import algebra.linear.Matrix;
import algebra.linear.Vector;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

// Matrix dimension must be equal to 1, 2 or 3
public class MatrixCharacteristics {
    private int dim;
    private Polynomial[][] pmap;
    private Polynomial p;

    private Matrix m;

    FieldFabric fc;

    public MatrixCharacteristics(Matrix m, FieldFabric fc){
        if(m.getType() != fc.getType())
            return;

        this.m = m;
        this.fc = fc;
        dim = m.getDim();
        pmap = new Polynomial[dim][dim];
        Field me = this.fc.getMinus1();
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                Map<Integer, Field> map = new TreeMap<>();
                map.put(0, m.getM()[i][j].copy());
                if(i == j){
                    map.put(1, me);
                }
                pmap[i][j] = new Polynomial(map, fc);
            }
        }
        p = pmap[0][0].getNewO();
    }

    private MatrixCharacteristics(Polynomial[][] pm, FieldFabric fc){
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

    private MatrixCharacteristics minor(int a, int b){
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
        return new MatrixCharacteristics(pmap1, fc);
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
        this.p = res;
        return res;
    }

    public List<Field> eigenvalues(){
        return characteristic().roots();
    }

    public Vector eigenvector(Field eigenvalue){
        Matrix m1 = new Matrix(dim, fc);
        m1.e();
        m1.scalarMul(eigenvalue, m1);
        m1.dif(m, m1);
        return m1.homogeneousLinearEquationSolution();
    }

    public int getDim() {
        return dim;
    }

    public Polynomial[][] getPmap() {
        return pmap;
    }

    public Polynomial getP() {
        return p;
    }

    public Matrix getM() {
        return m;
    }

    public FieldEnum getType(){
        return fc.getType();
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
