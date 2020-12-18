import World.Entities.AnimalGenome;

public class AnimalGenomTests {
    void randomiseConstructorTest() {
        for(int i = 0; i < 100; ++i) {
            AnimalGenome gen = new AnimalGenome();
            //assertTrue(gen.is_proper());
        }
    }

    void crossoverTest() {
        for(int i = 0; i < 100; ++i) {
            AnimalGenome gen1 = new AnimalGenome();
            AnimalGenome gen2 = new AnimalGenome();
            AnimalGenome gen3 = new AnimalGenome(gen1, gen2);
            //assertTrue(gen3.is_proper());
        }
    }

}
