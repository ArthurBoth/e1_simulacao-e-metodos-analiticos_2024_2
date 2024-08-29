package auxiliaries;

import static auxiliaries.Configs.SEED;
import static auxiliaries.Configs.MULTIPLIER;
import static auxiliaries.Configs.INCREMENT;
import static auxiliaries.Configs.MODULE;

public class RNG {

    /*
     * M > 0
     * 0 < a < M
     * 0 <= c < M
     * 0 <= X < M
     */

    private double previous;

    private int stopCount;
    private int iteration;

    public RNG() {
        previous  = 0;
        stopCount = 0;
        iteration = 0;
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
        return (iteration < stopCount);
    }
}
