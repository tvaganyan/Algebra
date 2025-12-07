package algebra.linear;

import algebra.fields.Field;
import algebra.fields.FieldEnum;
import algebra.fields.FieldFabric;

import java.util.Arrays;

public class Vector {
    private int dim;
    private Field[] v;
    private FieldFabric fc;

    public Vector(Field[] v, FieldFabric fc) {
        this.fc = fc;
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

    public boolean eq(Vector x){
        for(int i = 0; i < dim; i++){
            if(!v[i].eq(x.v[i]))
                return false;
        }
        return true;
    }

    public double norm() {
        if(fc.getType() == FieldEnum.ZP)
            return 1;
        double res = 0;
        for(Field f: v){
            res += f.norm() * f.norm();
        }
        return Math.sqrt(res);
    }

    public void norming(){
        double n = norm();
        Field nf = null;
        if(fc.getType() == FieldEnum.ZP)
            nf = fc.Zp(1);
        if(fc.getType() == FieldEnum.COMPLEX)
            nf = fc.Complex(n);
        if(fc.getType() == FieldEnum.REAL)
            nf = fc.Real(n);
        for(int i = 0; i < dim; i++){
            v[i].div(v[i], nf);
        }
    }

    public int getDim(){
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
}
