package World.AnimalStatistics;

import World.Entities.Animal;
import World.IBirthObserver;
import World.IDeathObserver;

import static java.lang.StrictMath.round;

public class NChildrenStatistics implements IBirthObserver, IDeathObserver<Animal>, ITextStatistic, IAccumulateStatistics {
    private int sum = 0;
    private int nAnimals = 0;

    private float sumOfAverages = 0;
    private int nDays = 0;

    public void addAnimal(Animal animal) {
        nAnimals++;
        animal.addIsDeadObserver(this);
    }

    public float getAverageNumberOfChildren() {
        if(nAnimals == 0) {
            return 0;
        }
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
        return  "Average number of children: " + (double) Math.round(getAverageNumberOfChildren() * 100) / 100;
    }

    float getAverageAverageNumberOfChildren() {
        if(nDays == 0) {
            return 0;
        }
        return sumOfAverages / nDays;
    }

    @Override
    public void updateAccumulation() {
        sumOfAverages += getAverageNumberOfChildren();
        nDays++;
    }
}
