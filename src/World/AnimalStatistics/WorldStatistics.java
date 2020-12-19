package World.AnimalStatistics;

import World.Entities.Animal;

import java.util.*;

public class WorldStatistics {
    private final AnimalTargetSystem animalTargetSystem =  new AnimalTargetSystem();
    private final BestGenomeStatistics bestGenomeStatistics = new BestGenomeStatistics();
    private final NChildrenStatistics nChildrenStatistics = new NChildrenStatistics();
    private final NLivedDaysStatistics nLivedDaysStatistics = new NLivedDaysStatistics();

    private final IMapStatistics mapStatistics;

    static private final int N_HISTORY = 200;
    private final List<Integer> energies = new ArrayList<>();
    private final List<Integer> nLives = new ArrayList<>();
    private final Map<String, Integer> genes = new HashMap<>();

    public WorldStatistics(IMapStatistics mapStatistics) {
        this.mapStatistics = mapStatistics;
//        for(int i = 0; i < N_HISTORY; ++i) {
//            energies.add(0);
//            nLives.add(0);
//        }
    }


    public void addAnimal(Animal animal) {
        bestGenomeStatistics.addAnimal(animal);
        nChildrenStatistics.addAnimal(animal);
        nLivedDaysStatistics.addAnimal(animal);
    }

    public void update() {
//        energies.set(idx, (int) mapStatistics.getAverageEnergy());
//        nLives.set(idx, mapStatistics.getNAnimals());
//        idx = (idx + 1) % N_HISTORY;

        energies.add((int) mapStatistics.getAverageEnergy());
        nLives.add(mapStatistics.getNAnimals());
        var bestGene = bestGenomeStatistics.getAnimalsWithBestGenomes().get(0).getGenomeString();
        if(genes.containsKey(bestGene)) {
            var n = genes.remove(bestGene);
            genes.put(bestGene, n + 1);
        } else {
            genes.put(bestGene, 1);
        }
    }

    public List<Integer> getEnergies() {
        return energies;
    }

    public List<Integer> getNLives() {
        return nLives;
    }

    public List<IStatistic> getStatistics() {
        return new ArrayList<>(Arrays.asList(mapStatistics, nLivedDaysStatistics, nChildrenStatistics, animalTargetSystem));
    }

    public int getNAnimals() {
        return mapStatistics.getNAnimals();
    }
    public int getNGrasses() {
        return mapStatistics.getNGrasses();
    }
    public List<Animal> getBestGenomes() {
        return bestGenomeStatistics.getAnimalsWithBestGenomes();
    }
    public float getAverageEnergy() {
        return mapStatistics.getAverageEnergy();
    }
    public float getAverageLifeSpan() {
        return nLivedDaysStatistics.getAverageLifeSpan();
    }
    public float getAverageNChildren() {
        return nChildrenStatistics.getAverageNumberOfChildren();
    }

}
