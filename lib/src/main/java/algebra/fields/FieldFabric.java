package algebra.fields;

import org.apache.commons.math3.complex.Complex;

public class FieldFabric {
    private int p;
    FieldEnum type;

    public FieldFabric(int p, FieldEnum type){
        this.p = p;
        this.type = type;
    }

    public Field get0(){
        Field res = null;
        switch (type){
            case REAL:
                res = new Real(0);
                break;
            case COMPLEX:
                res = new ComplexField(0,0);
                break;
            case ZP:
                res = new Zp(p,0);
                break;
        }
        return res;
    }

    public Field get1(){
        Field res = null;
        switch (type){
            case REAL:
                res = new Real(1);
                break;
            case COMPLEX:
                res = new ComplexField(1,0);
                break;
            case ZP:
                res = new Zp(p,1);
                break;
        }
        return res;
    }

    public Field getMinus1(){
        Field res = null;
        switch (type){
            case REAL:
                res = new Real(-1);
                break;
            case COMPLEX:
                res = new ComplexField(-1,0);
                break;
            case ZP:
                res = new Zp(p,p - 1);
                break;
        }
        return res;
    }

    public Field Real(double r){
        return new Real(r);
    }
    public Field Complex(double r, double i){
        return new ComplexField(r,i);
    }
    public Field Complex(Complex c){
        return new ComplexField(c);
    }
    public Field Complex(double r){
        return new ComplexField(r);
    }
    public Field Zp(int n){
        return new Zp(p, n);
    }

    public int getP() {
        return p;
    }

    public FieldEnum getType() {
        return type;
    }
}
