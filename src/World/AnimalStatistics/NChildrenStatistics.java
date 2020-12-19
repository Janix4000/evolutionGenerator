package World.AnimalStatistics;

import World.Entities.Animal;
import World.IBirthObserver;
import World.IBirthSender;
import World.IDeathObserver;

import java.util.ArrayList;

public class NChildrenStatistics implements IBirthObserver, IDeathObserver<Animal>, IStatistic {
    private int sum = 0;
    private int nAnimals = 0;

    public void addAnimal(Animal animal) {
        nAnimals++;
    }

    public float getAverageNumberOfChildren() {
        return (float) sum / nAnimals;
    }

    @Override
    public void hasBeenGivenBirth(Animal parent, Animal child) {
        sum++;
    }

    @Override
    public void senderIsDead(Animal sender, int deathDay) {
        nAnimals--;
        sum -= sender.getNChildren();
    }

    @Override
    public String getText() {
        return  "Average number of children: " + getAverageNumberOfChildren();
    }
}
