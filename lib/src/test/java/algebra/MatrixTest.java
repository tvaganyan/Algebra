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

    Field[] v = new Field[2];
    Field[] v3 = new Field[3];
    Field[][] m = new Field[2][2];
    Field[][] mc = new Field[2][2];
    Field[][] mcr = new Field[2][2];
    Field[][] m3 = new Field[3][3];

    @Test public void matrixRealOp(){
        FieldFabric fc = new FieldFabric(0, FieldEnum.REAL);
        v[0] = fc.Real(3);
        v[1] = fc.Real(2);
        m[0][0] = fc.Real(2);
        m[0][1] = fc.Real(5);
        m[1][0] = fc.Real(1);
        m[1][1] = fc.Real(3);


        Matrix A = new Matrix(m, fc);
        Field det = A.det();

        Assert.assertTrue(det.eq(fc.Real(1)));

        Vector a = new Vector(v,fc);
        Vector x = A.linearEquationSolution(a);
        Field[] r = new Field[2];
        r[0] = fc.Real(-1);
        r[1] = fc.Real(1);
        Vector b = new Vector(r,fc);

        Assert.assertTrue(x.eq(b));

        b.matrixMul(A, x);

        Assert.assertTrue(b.eq(a));

        Matrix I = new Matrix(A.getDim(), fc);
        I.inverse(A);

        Matrix E = new Matrix(A.getDim(), fc);
        E.mul(I, A);
        I.e();
        Assert.assertTrue(E.eq(I));

        FieldFabric fcc = new FieldFabric(0, FieldEnum.COMPLEX);
        mc[0][0] = fcc.Complex(1, 1);
        mc[0][1] = fcc.Complex(0, 2);
        mc[1][0] = fcc.Complex(3, 0);
        mc[1][1] = fcc.Complex(1, -1);

        Matrix C = new Matrix(mc, fcc);
        Matrix M = new Matrix(A.getDim(), fcc);
        M.mul(C, A);
        mcr[0][0] = fcc.Complex(2, 4);
        mcr[0][1] = fcc.Complex(5, 11);
        mcr[1][0] = fcc.Complex(7, -1);
        mcr[1][1] = fcc.Complex(18, -3);
        Assert.assertTrue(M.eq(new Matrix(mcr, fcc)));

        m3[0][0] = fc.Real(-1);
        m3[0][1] = fc.Real(1);
        m3[0][2] = fc.Real(2);
        m3[1][0] = fc.Real(4);
        m3[1][1] = fc.Real(-1);
        m3[1][2] = fc.Real(1);
        m3[2][0] = fc.Real(3);
        m3[2][1] = fc.Real(-1);
        m3[2][2] = fc.Real(0);
        Matrix B = new Matrix(m3, fc);

        Assert.assertTrue(B.det().isO());

        Vector y = B.homogeneousLinearEquationSolution();

        v3[0] = fc.Real(1);
        v3[1] = fc.Real(3);
        v3[2] = fc.Real(-1);
        Vector c = new Vector(v3, fc);

        Assert.assertTrue(y.eq(c));
    }
}
