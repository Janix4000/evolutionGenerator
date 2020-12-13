package World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class AnimalGenom {
    private final int[] gens = new int[32];

    public AnimalGenom() {
        randomize();
    }

    public AnimalGenom(AnimalGenom par1, AnimalGenom par2) {
        crossOver(par1, par2);
    }

    private void crossOver(AnimalGenom par1, AnimalGenom par2) {
        int len = 32;
        int i_first = ThreadLocalRandom.current().nextInt(0, len);
        int i_second = ThreadLocalRandom.current().nextInt(i_first, len);
        if (i_first >= 0) System.arraycopy(par1.gens, 0, gens, 0, i_first);
        if (i_second - i_first >= 0) System.arraycopy(par2.gens, i_first, gens, i_first, i_second - i_first);
        if (len - i_second >= 0) System.arraycopy(par1.gens, i_second, gens, i_second, len - i_second);

        Arrays.sort(gens);
        validate();
    }

    private void randomize() {
        for (int i = 0; i < 32; ++i) {
            gens[i] = ThreadLocalRandom.current().nextInt(0, 8);
        }
         Arrays.sort(gens);
         validate();
    }

    private void validate() {
        int nextGen = 0;
        for (int i = 0; i < 32; i++) {
            if(gens[i] >= nextGen) {
                if(gens[i] > nextGen) {
                    gens[i] = nextGen;
                }
                nextGen++;
            }
            if(nextGen == 8) {
                break;
            }
        }
    }
}
