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
    public boolean isO() {
        return (Math.abs(el) < 1e-12);
    }

    @Override
    public Field getNewO() {
        return new Real(0);
    }

    @Override
    public Field copy() {
        return new Real(el);
    }

    @Override
    public boolean equals(Field f) {
        return (el == ((Real)f).el);
    }

    public double getEl() {
        return el;
    }

    public void setEl(double el) {
        this.el = el;
    }

    @Override
    public String toString() {
        return "" + el;
    }
}
