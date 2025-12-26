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

    private int toZp(Field x){
        return (int)x.getEl();
    }

    @Override
    public void sum(Field x, Field y) {
        el = modP(toZp(x) + toZp(y));
    }

    @Override
    public void dif(Field x, Field y) {
        el = modP(toZp(x) - toZp(y));
    }

    @Override
    public void mul(Field x, Field y) {
        el = modP(toZp(x) * toZp(y));
    }

    @Override
    public void div(Field x, Field y) {
        int x1 = toZp(x);
        int y1 = toZp(y);
        for(int k = 1; k < p; k++){
            if(modP(k * y1) == 1) {
                el = modP(x1 * k);
                break;
            }
        }
    }

    @Override
    public void deg(Field x, int n) {
        int r = toZp(x);
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
        return (el == toZp(f));
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
