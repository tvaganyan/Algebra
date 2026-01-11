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
    FieldFabric fc;

    @Test
    public void realDistribution() {
        fc = new FieldFabric(0, FieldEnum.REAL);
        Random r = new Random();
        List<Vector> list = new ArrayList<>();
        for(int i = 0; i < 1e5; i++) {
            Field f1 = fc.Real(r.nextGaussian() - 1 + 3 * Math.sin(Math.PI * i * 1e-2));
            Field f2 = fc.Real(r.nextGaussian() * 2 + 1);
            f1.sum(f1, f2);
            Field[] f = {f1, f2};
            Vector v = new Vector(f, fc);
            list.add(v);
        }

        NormalDistribution dist = new NormalDistribution(list);
        Field fr = fc.get0();
        fr.dif(fc.getN(0), dist.getAverage().getV()[0]);
        Assert.assertTrue(fr.norm() < 0.1);
        fr.dif(fc.getN(1), dist.getAverage().getV()[1]);
        Assert.assertTrue(fr.norm() < 0.1);
        fr.dif(fc.getN(2*(4+1)), dist.getNoise().getM()[0][0]);
        Assert.assertTrue(fr.norm() < 0.1);
        fr.dif(fc.getN(2*4), dist.getNoise().getM()[0][1]);
        Assert.assertTrue(fr.norm() < 0.1);
        fr.dif(fc.getN(2*4), dist.getNoise().getM()[1][1]);
        Assert.assertTrue(fr.norm() < 0.1);
        JordanAlgebra ds = new JordanAlgebra(dist.getDispersion());
        JordanAlgebra rs = new JordanAlgebra(dist.resistance());
        JordanAlgebra bs = new JordanAlgebra(new Matrix(dist.getDim(), fc));
        bs.mul(ds, rs);
        Assert.assertTrue(bs.getM().eq(dist.getNoise()));
    }
}
