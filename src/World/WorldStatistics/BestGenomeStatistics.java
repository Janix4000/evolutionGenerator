package World.WorldStatistics;

import World.Entities.Animal;
import World.ObserversInterfaces.IDeathObserver;

import java.util.*;

public class BestGenomeStatistics implements IDeathObserver<Animal>, ITextStatistic, IAccumulateStatistics {
    private final SortedSet<List<Animal>> genes = new TreeSet<>((a, b) -> {
        if(a.size() == b.size()) {
            return a.get(0).getGenomeString().compareTo(b.get(0).getGenomeString());
        }
        return a.size() - b.size();
    });
    private final HashMap<String, Integer> bestGenomes = new HashMap<>();


    public void addAnimal(Animal animal) {
        animal.addIsDeadObserver(this);
        var genome = animal.getGenomeString();
        for(var l : genes) {
            if(l.get(0).getGenomeString().equals(genome)) {
                genes.remove(l);
                l.add(animal);
                genes.add(l);
                return;
            }
        }

        var l = new ArrayList<Animal>();
        l.add(animal);
        genes.add(l);
    }

    public List<Animal> getAnimalsWithBestGenomes() {
        if(genes.isEmpty()) {
            return new ArrayList<>();
        }
        return genes.last();
    }


    @Override
    public void senderIsDead(Animal sender, int deathDay) {
        var genome = sender.getGenomeString();
        for(var l : genes) {
            if(l.get(0).getGenomeString().equals(genome)) {
                genes.remove(l);
                l.remove(sender);
                if(!l.isEmpty()) {
                    genes.add(l);
                }
                break;
            }
        }
    }


    @Override
    public String getText() {
        var bestGenomes = getAnimalsWithBestGenomes();
        if(bestGenomes.isEmpty()) {
            return "All animals are dead";
        }
        return "Best genome: \n" + bestGenomes.get(0).getGenomeString() + "\n" +
                "There are " + bestGenomes.size() + " with this genome.";
    }

    @Override
    public void updateAccumulation() {
        var bestAnimals = getAnimalsWithBestGenomes();
        if(bestAnimals.isEmpty()) {
            return;
        }
        var bestGenome = bestAnimals.get(0).getGenomeString();
        if(bestGenomes.containsKey(bestGenome)) {
            var n = bestGenomes.get(bestGenome);
            bestGenomes.replace(bestGenome, n + 1);
        } else {
            bestGenomes.put(bestGenome, 1);
        }
    }

    @Override
    public HashMap<String, String> getAverageResults() {
        HashMap<String, String> res = new HashMap<>();
        if(bestGenomes.isEmpty()) {
            res.put("BestGenome", "None");
        } else {
            res.put("BestGenome", getBestGenomeString());
        }
        return res;
    }

    public String getBestGenomeString() {
        int nBest = 0;
        String bestG = "";
        for(var entry : bestGenomes.entrySet()) {
            int n = entry.getValue();
            if(n > nBest) {
                bestG = entry.getKey();
                nBest = n;
            }
        }
        return bestG;
    }
}
