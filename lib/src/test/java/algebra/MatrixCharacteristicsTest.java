package algebra;

import algebra.fields.Field;
import algebra.fields.FieldEnum;
import algebra.fields.FieldFabric;
import algebra.linear.Matrix;
import algebra.linear.Vector;
import algebra.polynomial.MatrixCharacteristics;
import algebra.polynomial.Polynomial;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MatrixCharacteristicsTest {
    FieldFabric fc = new FieldFabric(0, FieldEnum.REAL);
    Matrix matrix;
    void init(){
        Field[][] m = new Field[3][3];
        m[0][0] = fc.Real(0);
        m[0][1] = fc.Real(1);
        m[0][2] = fc.Real(-4);
        m[1][0] = fc.Real(1);
        m[1][1] = fc.Real(3);
        m[1][2] = fc.Real(-1);
        m[2][0] = fc.Real(-4);
        m[2][1] = fc.Real(-1);
        m[2][2] = fc.Real(0);
        matrix = new Matrix(m, fc);
    }

    @Test
    public void matrixCharacteristicsReal(){
        init();
        Vector v;
        Vector v1;
        Vector v2 = new Vector(3, fc);

        MatrixCharacteristics cp = new MatrixCharacteristics(matrix, fc);
        Polynomial p = cp.characteristic();
        Map<Integer, Field> map = new TreeMap<>();
        map.put(0,fc.Real(-40));
        map.put(1,fc.Real(18));
        map.put(2,fc.Real(3));
        map.put(3,fc.Real(-1));
        Assert.assertTrue(p.eq(new Polynomial(map, fc)));

        List<Field> ev = cp.eigenvalues();
        Assert.assertTrue(ev.get(0).eq(fc.Real(5)));
        Assert.assertTrue(ev.get(1).eq(fc.Real(2)));
        Assert.assertTrue(ev.get(2).eq(fc.Real(-4)));

        v1 = cp.eigenvector(ev.get(0));
        v2.matrixMul(matrix, v1);
        v1.scalarMul(ev.get(0), v1);
        Assert.assertTrue(v1.eq(v2));

        v1 = cp.eigenvector(ev.get(1));
        v2.matrixMul(matrix, v1);
        v1.scalarMul(ev.get(1), v1);
        Assert.assertTrue(v1.eq(v2));

        v1 = cp.eigenvector(ev.get(2));
        v2.matrixMul(matrix, v1);
        v1.scalarMul(ev.get(2), v1);
        Assert.assertTrue(v1.eq(v2));
    }
}
