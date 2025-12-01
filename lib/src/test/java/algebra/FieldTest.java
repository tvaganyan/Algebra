package algebra;
import algebra.fields.*;
import org.apache.commons.math3.complex.Complex;
import org.junit.Assert;
import org.junit.Test;

public class FieldTest {
    FieldFabric fc;

    @Test public void realOp(){
        fc = new FieldFabric(0, FieldEnum.REAL);
        
        Field x = fc.Real(3);
        Field y = fc.Real(5);
        Field r = fc.Real(0);
        
        r.sum(x,y);
        Assert.assertTrue(r.equals(fc.Real(8)));
        r.dif(x,y);
        Assert.assertTrue(r.equals(fc.Real(-2)));
        r.mul(x,y);
        Assert.assertTrue(r.equals(fc.Real(15)));
    }

    @Test public void complexOp(){
        fc = new FieldFabric(0, FieldEnum.COMPLEX);
        
        Field x = fc.Complex(1,3);
        Field y = fc.Complex(2,5);
        Field r = fc.Complex(0,0);
        
        r.sum(x,y);
        Assert.assertTrue(r.equals(fc.Complex(3,8)));
        r.dif(x,y);
        Assert.assertTrue(r.equals(fc.Complex(-1,-2)));
        r.mul(x,y);
        Assert.assertTrue(r.equals(fc.Complex(-13,11)));
    }

    @Test public void ZpOp(){
        fc = new FieldFabric(7, FieldEnum.COMPLEX);

        Field x = fc.Zp(3);
        Field y = fc.Zp(5);
        Field r = fc.Zp(0);

        r.sum(x,y);
        Assert.assertTrue(r.equals(fc.Zp(1)));
        r.dif(x,y);
        Assert.assertTrue(r.equals(fc.Zp(5)));
        r.mul(x,y);
        Assert.assertTrue(r.equals(fc.Zp(1)));
    }
}
