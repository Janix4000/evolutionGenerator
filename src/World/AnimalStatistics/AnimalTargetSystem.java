package World.AnimalStatistics;

import World.Entities.Animal;
import World.IDeathObserver;
import World.Systems.AnimalsFamily;

import java.util.HashSet;

public class AnimalTargetSystem implements IDeathObserver<Animal> {
    private Animal target = null;
    private final HashSet<Integer> descendants = new HashSet<>();
    private int deathDay = -1;

    public void setTarget(Animal animal) {
        removeTarget();
        target = animal;
        descendants.add(animal.getId());
        target.addIsDeadObserver(this);
    }

    public boolean hasTarget() {
        return target != null;
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
        if(!hasTarget()) {
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
}

