package World.AnimalStatistics;

import World.Entities.Animal;
import World.IBirthSender;

import java.util.*;

public class WorldStatistics implements IAccumulateStatistics {
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

    public void addBirthSender(IBirthSender sender) {
        sender.addObserver(nChildrenStatistics);
        sender.addObserver(animalTargetSystem);
    }

    public void update() {
        for(var statistic : getAccumulateStatistics()) {
            statistic.updateAccumulation();
        }
    }

    public List<Integer> getEnergies() {
        return energies;
    }

    public List<Integer> getNLives() {
        return nLives;
    }

    public List<ITextStatistic> getTextStatistics() {
        return new ArrayList<>(Arrays.asList(mapStatistics, bestGenomeStatistics, nLivedDaysStatistics, nChildrenStatistics, animalTargetSystem));
    }

    public List<IAccumulateStatistics> getAccumulateStatistics() {
        return new ArrayList<>(Arrays.asList(bestGenomeStatistics, nLivedDaysStatistics, nChildrenStatistics, this));
    }

    public int getNAnimals() {
        return mapStatistics.getNAnimals();
    }
    public int getNGrasses() {
        return mapStatistics.getNGrasses();
    }
    public List<Animal> getAnimalsWithBestGenomes() {
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

    public void setTarget(Animal animal) {
        animalTargetSystem.setTarget(animal);
    }

    public boolean hasNoTarget() {
        return animalTargetSystem.hasNoTarget();
    }

    public Animal getTarget() {
        return animalTargetSystem.getTarget();
    }

    @Override
    public void updateAccumulation() {
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
}
