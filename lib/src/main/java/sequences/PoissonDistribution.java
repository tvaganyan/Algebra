package sequences;

import java.util.List;

public class PoissonDistribution {
    private List<Integer> sequence;
    private double average;

    public PoissonDistribution(List<Integer> sequence){
        this.sequence = sequence;
        average = 0;
        for(int s: sequence) {
            average += s;
        }
        average = average / sequence.size();
    }

    public double probability(int n){
        double nf = 1;
        for(int j = 1; j <= n; j++) {
            nf *= j;
        }
        return Math.pow(average, n) * Math.exp(-average) / nf;
    }

    public static double probability(int n, double mean){
        double nf = 1;
        for(int j = 1; j <= n; j++) {
            nf *= j;
        }
        return Math.pow(mean, n) * Math.exp(-mean) / nf;
    }

    public List<Integer> getSequence() {
        return sequence;
    }

    public double getAverage() {
        return average;
    }
}
