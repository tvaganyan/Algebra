package sequences;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FilterTest {
    @Test
    public void filter() {
        Random r = new Random();
        int size = 1000;
        List<Double> list = new ArrayList<>();
        for(int i = 0; i < size; i++){
            list.add(r.nextDouble() - 2 * Math.sin(2 * Math.PI * i / size));
        }

        Filter fl = new Filter(list);

        Assert.assertTrue(fl.getStatus() == 2);
        Assert.assertTrue(1 - fl.getA() < 0.1);
    }
}
