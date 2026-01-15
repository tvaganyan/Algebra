package sequences;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OptimalParametersTest {

    @Test
    public void optParams() {
        Random r = new Random();
        int size = 50;
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(r.nextGaussian() + 0.05 * i);
        }
        OptimalParameters op = new OptimalParameters(list);
        System.out.println("pr: " + op.getPr());
        System.out.println("stepUp: " + op.getStepUp());
        System.out.println("stepDown: " + op.getStepDown());
        System.out.println("mt: " + op.getMt());
        System.out.println("UpPoints: " + op.getUpPoints());
        System.out.println("LowPoints: " + op.getLowPoints());
        System.out.println("UpVal: " + op.getUpVal());
        System.out.println("LowVal: " + op.getLowVal());
        System.out.println("prUp: " + op.getPrUp());
        System.out.println("prDown: " + op.getPrDown());
    }
}
