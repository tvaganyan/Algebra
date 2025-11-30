package algebra;

import algebra.fields.Field;
import algebra.fields.Real;
import algebra.linear.Matrix;
import algebra.linear.Vector;
import org.junit.Assert;
import org.junit.Test;

public class MatrixTest {
    Field[] v = new Field[2];
    Field[][] m = new Field[2][2];

    void init(){
        v[0] = new Real(3);
        v[1] = new Real(2);
        m[0][0] = new Real(2);
        m[0][1] = new Real(5);
        m[1][0] = new Real(1);
        m[1][1] = new Real(3);
    }

    @Test public void matrixRealOp(){
        init();
        Matrix A = new Matrix(m);
        Field det = A.det();
        Assert.assertTrue(det.equals(new Real(1)));

        Vector a = new Vector(v);
        Vector x = A.linearEquationSolution(a);
        Field[] r = new Field[2];
        r[0] = new Real(-1);
        r[1] = new Real(1);
        Assert.assertTrue(x.equals(new Vector(r)));

        Matrix I = new Matrix(m);
        I.inverse(A);

        Matrix E = new Matrix(m);
        E.mul(I, A);
        I.e();

        Assert.assertTrue(E.equals(I));
    }
}
