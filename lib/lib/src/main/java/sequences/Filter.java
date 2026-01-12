package sequences;

import algebra.fields.Field;
import algebra.fields.FieldEnum;
import algebra.fields.FieldFabric;
import algebra.linear.Vector;

import java.util.ArrayList;
import java.util.List;

public class Filter {
    private List<Double> sequence;
    private List<Double> sequenceF;
    private int size;

    private int status;
    private double a;
    private double forecastSeq;

    private FourierTransform ft;

    public Filter(List<Double> sequence){
        this.sequence = sequence;
        size = sequence.size();

        ft = new FourierTransform(sequence);
        this.sequenceF = ft.getSequenceF();
        FieldFabric fc = new FieldFabric(0, FieldEnum.REAL);
        List<Vector> seq = new ArrayList<>();
        List<Vector> fSeq = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            seq.add(new Vector(new Field[]{fc.Real(sequenceF.get(i))}, fc));
            fSeq.add(new Vector(new Field[]{fc.Real(sequence.get(i))}, fc));
        }
        forecast(new NormalDistribution(seq), new NormalDistribution(fSeq));
    }

    private void forecast(NormalDistribution dist, NormalDistribution distF){
        double average = (double) dist.getAverage().getV()[0].getEl();
        double noise = (double) dist.getNoise().getM()[0][0].getEl();

        double averageF = (double) distF.getAverage().getV()[0].getEl();
        double dispersionF = (double) distF.getDispersion().getM()[0][0].getEl();

        double cov = 0;
        for(int i = 0; i < size; i++){
            cov += (sequence.get(i) - average) * (sequenceF.get(i) - averageF) / size;
        }
        double y = dispersionF * noise / (cov * cov);

        System.out.println("y: " + y);

        if(y >= 27.0 / 64){
            a = 0;
            status = 0;
            forecastSeq = sequenceF.get(size -1);
            return;
        }
        if(y >= 1.0 / 4){
            double x = 7.0 / 16;
            double x1 = 0;
            int i = 0;
            while(Math.abs(x - x1) > 1e-2 && i < 1000){
                x1 = Math.sqrt(Math.sqrt(2*x*y) - y);
                i++;
            }
            System.out.println("i: " + i);
            if(Math.abs(x - x1) > 1e-2){
               a = 0;
               status = 0;
               forecastSeq = sequenceF.get(size -1);
               return;
            }
            a = cov * x / dispersionF;
            status = 1;
            forecastSeq = sequenceF.get(size -1) + a * ft.df(size -1) / size;
            return;
        }
        double x = (1 + Math.sqrt(1 - 4 * y)) / 2;
        a = cov * x / dispersionF;
        status = 2;
        forecastSeq = sequenceF.get(size -1) + a * ft.df(size -1) / size;
    }

    public List<Double> getSequence() {
        return sequence;
    }

    public List<Double> getSequenceF() {
        return sequenceF;
    }

    public int getSize() {
        return size;
    }

    public int getStatus() {
        return status;
    }

    public double getA() {
        return a;
    }

    public double getForecastSeq() {
        return forecastSeq;
    }
}
