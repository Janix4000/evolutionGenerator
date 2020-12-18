package World.AnimalStatistics;

import World.Entities.Animal;
import World.Systems.AnimalsFamily;

import java.util.HashSet;

public class AnimalTargetSystem {
    private Animal target = null;
    private final HashSet<Integer> descendants = new HashSet<>();


    public void setTarget(Animal animal) {
        removeTarget();
        target = animal;
        descendants.add(animal.getId());
    }

    public boolean hasTarget() {
        return target != null;
    }

    public void removeTarget() {
        target = null;
        descendants.clear();
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

}

