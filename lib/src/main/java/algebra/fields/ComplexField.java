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

    @Override
    public void sum(Field x, Field y) {
        el =  ((ComplexField)x).el.add(((ComplexField)y).el);
    }

    @Override
    public void dif(Field x, Field y) {
        el =  ((ComplexField)x).el.subtract(((ComplexField)y).el);
    }

    @Override
    public void mul(Field x, Field y) {
        el =  ((ComplexField)x).el.multiply(((ComplexField)y).el);
    }

    @Override
    public void div(Field x, Field y) {
        el =  ((ComplexField)x).el.divide(((ComplexField)y).el);
    }

    public void conjugate(Field x){
        el = ((ComplexField)x).el.conjugate();
    }

    @Override
    public void deg(Field x, int n) {
        Complex c = ((ComplexField)x).el;
        for(int i = 0; i < n; i++)
            el = el.multiply(c);
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
        return el.subtract(((ComplexField)f).el).abs() < 1e-9;
    }

    @Override
    public FieldEnum getType() {
        return FieldEnum.COMPLEX;
    }

    public Complex getEl() {
        return Complex.valueOf(el.getReal(), el.getImaginary());
    }

    @Override
    public String toString() {
        return el.toString();
    }
}
