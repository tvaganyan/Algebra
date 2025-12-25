package algebra;

import algebra.fields.Field;
import algebra.fields.FieldEnum;
import algebra.fields.FieldFabric;
import algebra.fields.Real;
import algebra.polynomial.Polynomial;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PolinomialTest {

    @Test
    public void polinomialRealOp(){
        Map<Integer, Field> map1 = new TreeMap<>();
        Map<Integer, Field> map2 = new TreeMap<>();
        FieldFabric fc = new FieldFabric(0, FieldEnum.REAL);

        map1.put(0, fc.Real(3));
        map1.put(1, fc.Real(1));
        map1.put(2, fc.Real(2));

        map2.put(0, fc.Real(3));
        map2.put(1, fc.Real(1));
        map2.put(2, fc.Real(-2));

        Polynomial x = new Polynomial(map1, fc);
        Polynomial y = new Polynomial(map2, fc);
        Polynomial z = new Polynomial(map1, fc);

        Map<Integer, Field> sum = new TreeMap<>();
        sum.put(0,fc.Real(6));
        sum.put(1,fc.Real(2));
        z.sum(x,y);
        Assert.assertTrue(z.eq(new Polynomial(sum, fc)));

        Map<Integer, Field> dif = new TreeMap<>();
        dif.put(2,fc.Real(4));
        z.dif(x,y);
        Assert.assertTrue(z.eq(new Polynomial(dif, fc)));

        Map<Integer, Field> mul = new TreeMap<>();
        mul.put(0,fc.Real(9));
        mul.put(1,fc.Real(6));
        mul.put(2,fc.Real(1));
        mul.put(4,fc.Real(-4));
        z.mul(x,y);
        Assert.assertTrue(z.eq(new Polynomial(mul, fc)));

    }

    @Test
    public void polinomialRootsDeg2(){
        Map<Integer, Field> map1 = new TreeMap<>();
        Map<Integer, Field> map2 = new TreeMap<>();
        Map<Integer, Field> map3 = new TreeMap<>();

        FieldFabric fc = new FieldFabric(0,FieldEnum.REAL);
        List<Field> roots;

        map1.put(0, fc.Real(-21));
        map1.put(1, fc.Real(-4));
        map1.put(2, fc.Real(1));

        Polynomial plm = new Polynomial(map1, fc);
        roots = plm.roots();
        Assert.assertTrue(roots.get(0).eq(fc.Real(7)));
        Assert.assertTrue(roots.get(1).eq(fc.Real(-3)));

        FieldFabric fcc = new FieldFabric(0,FieldEnum.COMPLEX);

        map2.put(0, fcc.Complex(5,1));
        map2.put(1, fcc.Complex(-3,-2));
        map2.put(2, fcc.Complex(1,0));

        plm = new Polynomial(map2, fcc);
        roots = plm.roots();
        Assert.assertTrue(roots.get(0).eq(fcc.Complex(2,3)));
        Assert.assertTrue(roots.get(1).eq(fcc.Complex(1,-1)));

        map3.put(0, fc.Real(1));
        map3.put(2, fc.Real(1));
        plm = new Polynomial(map3, fc);
        roots = plm.roots();
        Assert.assertTrue(roots.get(0).eq(fcc.Complex(0,-1)));
        Assert.assertTrue(roots.get(1).eq(fcc.Complex(0,1)));

        System.out.println(roots);
    }

    @Test
    public void polinomialRootsDeg3() {
        Map<Integer, Field> map1 = new TreeMap<>();
        Map<Integer, Field> map2 = new TreeMap<>();
        Map<Integer, Field> map3 = new TreeMap<>();

        FieldFabric fc = new FieldFabric(0, FieldEnum.REAL);
        List<Field> roots;

        map1.put(0, fc.Real(30));
        map1.put(1, fc.Real(-19));
        map1.put(2, fc.Real(0));
        map1.put(3, fc.Real(1));

        Polynomial plm = new Polynomial(map1, fc);
        roots = plm.roots();
        Assert.assertTrue(roots.get(0).eq(fc.Real(3)));
        Assert.assertTrue(roots.get(1).eq(fc.Real(2)));
        Assert.assertTrue(roots.get(2).eq(fc.Real(-5)));

        FieldFabric fcc = new FieldFabric(0,FieldEnum.COMPLEX);

        map2.put(0, fcc.Complex(-48,36));
        map2.put(1, fcc.Complex(21,15));
        map2.put(2, fcc.Complex(12,9));
        map2.put(3, fcc.Complex(3,0));

        plm = new Polynomial(map2, fcc);
        roots = plm.roots();
        Assert.assertTrue(roots.get(0).eq(fcc.Complex(-2,2)));
        Assert.assertTrue(roots.get(1).eq(fcc.Complex(1,-1)));
        Assert.assertTrue(roots.get(2).eq(fcc.Complex(-3,-4)));

        map3.put(0, fc.Real(1));
        map3.put(3, fc.Real(1));
        plm = new Polynomial(map3, fc);
        roots = plm.roots();
        Assert.assertTrue(roots.get(0).eq(fcc.Complex(0.5,Math.sqrt(3) / 2)));
        Assert.assertTrue(roots.get(1).eq(fcc.Complex(-1,0)));
        Assert.assertTrue(roots.get(2).eq(fcc.Complex(0.5,- Math.sqrt(3) / 2)));
        System.out.println(roots);
    }
}
