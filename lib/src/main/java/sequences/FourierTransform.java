package sequences;

import java.util.ArrayList;
import java.util.List;

public class FourierTransform {
    private List<Double> sequence;
    private int size;
    private int terms;
    private double[] c;
    private double[] s;

    public FourierTransform(List<Double> sequence, int terms){
        this.terms = terms;
        this.sequence = sequence;
        size = sequence.size();
        c = new double[terms];
        s = new double[terms];
        for(int i = 0; i < terms; i++){
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
       for(int i = 0; i < terms; i++){
           res += c[i] * Math.cos(2 * i * Math.PI * x / size) + s[i] * Math.sin(2 * i * Math.PI * x / size);
       }
       return res;
    }

    public double f(int x, int terms){
        double res = 0;
        for(int i = 0; i < terms; i++){
            res += c[i] * Math.cos(2 * i * Math.PI * x / size) + s[i] * Math.sin(2 * i * Math.PI * x / size);
        }
        return res;
    }

    public double df(int x){
        double res = 0;
        for(int i = 1; i < terms; i++){
            res += 2 * i * Math.PI / size *
                    (s[i] * Math.cos(2 * i * Math.PI * x / size) - c[i] * Math.sin(2 * i * Math.PI * x / size));
        }
        return res;
    }

    public double df(int x, int terms){
        double res = 0;
        for(int i = 1; i < terms; i++){
            res += 2 * i * Math.PI / size *
                    (s[i] * Math.cos(2 * i * Math.PI * x / size) - c[i] * Math.sin(2 * i * Math.PI * x / size));
        }
        return res;
    }

    public int getSize() {
        return size;
    }

    public int getK() {
        return terms;
    }

    public double[] getC() {
        return c;
    }

    public double[] getS() {
        return s;
    }

    public List<Double> getSequence() {
        return sequence;
    }

    public List<Double> getSequenceF() {
        List<Double> sequenceF = new ArrayList<>();
        for(int j = 0; j < size; j++){
            sequenceF.add(f(j, terms));
        }
        return sequenceF;
    }

    public List<Double> getSequenceF(int terms) {
        List<Double> sequenceF = new ArrayList<>();
        for(int j = 0; j < size; j++){
            sequenceF.add(f(j, terms));
        }
        return sequenceF;
    }

    public List<Double> getSequenceDF() {
        List<Double> sequenceDF = new ArrayList<>();
        for(int j = 0; j < size; j++){
            sequenceDF.add(df(j, terms));
        }
        return sequenceDF;
    }
    public List<Double> getSequenceDF(int terms) {
        List<Double> sequenceDF = new ArrayList<>();
        for(int j = 0; j < size; j++){
            sequenceDF.add(df(j, terms));
        }
        return sequenceDF;
    }
}
