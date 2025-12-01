package algebra.fields;

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
                res = new ComlexField(0,0);
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
                res = new ComlexField(1,0);
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
                res = new ComlexField(-1,0);
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
        return new ComlexField(r,i);
    }
    public Field Zp(int n){
        return new Zp(p, n);
    }
}
