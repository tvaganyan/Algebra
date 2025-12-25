package algebra;

import algebra.fields.Field;
import algebra.fields.FieldEnum;
import algebra.fields.FieldFabric;
import algebra.linear.JordanAlgebra;
import algebra.linear.Matrix;
import org.junit.Test;

public class JordanAlgebraTest {
    @Test
    public void operations(){
        FieldFabric fc = new FieldFabric(0, FieldEnum.REAL);
        FieldFabric fcc = new FieldFabric(0, FieldEnum.COMPLEX);

        Field[][] m1 = new Field[2][2];
        m1[0][0] = fcc.Real(1);
        m1[0][1] = fcc.Real(2);
        m1[1][0] = fcc.Real(2);
        m1[1][1] = fcc.Real(-2);

        JordanAlgebra a = new JordanAlgebra(new Matrix(m1, fc), fc);

        Field[][] m2 = new Field[2][2];
        m2[0][0] = fcc.Complex(1);
        m2[0][1] = fcc.Complex(0, -2);
        m2[1][0] = fcc.Complex(0, 2);
        m2[1][1] = fcc.Complex(-1);

        JordanAlgebra b = new JordanAlgebra(new Matrix(m2, fcc), fcc);

        System.out.println(a.isHermitian());
        System.out.println(b.isHermitian());

        JordanAlgebra r = new JordanAlgebra(new Matrix(2, fcc), fcc);

        r.sum(a, b);
        System.out.println(r.getM());
        r.dif(a, b);
        System.out.println(r.getM());
        r.mul(a, b);
        System.out.println(r.getM());

        System.out.println(b.eigenvalues());
    }
}
