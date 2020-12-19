package World.AnimalStatistics;

import World.Entities.Animal;
import World.IDeathObserver;

import java.util.*;

public class BestGenomeStatistics implements IDeathObserver<Animal> {
    private final SortedSet<List<Animal>> genes = new TreeSet<>((a, b) -> -(a.size() - b.size()));


    public void addAnimal(Animal animal) {
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
            return null;
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


}
