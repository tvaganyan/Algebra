package algebra;

import algebra.fields.Field;
import algebra.fields.FieldEnum;
import algebra.fields.FieldFabric;
import algebra.linear.Matrix;
import algebra.polynomial.CharacteristicPolinomial;
import algebra.polynomial.Polynomial;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

public class CharacteristicPolinomialTest {
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
    public void matrixCharacteristicReal(){
        init();
        CharacteristicPolinomial cp = new CharacteristicPolinomial(matrix, fc);
        Polynomial p = cp.characteristic();
        Map<Integer, Field> map = new TreeMap<>();
        map.put(0,fc.Real(-40));
        map.put(1,fc.Real(18));
        map.put(2,fc.Real(3));
        map.put(3,fc.Real(-1));
        Assert.assertTrue(p.equals(new Polynomial(map, fc)));
    }
}
