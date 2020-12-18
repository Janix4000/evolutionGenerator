package World.Systems;

import World.Entities.Animal;

public class AnimalsFamily {
    public Animal firstParent;
    public Animal secondParent;
    public Animal child;

    public AnimalsFamily(Animal firstParent, Animal secondParent, Animal child) {
        this.child = child;
        this.firstParent = firstParent;
        this.secondParent = secondParent;
    }
}
