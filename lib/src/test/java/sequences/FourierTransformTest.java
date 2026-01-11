package sequences;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FourierTransformTest {
    @Test
    public void transform() {
        List<Double> list = new ArrayList<>();
        for(int i = 0; i < 1000; i++){
            if(i < 500)
                list.add(1.0);
            else
                list.add(0.0);
        }
        FourierTransform ft = new FourierTransform(list);
        for(int i = 1; i < ft.getK(); i++) {
            if(i % 2 == 1)
                Assert.assertTrue(Math.abs(ft.getS()[i] * i / 0.63662 - 1) < 0.01);
            else
                Assert.assertTrue(Math.abs(ft.getS()[i]) < 0.01);

            Assert.assertTrue(Math.abs(ft.getC()[i]) < 0.01);
        }

        Assert.assertTrue(Math.abs(ft.f(250) - 1) < 0.1);
        Assert.assertTrue(Math.abs(ft.f(750)) < 0.1);
    }
}
