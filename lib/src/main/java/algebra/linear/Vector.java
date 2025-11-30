package algebra.linear;

import algebra.fields.Field;
import algebra.fields.Real;

import java.util.Arrays;

public class Vector {
    private int dim;
    private Field[] v;

    public Vector(Field[] v) {
        dim = v.length;
        this.v = new Field[dim];
        for(int i = 0; i < dim; i++){
            this.v[i] = v[i].copy();
        }
    }

    void o(){
        for(Field x: v){
            x.o();
        }
    }

    void e(int n){
        for(int i = 0; i < dim; i++){
            if(i == n)
                v[i].e();
            else
                v[i].o();
        }
    }

    void sum(Vector x, Vector y){
        for(int i = 0; i < dim; i++){
            v[i].sum(x.v[i], y.v[i]);
        }
    }

    void dif(Vector x, Vector y){
        for(int i = 0; i < dim; i++){
            v[i].dif(x.v[i], y.v[i]);
        }
    }

    void scalarMul(Field s, Vector x){
        for(int i = 0; i < dim; i++){
            v[i].mul(s, x.v[i]);
        }
    }

    public boolean equals(Vector x){
        boolean res = true;
        for(int i = 0; i < dim; i++){
            if(!v[i].equals(x.v[i]))
                return false;
        }
        return res;
    }

    public int getDim() {
        return dim;
    }

    public Field[] getV() {
        return v;
    }

    public void setV(Field[] v) {
        this.v = v;
    }

    @Override
    public String toString() {
        return Arrays.toString(v);
    }

    public static void main(String[] s){
        Field[] v = new Field[2];
        for(int i = 0; i < 2; i++){
            v[i] = new Real(2+i);
        }
        Vector a = new Vector(v);
        System.out.println(a);
    }
}
