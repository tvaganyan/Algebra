package algebra;

import algebra.fields.Field;
import algebra.fields.FieldEnum;
import algebra.fields.FieldFabric;
import algebra.linear.JordanAlgebra;
import algebra.linear.Matrix;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class JordanAlgebraTest {
    @Test
    public void operations(){
        FieldFabric fcc = new FieldFabric(0, FieldEnum.COMPLEX);

        Field[][] m1 = new Field[2][2];
        m1[0][0] = fcc.Complex(1);
        m1[0][1] = fcc.Complex(2);
        m1[1][0] = fcc.Complex(2);
        m1[1][1] = fcc.Complex(-2);

        JordanAlgebra a = new JordanAlgebra(new Matrix(m1, fcc), fcc);

        Field[][] m2 = new Field[2][2];
        m2[0][0] = fcc.Complex(1);
        m2[0][1] = fcc.Complex(0, -2);
        m2[1][0] = fcc.Complex(0, 2);
        m2[1][1] = fcc.Complex(-1);

        JordanAlgebra b = new JordanAlgebra(new Matrix(m2, fcc), fcc);

        JordanAlgebra r = new JordanAlgebra(new Matrix(2, fcc), fcc);
        Field[][] mr = new Field[2][2];

        r.sum(a, b);
        mr[0][0] = fcc.Complex(2);
        mr[0][1] = fcc.Complex(2, -2);
        mr[1][0] = fcc.Complex(2, 2);
        mr[1][1] = fcc.Complex(-3);
        Assert.assertTrue(r.eq(new JordanAlgebra(new Matrix(mr, fcc), fcc)));

        r.dif(a, b);
        mr[0][0] = fcc.Complex(0);
        mr[0][1] = fcc.Complex(2, 2);
        mr[1][0] = fcc.Complex(2, -2);
        mr[1][1] = fcc.Complex(-1);
        Assert.assertTrue(r.eq(new JordanAlgebra(new Matrix(mr, fcc), fcc)));

        r.mul(a, b);
        mr[0][0] = fcc.Complex(1);
        mr[0][1] = fcc.Complex(0, 1);
        mr[1][0] = fcc.Complex(0, -1);
        mr[1][1] = fcc.Complex(2);
        Assert.assertTrue(r.eq(new JordanAlgebra(new Matrix(mr, fcc), fcc)));

        List<Field> ev = b.eigenvalues();
        Assert.assertTrue(fcc.Complex(Math.sqrt(5)).eq(ev.get(0)));
        Assert.assertTrue(fcc.Complex(-Math.sqrt(5)).eq(ev.get(1)));

        Matrix d = b.diagonalizationMatrix();
        Assert.assertTrue(d.det().norm() == 1);

        r.conjugate(b, d);
        mr[0][0] = fcc.Complex(Math.sqrt(5));
        mr[0][1] = fcc.Complex(0);
        mr[1][0] = fcc.Complex(0);
        mr[1][1] = fcc.Complex(-Math.sqrt(5));
        Assert.assertTrue(r.eq(new JordanAlgebra(new Matrix(mr, fcc), fcc)));

        r.linearEquationSolution(a, b);
        mr[0][0] = fcc.Complex(-1);
        mr[0][1] = fcc.Complex(1, 4);
        mr[1][0] = fcc.Complex(1, -4);
        mr[1][1] = fcc.Complex(1.5);
        Assert.assertTrue(r.eq(new JordanAlgebra(new Matrix(mr, fcc), fcc)));

        JordanAlgebra x = new JordanAlgebra(new Matrix(2, fcc), fcc);

        x.mul(a, r);
        Assert.assertTrue(x.eq(b));
    }
}
