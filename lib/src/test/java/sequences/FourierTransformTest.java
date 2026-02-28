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
        FourierTransform ft = new FourierTransform(list, 10);
        for(int i = 1; i < ft.getK(); i++) {
            if(i % 2 == 1) {
                System.out.println(ft.getS()[i] * i * Math.PI / 2);
                Assert.assertTrue(Math.abs(ft.getS()[i] * i * Math.PI / 2 - 1) < 1e-3);
            }
            else {
                System.out.println(Math.abs(ft.getS()[i]));
                Assert.assertTrue(Math.abs(ft.getS()[i]) < 1e-3);
            }

            System.out.println(Math.abs(ft.getC()[i]));
            Assert.assertTrue(Math.abs(ft.getC()[i]) < 1e-3);
        }
    }
}
