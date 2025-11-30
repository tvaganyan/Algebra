package algebra;
import algebra.fields.ComlexField;
import algebra.fields.Field;
import algebra.fields.Real;
import algebra.fields.Zp;
import org.apache.commons.math3.complex.Complex;
import org.junit.Assert;
import org.junit.Test;

public class FieldTest {

    @Test public void realOp(){
        Field x = new Real(3);
        Field y = new Real(5);
        Field r = new Real(0);
        r.sum(x,y);
        Assert.assertTrue(r.equals(new Real(8)));
        r.dif(x,y);
        Assert.assertTrue(r.equals(new Real(-2)));
        r.mul(x,y);
        Assert.assertTrue(r.equals(new Real(15)));
    }

    @Test public void complexOp(){
        Field x = new ComlexField(1,3);
        Field y = new ComlexField(2,5);
        Field r = new ComlexField(0,0);
        r.sum(x,y);
        Assert.assertTrue(r.equals(new ComlexField(3,8)));
        r.dif(x,y);
        Assert.assertTrue(r.equals(new ComlexField(-1,-2)));
        r.mul(x,y);
        Assert.assertTrue(r.equals(new ComlexField(-13,11)));
    }

    @Test public void ZpOp(){
        int p = 7;
        Field x = new Zp(p,3);
        Field y = new Zp(p,5);
        Field r = new Zp(p,0);
        r.sum(x,y);
        Assert.assertTrue(r.equals(new Zp(p,1)));
        r.dif(x,y);
        Assert.assertTrue(r.equals(new Zp(p,5)));
        r.mul(x,y);
        Assert.assertTrue(r.equals(new Zp(p,1)));
    }
}
