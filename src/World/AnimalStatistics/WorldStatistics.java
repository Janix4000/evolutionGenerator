package World.AnimalStatistics;

import World.Entities.Animal;
import World.IBirthSender;

import java.util.*;

import static java.lang.StrictMath.round;

public class WorldStatistics implements IAccumulateStatistics {
    private final AnimalTargetStatistics animalTargetSystem =  new AnimalTargetStatistics();
    private final BestGenomeStatistics bestGenomeStatistics = new BestGenomeStatistics();
    private final NChildrenStatistics nChildrenStatistics = new NChildrenStatistics();
    private final NLivedDaysStatistics nLivedDaysStatistics = new NLivedDaysStatistics();

    private final IMapStatistics mapStatistics;

    static private final int N_HISTORY = 200;
    private final List<Integer> energies = new ArrayList<>();
    private final List<Integer> nGrasses = new ArrayList<>();
    private final List<Integer> nLives = new ArrayList<>();

    public WorldStatistics(IMapStatistics mapStatistics) {
        this.mapStatistics = mapStatistics;
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
        nGrasses.add(mapStatistics.getNGrasses());
    }

    private float getAverageAverageNumberOf(List<Integer> list) {
        if (list.isEmpty()) {
            return 0;
        }
        return (float) list.stream().reduce(Integer::sum).get() / list.size();
    }

    @Override
    public HashMap<String, String> getAverageResults() {
        HashMap<String, String> res = new HashMap<>();
        res.put("AverageNumberOfLivingAnimals", String.valueOf(round(getAverageAverageNumberOf(nLives))));
        res.put("AverageNumberOfGrasses", String.valueOf(round(getAverageAverageNumberOf(nGrasses))));
        res.put("AverageEnergy", String.valueOf(round(getAverageAverageNumberOf(energies))));
        return res;
    }

    public HashMap<String, String> getMapOfResults() {
        var statistics = getAccumulateStatistics();
        return statistics.stream().map(IAccumulateStatistics::getAverageResults).
                reduce(new HashMap<>(), (a, b) -> { a.putAll(b); return a; });
    }

}
