package World.Entities;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class AnimalGenome {
    static private final int N_GENES = 32;
    static private final int N_DIF_GENES = 8;
    private final int[] gens = new int[N_GENES];

    public AnimalGenome() {
        randomize();
    }

    public AnimalGenome(AnimalGenome par1, AnimalGenome par2) {
        crossOver(par1, par2);
    }

    public int getRandomRotation() {
        int idx = ThreadLocalRandom.current().nextInt(0, N_GENES);
        return gens[idx];
    }

    @Override
    public String toString() {
        return Arrays.stream(gens).mapToObj(Integer::toString).reduce("", (a, b) -> a + b);
    }

    private void crossOver(AnimalGenome par1, AnimalGenome par2) {
        int len = N_GENES;
        int i_first = ThreadLocalRandom.current().nextInt(0, len);
        int i_second = ThreadLocalRandom.current().nextInt(i_first, len);
        if (i_first >= 0) System.arraycopy(par1.gens, 0, gens, 0, i_first);
        if (i_second - i_first >= 0) System.arraycopy(par2.gens, i_first, gens, i_first, i_second - i_first);
        if (len - i_second >= 0) System.arraycopy(par1.gens, i_second, gens, i_second, len - i_second);

        Arrays.sort(gens);
        validate();
    }

    private void randomize() {
        for (int i = 0; i < N_GENES; ++i) {
            gens[i] = ThreadLocalRandom.current().nextInt(0, N_DIF_GENES);
        }
         Arrays.sort(gens);
         validate();
    }

    private void validate() {
        int nextGen = 0;
        for (int i = 0; i < N_GENES; i++) {
            if(gens[i] >= nextGen) {
                if(gens[i] > nextGen) {
                    gens[i] = nextGen;
                }
                nextGen++;
            }
            if(nextGen == N_DIF_GENES) {
                break;
            }
        }
        if(nextGen < N_DIF_GENES) {
            int idx = 0;
            for(; nextGen < N_DIF_GENES; ++nextGen) {
                gens[idx++] = nextGen;
            }
            Arrays.sort(gens);
            validate();
        }
    }

    public boolean is_proper() {
        int nextGen = 0;
        for (int i = 0; i < N_GENES; i++) {
            if(gens[i] >= nextGen) {
                if(gens[i] > nextGen) {
                    return false;
                }
                nextGen++;
            }
        }
        return nextGen == N_DIF_GENES;
    }
}
