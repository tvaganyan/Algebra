package algebra.linear;

import algebra.fields.ComplexField;
import algebra.fields.Field;
import algebra.fields.FieldEnum;
import algebra.fields.FieldFabric;
import algebra.polynomial.MatrixCharacteristics;

import java.util.List;

// Jordan algebra of Hermitian matrices of dimension 1, 2 or 3
public class JordanAlgebra {

    private int dim;
    private Matrix m;
    private FieldFabric fc;

    public boolean isHermitian(){
        Field[][] fm = m.getM();
        if(fc.getType() == FieldEnum.REAL){
            for(int i = 0; i < dim; i++){
                for(int j = i; j < dim; j++){
                    if(!fm[i][j].eq(fm[j][i]))
                        return false;
                }
            }
        }
        if(fc.getType() == FieldEnum.COMPLEX){
            for(int i = 0; i < dim; i++){
                for(int j = i; j < dim; j++){
                    Field f = fc.get0();
                    ((ComplexField)f).conjugate(fm[j][i]);
                    if(!fm[i][j].eq(f))
                        return false;
                }
            }
        }
        return true;
    }

    public JordanAlgebra(Matrix m, FieldFabric fc) {
        this.fc = fc;
        dim = m.getDim();
        this.m = new Matrix(m.getM(), fc);
        if(m.getType() != fc.getType() || !isHermitian()) {
            this.fc = null;
        }
    }

    public void o(){
        m.o();
    }

    public void e(){
        m.e();
    }

    public void sum(JordanAlgebra x, JordanAlgebra y){
        m.sum(x.m, y.m);
    }

    public void dif(JordanAlgebra x, JordanAlgebra y){
        m.dif(x.m, y.m);
    }

    public void mul(JordanAlgebra x, JordanAlgebra y){
        Matrix m1 = new Matrix(dim, fc);
        Matrix m2 = new Matrix(dim, fc);
        m1.mul(x.m, y.m);
        m2.mul(y.m, x.m);
        m.sum(m1, m2);
        if(fc.getType() == FieldEnum.REAL){
            m.scalarMul(fc.Real(0.5), m);
        }
        if(fc.getType() == FieldEnum.COMPLEX){
            m.scalarMul(fc.Complex(0.5), m);
        }
    }

    public void conjugate(JordanAlgebra x, Matrix y){  // y * x.m * y^-1
        Matrix m1 = new Matrix(dim, fc);
        m1.mul(x.m, y);
        Matrix y1 = new Matrix(dim, fc);
        y1.inverse(y);
        m.mul(y1, m1);
    }

    public void linearEquationSolution(JordanAlgebra a, JordanAlgebra b){   // a * this = b
        JordanAlgebra r1 = new JordanAlgebra(new Matrix(dim, fc), fc);
        JordanAlgebra r2 = new JordanAlgebra(new Matrix(dim, fc), fc);
        List<Field> ev = a.eigenvalues();
        Matrix d = a.diagonalizationMatrix();
        r1.conjugate(b, d);
        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++){
                if(fc.getType() == FieldEnum.REAL) {
                    Field f = fc.get0();
                    f.sum(ev.get(i), ev.get(j));
                    f.div(fc.Real(2), f);
                    r2.m.getM()[i][j].mul(r1.m.getM()[i][j], f);
                }
                if(fc.getType() == FieldEnum.COMPLEX) {
                    Field f = fc.get0();
                    f.sum(ev.get(i), ev.get(j));
                    f.div(fc.Complex(2), f);
                    r2.m.getM()[i][j].mul(r1.m.getM()[i][j], f);
                }
            }
        }
        Matrix di = new Matrix(dim, fc);
        di.inverse(d);
        conjugate(r2, di);
    }

    public List<Field> eigenvalues(){
        MatrixCharacteristics mc = new MatrixCharacteristics(m, fc);
        return mc.eigenvalues();
    }

    public Matrix diagonalizationMatrix(){
        MatrixCharacteristics mc = new MatrixCharacteristics(m, fc);
        Matrix res = new Matrix(dim, fc);
        List<Field> ev = mc.eigenvalues();
        for(int i = 0; i < dim; i++) {
            Vector v = mc.eigenvector(ev.get(i));
            v.norming();
            for(int j = 0; j < dim; j++) {
                res.getM()[j][i] = v.getV()[j];
            }
        }
        return res;
    }

    public boolean eq(JordanAlgebra x){
        return m.eq(x.m);
    }

    public int getDim() {
        return dim;
    }

    public Matrix getM() {
        return m;
    }

    public FieldEnum getType(){
        return fc.getType();
    }
}
