package sequences;

import java.util.List;

public class OptimalParameters {
    private List<Double> sequence;
    private int size;

    private double stepUp = 0, stepDown = 0;
    private double pr, prUp, prDown;
    private double mt;
    private int upPoints = 1, lowPoints = 1;

    private double upVal, lowVal;

    public OptimalParameters(List<Double> sequence){
        this.sequence = sequence;
        size = sequence.size();
        double upCount = 1, downCount = 1;
        upVal = sequence.get(0);
        lowVal = sequence.get(0);
        for(int i = 1; i < size; i++){
            double d = sequence.get(i) - sequence.get(i-1);
            if(d > 0) {
                stepUp += d;
                upCount += 1;
            }
            else {
                stepDown += d;
                downCount += 1;
            }
            if(upVal < sequence.get(i)){
                upVal = sequence.get(i);
                upPoints ++;
            }
            if(lowVal > sequence.get(i)){
                lowVal = sequence.get(i);
                lowPoints ++;
            }
        }
        stepUp /= upCount;
        stepDown /= downCount;
        pr = upCount / (upCount + downCount);
        martingale();
        double g = 0.5772;
        double s = size;
        double mtUp = Math.pow(mt, stepUp);
        double mtDown = Math.pow(mt, stepDown);
        prUp = goal(size, upPoints, Math.log(s) - g, s / 2 - Math.log(s) + g) * (1 - mtDown) / (mtUp - mtDown);
        prDown = goal(size, lowPoints, Math.log(s) - g, s / 2 - Math.log(s) + g) * (mtUp - 1) / (mtUp - mtDown);
    }

    private double DM(double x){
        double up = Math.pow(x,stepUp);
        double down = Math.pow(x, stepDown);
        double p = pr / (1 - pr);
        return x * (up - 1) * (down + p * (up - 1) - 1) / (stepDown * down * (up - 1) - stepUp * up * (down - 1));
    }

    private double F(double x){
        double up = Math.pow(x,stepUp);
        double down = Math.pow(x, stepDown);
        double p = pr / (1 - pr);
        return (1 - down) / (up - 1) - p;
    }

    private void martingale(){
        double x = 1e-9;
        double dx = 1e-2;
        double f = F(x);
        while(f * F(x + dx) > 0 && x < 100){
            x += dx;
            f = F(x);
        }
        int i = 1;
        while(Math.abs(dx) > 1e-3 && i < 100){
            dx = DM(x);
            x = x - dx;
            i++;
        }
        mt = x;
    }

    public double goal(int n, int k, double m1, double m2){
        int t = (int) (k + 3 * Math.round(Math.log(n)));
        double res = PoissonDistribution.probability(k - 1, m1) *
                PoissonDistribution.probability(t - k, m2) * (n - t + 1);
        return res;
    }

    public List<Double> getSequence() {
        return sequence;
    }

    public int getSize() {
        return size;
    }

    public double getStepUp() {
        return stepUp;
    }

    public double getStepDown() {
        return stepDown;
    }

    public double getPr() {
        return pr;
    }

    public double getPrUp() {
        return prUp;
    }

    public double getPrDown() {
        return prDown;
    }

    public double getMt() {
        return mt;
    }

    public int getUpPoints() {
        return upPoints;
    }

    public int getLowPoints() {
        return lowPoints;
    }

    public double getUpVal() {
        return upVal;
    }

    public double getLowVal() {
        return lowVal;
    }
}
