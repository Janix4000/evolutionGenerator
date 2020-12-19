package World.AnimalStatistics;

import World.Entities.Animal;
import World.IDeathObserver;

public class NLivedDaysStatistics implements IDeathObserver<Animal> {
    private int nDead = 0;
    private int sum = 0;
    void addAnimal(Animal animal) {
        animal.addIsDeadObserver(this);
    }


    @Override
    public void senderIsDead(Animal sender, int deathDay) {
        sum += deathDay - sender.getBirthDay();
        nDead++;
    }
}
