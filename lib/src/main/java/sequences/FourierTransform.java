package sequences;

import algebra.fields.Field;

import java.util.List;

public class FourierTransform {
    private List<Double> sequence;
    private int size;
    private int k = 20;
    private double[] c = new double[k];
    private double[] s = new double[k];

    public FourierTransform(List<Double> sequence){
        this.sequence = sequence;
        size = sequence.size();
        for(int i = 0; i < k; i++){
            c[i] = 2 * cos(i);
            s[i] = 2 * sin(i);
        }
    }

    private double sin(int n){
        if(n == 0)
            return 0;
        double res = 0;
        for(int i = 0; i < size; i++){
            res += Math.sin(2 * n * Math.PI * i / size) * sequence.get(i) / size;
        }
        return res;
    }

    private double cos(int n){
        double res = 0;
        for(int i = 0; i < size; i++){
            res += Math.cos(2 * n * Math.PI * i / size) * sequence.get(i) / size;
        }
        if(n == 0)
            return res / 2;
        return res;
    }

    public double f(int x){
       double res = 0;
       for(int i = 0; i < k; i++){
           res += c[i] * Math.cos(2 * i * Math.PI * x / size) + s[i] * Math.sin(2 * i * Math.PI * x / size);
       }
       return res;
    }

    public double df(int x){
        double res = 0;
        for(int i = 0; i < k; i++){
            res += 2 * i * Math.PI / size *
                    (s[i] * Math.cos(2 * i * Math.PI * x / size) - c[i] * Math.sin(2 * i * Math.PI * x / size));
        }
        return res;
    }

    public List<Double> getSequence() {
        return sequence;
    }

    public int getSize() {
        return size;
    }

    public int getK() {
        return k;
    }

    public double[] getC() {
        return c;
    }

    public double[] getS() {
        return s;
    }
}
