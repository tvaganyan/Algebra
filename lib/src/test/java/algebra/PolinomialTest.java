package algebra;

import algebra.fields.Field;
import algebra.fields.Real;
import algebra.polynomial.Polynomial;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

public class PolinomialTest {
    Map<Integer, Field> map1 = new TreeMap<>();
    Map<Integer, Field> map2 = new TreeMap<>();

    void init(){
        map1.put(0, new Real(3));
        map1.put(1, new Real(1));
        map1.put(2, new Real(2));

        map2.put(0, new Real(3));
        map2.put(1, new Real(1));
        map2.put(2, new Real(-2));
    }

    @Test
    public void polinomialRealOp(){
        init();
        Polynomial x = new Polynomial(map1);
        Polynomial y = new Polynomial(map2);
        Polynomial z = new Polynomial(map1);

        Map<Integer, Field> sum = new TreeMap<>();
        sum.put(0,new Real(6));
        sum.put(1,new Real(2));
        z.sum(x,y);
        Assert.assertTrue(z.equals(new Polynomial(sum)));

        Map<Integer, Field> dif = new TreeMap<>();
        dif.put(2,new Real(4));
        z.dif(x,y);
        Assert.assertTrue(z.equals(new Polynomial(dif)));

        Map<Integer, Field> mul = new TreeMap<>();
        mul.put(0,new Real(9));
        mul.put(1,new Real(6));
        mul.put(2,new Real(1));
        mul.put(4,new Real(-4));
        z.mul(x,y);
        Assert.assertTrue(z.equals(new Polynomial(mul)));
    }
}
