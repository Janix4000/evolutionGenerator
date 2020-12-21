package World.WorldStatistics;

import World.Entities.Animal;
import World.IBirthObserver;
import World.IDeathObserver;

import java.util.HashSet;

public class AnimalTargetStatistics implements IDeathObserver<Animal>, IBirthObserver, ITextStatistic {
    private Animal target = null;
    private final HashSet<Integer> descendants = new HashSet<>();
    private int nChildren = 0;
    private int deathDay = -1;

    public void setTarget(Animal animal) {
        removeTarget();
        target = animal;
        descendants.add(animal.getId());
        target.addIsDeadObserver(this);
    }

    public boolean hasNoTarget() {
        return target == null;
    }

    public void removeTarget() {
        if(target != null) {
            target.removeIsDeadObserver(this);
        }
        target = null;
        descendants.clear();
        deathDay = -1;
        nChildren = 0;
    }

    public void checkFamily(Animal parent, Animal child) {
        if(hasNoTarget()) {
            return;
        }
        int childId = child.getId();
        if(descendants.contains(childId)) {
            return;
        }
        var parentId = parent.getId();
        if(descendants.contains(parentId)) {
            descendants.add(childId);
        }
        if(parent.isEqual(target)) {
            nChildren++;
        }
    }

    public int getNDescendants() {
        return descendants.size() - 1;
    }

    public Animal getTarget() {
        return target;
    }

    @Override
    public void senderIsDead(Animal sender, int deathDay) {
        this.deathDay = deathDay;
    }

    @Override
    public String getText() {
        if (hasNoTarget()) {
            return "\nThere is no target";
        } else {
            String text = "\nTarget: \n" +
                target.getGenomeString() + "\n" +
                "Number of children: " + nChildren + "\n" +
                "Total number of descendants: " + getNDescendants() + "\n" +
                "Birthday: " + target.getBirthDay() + "\n";
            if(target.hasNoEnergy()) {
                text += "Day of death: " + deathDay + "\n" +
                        "Lived " + (deathDay - target.getBirthDay()) + " days";
            } else {
                text += "Energy: " + target.getEnergy();
            }
            return text;
        }
    }

    @Override
    public void hasBeenGivenBirth(Animal parent, Animal child) {
        checkFamily(parent, child);
    }
}

