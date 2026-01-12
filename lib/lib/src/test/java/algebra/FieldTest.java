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
        Assert.assertTrue(r.eq(fc.Real(8)));
        r.dif(x,y);
        Assert.assertTrue(r.eq(fc.Real(-2)));
        r.mul(x,y);
        Assert.assertTrue(r.eq(fc.Real(15)));
    }

    @Test public void complexOp(){
        fc = new FieldFabric(0, FieldEnum.COMPLEX);
        FieldFabric fcr = new FieldFabric(0, FieldEnum.REAL);
        
        Field x = fc.Complex(1,3);
        Field y = fc.Complex(2,5);
        Field z = fcr.Real(3);
        Field r = fc.get0();

        r.sum(x,y);
        Assert.assertTrue(r.eq(fc.Complex(3,8)));
        r.sum(x,z);
        Assert.assertTrue(r.eq(fc.Complex(4,3)));
        r.dif(x,y);
        Assert.assertTrue(r.eq(fc.Complex(-1,-2)));
        r.dif(x,z);
        Assert.assertTrue(r.eq(fc.Complex(-2,3)));
        r.mul(x,y);
        Assert.assertTrue(r.eq(fc.Complex(-13,11)));
        r.mul(x,z);
        Assert.assertTrue(r.eq(fc.Complex(3,9)));
        ((ComplexField)r).conjugate(x);
        Assert.assertTrue(r.eq(fc.Complex(1,-3)));
    }

    @Test public void ZpOp(){
        fc = new FieldFabric(7, FieldEnum.COMPLEX);

        Field x = fc.Zp(3);
        Field y = fc.Zp(5);
        Field r = fc.Zp(0);

        r.sum(x,y);
        Assert.assertTrue(r.eq(fc.Zp(1)));
        r.dif(x,y);
        Assert.assertTrue(r.eq(fc.Zp(5)));
        r.mul(x,y);
        Assert.assertTrue(r.eq(fc.Zp(1)));
    }
}
