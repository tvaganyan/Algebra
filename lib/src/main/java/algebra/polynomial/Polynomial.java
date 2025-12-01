package algebra.polynomial;

import algebra.fields.Field;
import algebra.fields.FieldFabric;
import algebra.linear.Matrix;
import algebra.linear.Vector;

import java.util.*;

public class Polynomial {
    public Map<Integer, Field> map;
    FieldFabric fc;

    public Polynomial(Map<Integer,Field> map, FieldFabric fc){
        this.fc = fc;
        this.map = new TreeMap<>();
        for(int i :map.keySet()) {
            this.map.put(i, map.get(i).copy());
        }
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

    public void removeO(){
        Iterator<Integer> it = map.keySet().iterator();
        while (it.hasNext()) {
            Integer k = it.next();
            if(map.get(k).isO())
                it.remove();
        }
        if(map.keySet().isEmpty())
            map.put(0, fc.get0());
    }

    public boolean equals(Polynomial x){
        if(!map.keySet().equals(x.map.keySet()))
            return false;
        for (int i : map.keySet()) {
            if(!map.get(i).equals(x.map.get(i)))
                return false;
        }
        return true;
    }

    public Polynomial getNewO(){
        Map<Integer, Field> m = new TreeMap<>();
        m.put(0, fc.get0());
        return new Polynomial(m, fc);
    }

    public Polynomial copy(){
        return new Polynomial(map, fc);
    }

    public String toString(){
        return map.toString();
    }
}
