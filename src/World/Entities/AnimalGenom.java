package World.Entities;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class AnimalGenom {
    static private final int N_GENES = 32;
    static private final int N_DIF_GENES = 8;
    private final int[] gens = new int[N_GENES];

    public AnimalGenom() {
        randomize();
    }

    public AnimalGenom(AnimalGenom par1, AnimalGenom par2) {
        crossOver(par1, par2);
    }

    private void crossOver(AnimalGenom par1, AnimalGenom par2) {
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
    }
}
