package algebra.linear;

import algebra.fields.Field;
import algebra.fields.Real;

import java.util.Arrays;

public class Matrix {
    private int dim;
    private Field[][] m;


    public Matrix(Field[][] m) {
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
                m[i][j].o();
                Field f = m[i][j].getNewO();
                for(int k = 0; k < dim; k++) {
                    f.mul(x.m[i][k], y.m[k][j]);
                    m[i][j].sum(m[i][j],f);
                }
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
        return new Matrix(m1);
    }

    private Matrix columnReplacement(int j, Vector a){
        Matrix res = new Matrix(m);
        for(int i = 0; i < dim; i++){
            res.m[i][j] = a.getV()[i];
        }
        return res;
    }

    public Field det(){
        if(dim == 1)
            return m[0][0];
        Field res = m[0][0].getNewO();
        Field f = m[0][0].getNewO();
        for(int i = 0; i < dim; i++){
            f.mul(m[0][i], minor(0,i).det());
            if(i % 2 == 0)
                res.sum(res,f);
            else
                res.dif(res,f);
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

    public boolean equals(Matrix x){
        boolean res = true;
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++) {
                if (!m[i][j].equals(x.m[i][j]))
                    return false;
            }
        }
        return res;
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

    public static void main(String[] s){
        Field[] v = new Field[2];
        Field[][] m = new Field[2][2];
        v[0] = new Real(5);
        v[1] = new Real(1);
        m[0][0] = new Real(1);
        m[0][1] = new Real(1);
        m[1][0] = new Real(2);
        m[1][1] = new Real(-1);
        Vector a = new Vector(v);
        Matrix A = new Matrix(m);
        Vector x = A.linearEquationSolution(a); //2,3
        Matrix B = new Matrix(m);
        B.inverse(A);
        System.out.println("A: " + A);
        System.out.println("a: " + a);
        System.out.println("det: " + A.det());
        System.out.println("x: " + x);
        System.out.println("B: " + B);
    }
}
