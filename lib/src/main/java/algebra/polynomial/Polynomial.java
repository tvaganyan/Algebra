package algebra.polynomial;

import algebra.fields.*;
import org.apache.commons.math3.complex.Complex;

import java.util.*;

public class Polynomial {
    public Map<Integer, Field> map;
    FieldFabric fc;

    public Polynomial(Map<Integer,Field> map, FieldFabric fc){
        this.fc = fc;
        this.map = new TreeMap<>();
        for(int i :map.keySet()) {
            if(map.get(i).getType() != fc.getType()){
                this.map = null;
                return;
            }
            this.map.put(i, map.get(i).copy());
        }
    }

    public Polynomial(FieldFabric fc){
        this.fc = fc;
        this.map = new TreeMap<>();
        o();
    }

    public int deg(){
        int res = 0;
        for(int i:map.keySet()){
            if(res < i){
                res = i;
            }
        }
        return res;
    }

    public void o(){
       map.clear();
       map.put(0, fc.get0());
    }

    public void e(){
        map.clear();
        map.put(0, fc.get1());
    }

    public void sum(Polynomial x, Polynomial y){
        map.clear();
        Field g = fc.get0();
        Set<Integer> keys = new HashSet<>(x.map.keySet());
        keys.addAll(y.map.keySet());
        for(int i :keys) {
            if (x.map.containsKey(i) && y.map.containsKey(i)){
                g.sum(x.map.get(i),y.map.get(i));
                map.put(i, g.copy());
            }
            else if (x.map.containsKey(i)){
                map.put(i, x.map.get(i).copy());
            }
            else if (y.map.containsKey(i)){
                map.put(i, y.map.get(i).copy());
            }
        }
        removeO();
    }

    public void scalarMul(Field s, Polynomial x){
        map.clear();
        for(int i :x.map.keySet()){
            Field g = fc.get0();
            g.mul(s, x.map.get(i));
            map.put(i,g);
        }
    }

    public void dif(Polynomial x, Polynomial y){
        Field s = fc.getMinus1();
        Polynomial z = new Polynomial(y.map, fc);
        z.scalarMul(s, y);
        sum(x, z);
    }

    public void mul(Polynomial x, Polynomial y){
        map.clear();
        int d = x.deg() + y.deg();
        for(int k = 0; k <= d; k++) {
            Field s = fc.get0();
            for (int i : x.map.keySet()) {
                if(y.map.keySet().contains(k-i)){
                    Field m = fc.get0();
                    m.mul(x.map.get(i),y.map.get(k-i));
                    s.sum(s,m);
                }
            }
            map.put(k,s);
        }
        removeO();
    }

    public Field eval(Field x){
        Field res = fc.get0();
        for(int i :map.keySet()){
            Field f = fc.get1();
            f.deg(x.copy(),i);
            f.mul(map.get(i),f);
            res.sum(res,f);
        }
        return res;
    }

    private void removeO(){
        Iterator<Integer> it = map.keySet().iterator();
        while (it.hasNext()) {
            Integer k = it.next();
            if(map.get(k).isO())
                it.remove();
        }
        if(map.keySet().isEmpty())
            map.put(0, fc.get0());
    }

    private void addO(){
        int d = deg();
        for(int i = 0; i <= d; i++){
            if(map.get(i) == null)
                map.put(i, fc.get0());
        }
    }

    public boolean eq(Polynomial x){
        if(!map.keySet().equals(x.map.keySet()))
            return false;
        for (int i : map.keySet()) {
            if(!map.get(i).eq(x.map.get(i)))
                return false;
        }
        return true;
    }

