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

    @Override
    public void sum(Field x, Field y) {
        el = ((Real)x).el + ((Real)y).el;
    }

    @Override
    public void dif(Field x, Field y) {
        el = ((Real)x).el - ((Real)y).el;
    }

    @Override
    public void mul(Field x, Field y) {
        el = ((Real)x).el * ((Real)y).el;
    }

    @Override
    public void div(Field x, Field y) {
        el = ((Real)x).el / ((Real)y).el;
    }

    @Override
    public void deg(Field x, int n) {
        double r = ((Real)x).el;
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
        return (Math.abs(el - ((Real)f).el) < 1e-9);
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
