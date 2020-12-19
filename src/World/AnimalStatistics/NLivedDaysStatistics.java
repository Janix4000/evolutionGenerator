package World.AnimalStatistics;

import World.Entities.Animal;
import World.IDeathObserver;

import static java.lang.StrictMath.round;

public class NLivedDaysStatistics implements IDeathObserver<Animal>, IStatistic {
    private int nDead = 0;
    private int sum = 0;
    void addAnimal(Animal animal) {
        animal.addIsDeadObserver(this);
    }

    public float getAverageLifeSpan() {
        if(nDead == 0) {
            return 0;
        }
        return (float) sum / nDead;
    }

    @Override
    public void senderIsDead(Animal sender, int deathDay) {
        sum += deathDay - sender.getBirthDay();
        nDead++;
    }

    @Override
    public String getText() {
        return "Average lifespan: " + round(getAverageLifeSpan());
    }
}
