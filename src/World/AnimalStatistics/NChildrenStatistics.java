package World.AnimalStatistics;

import World.Entities.Animal;
import World.IDeathObserver;

import java.util.ArrayList;

public class NChildrenStatistics implements IDeathObserver<Animal> {
    private int sum = 0;
    private int nDead = 0;

    public void addAnimal(Animal animal) {
        animal.addIsDeadObserver(this);
    }

    @Override
    public void senderIsDead(Animal sender) {
        sum += sender.getNChildren();
        nDead++;
    }

    public float getAverageNumberOfChildren() {
        return (float) sum / nDead;
    }
}
