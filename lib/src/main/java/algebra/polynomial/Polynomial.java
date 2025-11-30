package algebra.polynomial;

import algebra.fields.Field;
import algebra.linear.Matrix;
import algebra.linear.Vector;

import java.util.*;

public class Polynomial {
    public Map<Integer, Field> map;
    private Field f;

    public Polynomial(Map<Integer,Field> map){
        this.map = new TreeMap<>();
        for(int i :map.keySet()) {
            this.map.put(i, map.get(i).copy());
            if(f == null)
                f = map.get(i).getNewO();
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
       map.put(0, f.getNewO());
    }

    public void e(){
        map.clear();
        map.put(0, f.getNewE());
    }

    public void sum(Polynomial x, Polynomial y){
        map.clear();
        Field g = f.getNewO();
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
            Field g = f.getNewO();
            g.mul(s, x.map.get(i));
            map.put(i,g);
        }
    }

    public void dif(Polynomial x, Polynomial y){
        Field s = f.getNewO();
        s.dif(f.getNewO(), f.getNewE()); // -1
        Polynomial z = new Polynomial(y.map);
        z.scalarMul(s, y);
        sum(x, z);
    }

    public void mul(Polynomial x, Polynomial y){
        map.clear();
        int d = x.deg() + y.deg();
        for(int k = 0; k <= d; k++) {
            Field s = f.getNewO();
            for (int i : x.map.keySet()) {
                if(y.map.keySet().contains(k-i)){
                    Field m = f.getNewO();
                    m.mul(x.map.get(i),y.map.get(k-i));
                    s.sum(s,m);
                }
            }
            map.put(k,s);
        }
        removeO();
    }

    private void removeO(){
        Iterator<Integer> it = map.keySet().iterator();
        while (it.hasNext()) {
            Integer k = it.next();
            if(map.get(k).isO())
                it.remove();
        }
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

    public String toString(){
        return map.toString();
    }
}
