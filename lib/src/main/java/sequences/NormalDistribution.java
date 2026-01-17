package sequences;

import algebra.fields.Field;
import algebra.fields.FieldFabric;
import algebra.linear.JordanAlgebra;
import algebra.linear.Matrix;
import algebra.linear.Vector;

import java.util.List;

public class NormalDistribution {
    private int dim;
    private int size;
    private List<Vector> sequence;
    private Vector average;
    private Matrix dispersion;
    private Matrix dispersionInv;
    private Matrix noise;
    private FieldFabric fc;

    public NormalDistribution(List<Vector> sequence){
        dim = sequence.get(0).getDim();
        fc = sequence.get(0).getFc();
        size = sequence.size();
        this.sequence = sequence;
        average = new Vector(dim, fc);
        average.o();
        noise = new Matrix(dim, fc);
        noise.o();
        for(int k = 1; k < size; k++) {
            average.sum(sequence.get(k), average);
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    Field x = fc.get0();
                    Field y = fc.get0();
                    Field z = fc.get0();
                    x.dif(sequence.get(k).getV()[i], sequence.get(k-1).getV()[i]);
                    y.dif(sequence.get(k).getV()[j], sequence.get(k-1).getV()[j]);
                    z.mul(x, fc.conjugate(y));
                    noise.getM()[i][j].sum(z, noise.getM()[i][j]);
                }
            }
        }
        Field s = fc.getN(size - 1);
        s.div(fc.get1(), s);   //    s = 1 / (size - 1)
        average.scalarMul(s, average);
        noise.scalarMul(s, noise);
        dispersion = new Matrix(dim, fc);
        dispersion.o();
        for(int k = 1; k < size; k++) {
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    Field x = fc.get0();
                    Field y = fc.get0();
                    Field z = fc.get0();
                    x.dif(sequence.get(k).getV()[i], average.getV()[i]);
                    y.dif(sequence.get(k).getV()[j], average.getV()[j]);
                    z.mul(x, fc.conjugate(y));
                    dispersion.getM()[i][j].sum(z, dispersion.getM()[i][j]);
                }
            }
        }
        dispersion.scalarMul(s, dispersion);
        dispersionInv = new Matrix(dim, fc);
        dispersionInv.inverse(dispersion);
    }

    public double probability(Vector x){
        Vector y = new Vector(dim, fc);
        Vector z = new Vector(dim, fc);
        y.dif(x, average);
        z.matrixMul(dispersionInv, y);
        Field r = (new Vector(dim, fc)).scalarMul(y, z);
        return Math.exp(- r.norm() / 2) * Math.sqrt(dispersion.det().norm()) / Math.pow(2 * Math.PI, dim * 0.5);
    }

    public Matrix resistance(){
        JordanAlgebra a = new JordanAlgebra(dispersion);
        JordanAlgebra b = new JordanAlgebra(noise);
        JordanAlgebra r = new JordanAlgebra(new Matrix(dim, fc));
        r.linearEquationSolution(a, b);
        return r.getM();
    }

    public int getDim() {
        return dim;
    }

    public Vector getAverage() {
        return average;
    }

    public Matrix getDispersion() {
        return dispersion;
    }

    public Matrix getNoise() {
        return noise;
    }

    public FieldFabric getFc() {
        return fc;
    }

    public Matrix getDispersionInv() {
        return dispersionInv;
    }

    public List<Vector> getSequence() {
        return sequence;
    }
}
