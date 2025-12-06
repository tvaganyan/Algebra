package algebra;

import algebra.fields.Field;
import algebra.fields.FieldEnum;
import algebra.fields.FieldFabric;
import algebra.fields.Real;
import algebra.linear.Matrix;
import algebra.linear.Vector;
import org.junit.Assert;
import org.junit.Test;

public class MatrixTest {

    FieldFabric fc = new FieldFabric(0, FieldEnum.REAL);
    Field[] v = new Field[2];
    Field[][] m = new Field[2][2];

    void init(){
        v[0] = fc.Real(3);
        v[1] = fc.Real(2);
        m[0][0] = fc.Real(2);
        m[0][1] = fc.Real(5);
        m[1][0] = fc.Real(1);
        m[1][1] = fc.Real(3);
    }

    @Test public void matrixRealOp(){
        init();
        Matrix A = new Matrix(m, fc);
        Field det = A.det();
        Assert.assertTrue(det.eq(fc.Real(1)));

        Vector a = new Vector(v);
        Vector x = A.linearEquationSolution(a);
        Field[] r = new Field[2];
        r[0] = fc.Real(-1);
        r[1] = fc.Real(1);
        Assert.assertTrue(x.eq(new Vector(r)));

        Matrix I = new Matrix(m, fc);
        I.inverse(A);

        Matrix E = new Matrix(m, fc);
        E.mul(I, A);
        I.e();

        Assert.assertTrue(E.eq(I));
    }
}
