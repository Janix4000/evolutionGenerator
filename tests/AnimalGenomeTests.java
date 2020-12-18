import World.Entities.AnimalGenome;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AnimalGenomeTests {

    @Test
    public void randomiseConstructorTest() {
        for(int i = 0; i < 100; ++i) {
            AnimalGenome gen = new AnimalGenome();
            assertTrue(gen.is_proper());
        }
    }
    @Test
    public void crossoverTest() {
        for(int i = 0; i < 100; ++i) {
            AnimalGenome gen1 = new AnimalGenome();
            AnimalGenome gen2 = new AnimalGenome();
            AnimalGenome gen3 = new AnimalGenome(gen1, gen2);
            assertTrue(gen3.is_proper());
        }
    }

}
