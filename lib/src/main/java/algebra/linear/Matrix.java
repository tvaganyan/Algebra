package algebra.linear;

import algebra.fields.Field;
import algebra.fields.FieldFabric;

public class Matrix {
    private int dim;
    private Field[][] m;
    FieldFabric fc;


    public Matrix(Field[][] m, FieldFabric fc) {
        this.fc = fc;
        dim = m[0].length;
        this.m = new Field[dim][dim];
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++) {
                this.m[i][j] = m[i][j].copy();
            }
        }
    }

    public void o(){
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++) {
                m[i][j].o();
            }
        }
    }

    public void e(){
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++) {
                if(i == j)
                    m[i][j].e();
                else
                    m[i][j].o();
            }
        }
    }

    public void sum(Matrix x, Matrix y){
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++) {
                m[i][j].sum(x.m[i][j], y.m[i][j]);
            }
        }
    }

    public void dif(Matrix x, Matrix y){
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++) {
                m[i][j].dif(x.m[i][j], y.m[i][j]);
            }
        }
    }

    public void mul(Matrix x, Matrix y){
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++) {
                Field s = fc.get0();
                Field g = fc.get0();
                for(int k = 0; k < dim; k++) {
                    g.mul(x.m[i][k], y.m[k][j]);
                    s.sum(s,g);
                }
                m[i][j] = s;
            }
        }
    }

    public void inverse(Matrix x){
        Vector a = new Vector(x.m[0]);
        for(int i = 0; i < dim; i++){
            a.e(i);
            for (int j = 0; j < dim; j++) {
                m[j][i] = x.linearEquationSolution(a).getV()[j];
            }
        }
    }

    public void scalarMul(Field s, Matrix x){
        for(int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                m[i][j].mul(s, x.m[i][j]);
            }
        }
    }

    private Matrix minor(int a, int b){
        Field[][] m1 = new Field[dim-1][dim-1];
        for(int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if(i < a) {
                    if(j < b)
                        m1[i][j] = m[i][j];
                    if(j > b)
                        m1[i][j-1] = m[i][j];
                }
                if(i > a) {
                    if(j < b)
                        m1[i-1][j] = m[i][j];
                    if(j > b)
                        m1[i-1][j-1] = m[i][j];
                }
            }
        }
        return new Matrix(m1, fc);
    }

    private Matrix columnReplacement(int j, Vector a){
        Matrix res = new Matrix(m, fc);
        for(int i = 0; i < dim; i++){
            res.m[i][j] = a.getV()[i];
        }
        return res;
    }

    public Field det(){
        if(dim == 1)
            return m[0][0];
        Field res = fc.get0();
        for(int i = 0; i < dim; i++){
            Field g = fc.get0();
            g.mul(m[0][i], minor(0,i).det());
            if(i % 2 == 0)
                res.sum(res,g);
            else
                res.dif(res,g);
        }
        return res;
    }

    public Vector linearEquationSolution(Vector a){
        Vector res = new Vector(a.getV());
        Field d = det();
        for(int j = 0; j < dim; j++){
            res.getV()[j].div(columnReplacement(j, a).det(), d);
        }
        return res;
    }

    public boolean eq(Matrix x){
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++) {
                if (!m[i][j].eq(x.m[i][j]))
                    return false;
            }
        }
        return true;
    }

    public int getDim() {
        return dim;
    }

    public Field[][] getM() {
        return m;
    }


    @Override
    public String toString() {
        String res = "";
        for(int i = 0; i < dim; i++) {
            res += "[";
            for (int j = 0; j < dim; j++) {
                res += m[i][j];
                if(j < dim - 1)
                    res += ", ";
            }
            res += "] \n";
        }
        return res;
    }
}
