package algebra.fields;
import org.apache.commons.math3.complex.Complex;

public class ComplexField implements Field{
    private Complex el;

    public ComplexField(Complex c){
        el =  Complex.valueOf(c.getReal(), c.getImaginary());
    }

    public ComplexField(double r, double i){
        el = Complex.valueOf(r, i);
    }

    public ComplexField(double r){
        el = Complex.valueOf(r, 0);
    }

    @Override
    public void o() {
        el = Complex.ZERO;
    }

    @Override
    public void e() {
        el = Complex.ONE;
    }

    private Complex toComplex(Field x){
        if(x.getType() == FieldEnum.COMPLEX)
            return (Complex) x.getEl();
        return new Complex((double) x.getEl());
    }

    @Override
    public void sum(Field x, Field y) {
        el =  toComplex(x).add(toComplex(y));
    }

    @Override
    public void dif(Field x, Field y) {
        el = toComplex(x).subtract(toComplex(y));
    }

    @Override
    public void mul(Field x, Field y) {
        el = toComplex(x).multiply(toComplex(y));
    }

    @Override
    public void div(Field x, Field y) {
        el = toComplex(x).divide(toComplex(y));
    }

    public void conjugate(Field x){
        el = toComplex(x).conjugate();
    }

    @Override
    public void deg(Field x, int n) {
        Complex cx = toComplex(x);
        for(int i = 0; i < n; i++)
            el = el.multiply(cx);
    }

    @Override
    public double norm() {
        return el.abs();
    }

    @Override
    public boolean isO() {
        return el.abs() < 1e-9;
    }

    @Override
    public Field copy() {
        return new ComplexField(el.getReal(), el.getImaginary());
    }

    @Override
    public boolean eq(Field f) {
        return el.subtract(toComplex(f)).abs() < 1e-9;
    }

    @Override
    public FieldEnum getType() {
        return FieldEnum.COMPLEX;
    }

    @Override
    public Complex getEl() {
        return Complex.valueOf(el.getReal(), el.getImaginary());
    }

    @Override
    public String toString() {
        return el.toString();
    }
}
