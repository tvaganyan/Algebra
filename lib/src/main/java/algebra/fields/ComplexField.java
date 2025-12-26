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
        Field cx = x.copy();
        Field cy = y.copy();
        if(x.getType() == FieldEnum.REAL)
            cx = new ComplexField((Double) x.getEl());
        if(y.getType() == FieldEnum.REAL)
            cy = new ComplexField((Double) y.getEl());
        el =  ((ComplexField)cx).el.add(((ComplexField)cy).el);
    }

    @Override
    public void dif(Field x, Field y) {
        Field cx = x.copy();
        Field cy = y.copy();
        if(x.getType() == FieldEnum.REAL)
            cx = new ComplexField((Double) x.getEl());
        if(y.getType() == FieldEnum.REAL)
            cy = new ComplexField((Double) y.getEl());
        el =  ((ComplexField)cx).el.subtract(((ComplexField)cy).el);
    }

    @Override
    public void mul(Field x, Field y) {
        Field cx = x.copy();
        Field cy = y.copy();
        if(x.getType() == FieldEnum.REAL)
            cx = new ComplexField((Double) x.getEl());
        if(y.getType() == FieldEnum.REAL)
            cy = new ComplexField((Double) y.getEl());
        el =  ((ComplexField)cx).el.multiply(((ComplexField)cy).el);
    }

    @Override
    public void div(Field x, Field y) {
        Field cx = x.copy();
        Field cy = y.copy();
        if(x.getType() == FieldEnum.REAL)
            cx = new ComplexField((Double) x.getEl());
        if(y.getType() == FieldEnum.REAL)
            cy = new ComplexField((Double) y.getEl());
        el =  ((ComplexField)cx).el.divide(((ComplexField)cy).el);
    }

    public void conjugate(Field x){
        Field cx = x.copy();
        if(x.getType() == FieldEnum.REAL)
            cx = new ComplexField((Double) x.getEl());
        el = ((ComplexField)cx).el.conjugate();
    }

    @Override
    public void deg(Field x, int n) {
        Field cx = x.copy();
        if(x.getType() == FieldEnum.REAL)
            cx = new ComplexField((Double) x.getEl());
        Complex c = ((ComplexField)cx).el;
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

    @Override
    public Complex getEl() {
        return Complex.valueOf(el.getReal(), el.getImaginary());
    }

    @Override
    public String toString() {
        return el.toString();
    }
}
