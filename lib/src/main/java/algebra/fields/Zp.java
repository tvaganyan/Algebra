package algebra.fields;

public class Zp implements Field{
    private int p, el;  // p must be a prime number

    public Zp(int p, int el) {
        this.p = p;
        this.el = el;
    }

    private int modP(int k){
        int r = k % p;
        if(r < 0)
            r = p + r;
        return r;
    }

    @Override
    public void o() {
        el = 0;
    }

    @Override
    public void e() {
        el = 1;
    }

    @Override
    public void sum(Field x, Field y) {
        el = modP(((Zp)x).el + ((Zp)y).el);
    }

    @Override
    public void dif(Field x, Field y) {
        el = modP(((Zp)x).el - ((Zp)y).el);
    }

    @Override
    public void mul(Field x, Field y) {
        el = modP(((Zp)x).el * ((Zp)y).el);
    }

    @Override
    public void div(Field x, Field y) {
        int x1 = ((Zp)x).el;
        int y1 = ((Zp)y).el;
        for(int k = 1; k < p; k++){
            if(modP(k * y1) == 1) {
                el = modP(x1 * k);
                break;
            }
        }
    }

    @Override
    public void deg(Field x, int n) {
        int r = ((Zp)x).el;
        for(int i = 0; i < n; i++)
            el *= r;
    }

    @Override
    public double norm() {
        if(el == 0)
            return 0;
        return 1;
    }

    @Override
    public boolean isO() {
        return (el == 0);
    }

    @Override
    public Field copy() {
        return new Zp(p, el);
    }

    @Override
    public boolean eq(Field f) {
        return (el == ((Zp)f).el);
    }

    @Override
    public FieldEnum getType() {
        return FieldEnum.ZP;
    }

    public int getP() {
        return p;
    }

    @Override
    public Integer getEl() {
        return el;
    }

    @Override
    public String toString() {
        return "" + el;
    }
}
