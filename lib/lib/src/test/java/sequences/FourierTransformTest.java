package sequences;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class FourierTransformTest {
    @Test
    public void transform() {
        int size = 10000;
        List<Double> list = new ArrayList<>();
        for(int i = 0; i < size; i++){
            if(i < size / 2)
                list.add(1.0);
            else
                list.add(0.0);
        }
        FourierTransform ft = new FourierTransform(list);
        for(int i = 1; i < ft.getK(); i++) {
            if(i % 2 == 1)
                Assert.assertTrue(Math.abs(ft.getS()[i] * i / 0.63662 - 1) < 1e-3);
            else
                Assert.assertTrue(Math.abs(ft.getS()[i]) < 1e-3);

           Assert.assertTrue(Math.abs(ft.getC()[i]) < 1e-3);
        }

        Assert.assertTrue(Math.abs(ft.f(size / 4) - 1) < 2e-2);
        Assert.assertTrue(Math.abs(ft.f((size * 3) / 4)) < 2e-2);
    }
}