    //  the degree of the polynomial must be equal to 1, 2 or 3
    public List<Field> roots(){
        int d = deg();
        if(d < 1 || d > 3)
            return null;
        List<Field> res = new ArrayList<>();

        if(fc.getType() == FieldEnum.ZP){
            int p = fc.getP();
            for(int i = 0; i < p; i++){
                if(eval(fc.Zp(i)).isO())
                    res.add(fc.Zp(i));
            }
            return res;
        }

        // if all roots are REAL, returns the results type REAL else returns COMPLEX

        if(d == 1){
            Field z = fc.get0();
            z.div(map.get(0), map.get(1));
            z.mul(fc.getMinus1(), z);
            res.add(z);
            return res;
        }
        if(d == 2){
            Complex z = null, c = null;
            if(fc.getType() == FieldEnum.REAL){
                addO();
                double a2 = (Double) map.get(2).getEl();
                double a = (Double)map.get(1).getEl()/a2/2;
                double b = (Double)map.get(0).getEl()/a2;
                removeO();
                z = new Complex((a * a - b - 2) / 2);
                c = new Complex(a);
            }
            if(fc.getType() == FieldEnum.COMPLEX){
                addO();
                Complex a2 = (Complex)map.get(2).getEl();
                Complex a = ((Complex)map.get(1).getEl()).divide(a2).divide(2);
                Complex b = ((Complex)map.get(0).getEl()).divide(a2);
                removeO();
                z = a.multiply(a).subtract(b).subtract(2).divide(2);
                c = a;
            }
            Complex r = z.multiply(z).subtract(1).sqrt().add(z).log().divide(2);
            Complex z1 = r.cosh().multiply(2).subtract(c);
            Complex z2 = r.cosh().multiply(-2).subtract(c);
            if(Math.abs(z1.getImaginary()) < 1e-9 && Math.abs(z2.getImaginary()) < 1e-9){
                res.add(fc.Real(z1.getReal()));
                res.add(fc.Real(z2.getReal()));
                return res;
            }
            res.add(fc.Complex(z1));
            res.add(fc.Complex(z2));
            return res;
        }

        if(d == 3){
            Complex z = Complex.ZERO, a = Complex.ZERO, b = Complex.ZERO;
            if(fc.getType() == FieldEnum.REAL){
                addO();
                double a3 = (Double)map.get(3).getEl();
                double a2 = (Double)map.get(2).getEl();
                double a1 = (Double)map.get(1).getEl();
                double a0 = (Double)map.get(0).getEl();
                removeO();
                double p = (a2*a2/(3*a3*a3)-a1/a3)/3;
                double q = -a0/a3+a2*a1/(3*a3*a3)-2*a2*a2*a2/(27*a3*a3*a3);
                if(Math.abs(p) < 1.e-9){
                    Complex s = new Complex(q);
                    s = s.log().divide(3);
                    Complex pi3 = new Complex(0, 2 * Math.PI / 3);
                    res.add(fc.Complex(s.exp()));
                    res.add(fc.Complex(s.add(pi3).exp()));
                    res.add(fc.Complex(s.add(pi3.multiply(2)).exp()));
                    return res;
                }
                a = (new Complex(p)).sqrt();
                z = new Complex(q/2).divide(a.pow(3));
                b = new Complex(a2 / a3 / 3);
            }
            if(fc.getType() == FieldEnum.COMPLEX){
                addO();
                Complex a3 = (Complex)map.get(3).getEl();
                Complex a2 = (Complex)map.get(2).getEl();
                Complex a1 = (Complex)map.get(1).getEl();
                Complex a0 = (Complex)map.get(0).getEl();
                removeO();
                Complex p = a2.multiply(a2).divide(a3.multiply(a3).multiply(9))
                        .subtract(a1.divide(a3).divide(3));
                Complex q = a1.multiply(a2).divide(a3.multiply(a3).multiply(3))
                        .subtract(a0.divide(a3))
                        .subtract(a2.multiply(a2).multiply(a2).multiply(2)
                                .divide(a3.multiply(a3).multiply(a3).multiply(27)));
                if(p.abs() < 1e-9){
                    Complex s = q;
                    s = s.log().divide(3);
                    Complex pi3 = new Complex(0, 2 * Math.PI / 3);
                    res.add(fc.Complex(s.exp()));
                    res.add(fc.Complex(s.add(pi3).exp()));
                    res.add(fc.Complex(s.add(pi3.multiply(2)).exp()));
                    return res;
                }
                a = p.sqrt();
                z = q.divide(a.pow(3).multiply(2));
                b = a2.divide(a3).divide(3);
            }
            Complex r = z.multiply(z).subtract(1).sqrt().add(z).log().divide(3);
            Complex fi = new Complex(0,2*Math.PI/3);
            Complex z1 = r.cosh().multiply(a).multiply(2).subtract(b);
            Complex z2 = r.add(fi).cosh().multiply(a).multiply(2).subtract(b);
            Complex z3 = r.add(fi.multiply(2)).cosh().multiply(a).multiply(2).subtract(b);
            if(Math.abs(z1.getImaginary()) < 1e-9 && Math.abs(z2.getImaginary()) < 1e-9 && Math.abs(z3.getImaginary()) < 1e-9){
                res.add(fc.Real(z1.getReal()));
                res.add(fc.Real(z2.getReal()));
                res.add(fc.Real(z3.getReal()));
                return res;
            }
            res.add(fc.Complex(z1));
            res.add(fc.Complex(z2));
            res.add(fc.Complex(z3));
            return res;
        }
        return res;
    }

    public Polynomial getNewO(){
        Map<Integer, Field> m = new TreeMap<>();
        m.put(0, fc.get0());
        return new Polynomial(m, fc);
    }

    public Polynomial copy(){
        return new Polynomial(map, fc);
    }

    public FieldEnum getType(){
        return fc.getType();
    }

    public String toString(){
        return map.toString();
    }
}
