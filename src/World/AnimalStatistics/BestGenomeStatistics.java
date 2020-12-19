package World.AnimalStatistics;

import World.Entities.Animal;
import World.IDeathObserver;

import java.util.*;

public class BestGenomeStatistics implements IDeathObserver<Animal>, IStatistic {
    private final SortedSet<List<Animal>> genes = new TreeSet<>((a, b) -> {
        if(a.size() == b.size()) {
            return a.get(0).getGenomeString().compareTo(b.get(0).getGenomeString());
        }
        return -(a.size() - b.size());
    });


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
        return genes.first();
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
}
