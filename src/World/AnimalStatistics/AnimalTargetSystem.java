package World.AnimalStatistics;

import World.Entities.Animal;
import World.IDeathObserver;
import World.Systems.AnimalsFamily;

import java.util.HashSet;

public class AnimalTargetSystem implements IDeathObserver<Animal>, IStatistic{
    private Animal target = null;
    private final HashSet<Integer> descendants = new HashSet<>();
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
    }

    public void checkFamily(AnimalsFamily family) {
        if(hasNoTarget()) {
            return;
        }
        var par0_id = family.firstParent.getId();
        var par1_id = family.secondParent.getId();
        if(descendants.contains(par0_id) || descendants.contains(par1_id)) {
            descendants.add(family.child.getId());
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
                "Number of children: " + target.getNChildren() + "\n" +
                "Total number of descendants: " + getNDescendants() + "\n" +
                "Birthday: " + target.getBirthDay() + "\n" +
                "Energy: " + target.getEnergy();
            if(target.hasNoEnergy()) {
                text = text + "\n" +
                    "Day of death: " + deathDay;
            }
            return text;
        }
    }
}

