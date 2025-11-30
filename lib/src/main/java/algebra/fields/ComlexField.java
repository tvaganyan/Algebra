package algebra.fields;
import org.apache.commons.math3.complex.Complex;

public class ComlexField implements Field{
    private Complex el;

    public ComlexField(double r, double i){
        el = Complex.valueOf(r, i);
    }

    @Override
    public void o() {
        el = Complex.ZERO;
    }

    @Override
    public void e() {
        el = Complex.ONE;
    }

    @Override
    public void sum(Field x, Field y) {
        el =  ((ComlexField)x).el.add(((ComlexField)y).el);
    }

    @Override
    public void dif(Field x, Field y) {
        el =  ((ComlexField)x).el.subtract(((ComlexField)y).el);
    }

    @Override
    public void mul(Field x, Field y) {
        el =  ((ComlexField)x).el.multiply(((ComlexField)y).el);
    }

    @Override
    public void div(Field x, Field y) {
        el =  ((ComlexField)x).el.divide(((ComlexField)y).el);
    }

    @Override
    public void deg(Field x, int n) {
        Complex c = ((ComlexField)x).el;
        for(int i = 0; i < n; i++)
            el = el.multiply(c);
    }

    @Override
    public boolean isO() {
        return el.abs() < 1e-12;
    }

    @Override
    public Field getNewO() {
        return new ComlexField(0,0);
    }

    @Override
    public Field getNewE() {
        return new ComlexField(1,0);
    }

    @Override
    public Field copy() {
        return new ComlexField(el.getReal(), el.getImaginary());
    }

    @Override
    public boolean equals(Field f) {
        return el.equals(((ComlexField)f).el);
    }

    public Complex getEl() {
        return el;
    }

    public void setEl(Complex el) {
        this.el = el;
    }

    @Override
    public String toString() {
        return el.toString();
    }
}
