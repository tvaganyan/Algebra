package algebra.fields;

public class Real implements Field {

    private double el;

    public Real(double d){
        el = d;
    }

    @Override
    public void o() {
        el = 0;
    }

    @Override
    public void e() {
        el = 1;
    }

    private double toDouble(Field x){
        return (double)x.getEl();
    }

    @Override
    public void sum(Field x, Field y) {
        el = toDouble(x) + toDouble(y);
    }

    @Override
    public void dif(Field x, Field y) {
        el = toDouble(x) - toDouble(y);
    }

    @Override
    public void mul(Field x, Field y) {
        el = toDouble(x) * toDouble(y);
    }

    @Override
    public void div(Field x, Field y) {
        el = toDouble(x) / toDouble(y);
    }

    @Override
    public void deg(Field x, int n) {
        double r = toDouble(x);
        for(int i = 0; i < n; i++)
            el *= r;
    }

    @Override
    public double norm() {
        return Math.abs(el);
    }

    @Override
    public boolean isO() {
        return (Math.abs(el) < 1e-9);
    }

    @Override
    public Field copy() {
        return new Real(el);
    }

    @Override
    public boolean eq(Field f) {
        System.out.println();
        return (Math.abs(el - toDouble(f)) < 1e-9);
    }

    @Override
    public FieldEnum getType() {
        return FieldEnum.REAL;
    }

    @Override
    public Double getEl() {
        return el;
    }

    @Override
    public String toString() {
        return "" + el;
    }
}
