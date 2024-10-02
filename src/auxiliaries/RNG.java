package auxiliaries;

public class RNG {

    /*
     * M > 0
     * 0 < a < M
     * 0 <= c < M
     * 0 <= X < M
     */

    public static final double SEED         = 3.1415926535;     
    public static final int MULTIPLIER      = 157907;            
    public static final double INCREMENT    = 14423.2563217511;  
    public static final double MODULE       = 16651.8647227172;  
    
    private double[] seeds;
    private double previous;
    private int stopCount;
    private long iteration;

    public RNG() {
        previous  = 0;
        stopCount = 0;
        iteration = 0;
    }

    public void setSeeds(double[] seeds) {
        this.seeds = seeds;
    }

    public double nextRandom() {
        iteration++;

        if (previous == 0) {
            previous = ((MULTIPLIER * SEED) + INCREMENT) % MODULE;
        } else {
            previous = ((MULTIPLIER * previous) + INCREMENT) % MODULE;
        }

        return (double) previous / MODULE;
    }

    public double nextRandom(double a, double b) {
        // U(a,b) = a + [(b-a)*x]
        return a + ((b - a) * nextRandom());
    }

    public void setStop(int stopCount) {
        this.stopCount = stopCount;
    }

    public boolean stop() {
        return (iteration >= (stopCount * seeds.length));
    }
}
