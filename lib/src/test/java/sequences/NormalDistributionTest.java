package sequences;

import algebra.fields.Field;
import algebra.fields.FieldEnum;
import algebra.fields.FieldFabric;
import algebra.linear.JordanAlgebra;
import algebra.linear.Matrix;
import algebra.linear.Vector;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NormalDistributionTest {

    @Test
    public void realDistribution() {
        FieldFabric fc = new FieldFabric(0, FieldEnum.REAL);
        int size = 100000;
        Random r = new Random();
        List<Vector> list = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            Field f1 = fc.Real(r.nextGaussian() - 1 + 3 * Math.sin(2 * Math.PI * i / size));
            Field f2 = fc.Real(r.nextGaussian() * 2 + 1);
            f1.sum(f1, f2);
            Field[] f = {f1, f2};
            Vector v = new Vector(f, fc);
            list.add(v);
        }

        NormalDistribution dist = new NormalDistribution(list);
        Field fr = fc.get0();
        fr.dif(fc.getN(0), dist.getAverage().getV()[0]);
        System.out.println(dist.getAverage());
        System.out.println(dist.getDispersion());
        System.out.println(dist.getNoise());
        System.out.println(dist.resistance());
    }
}
