package World.WorldStatistics;

import World.Entities.Animal;
import World.ObserversInterfaces.IDeathObserver;

import java.util.HashMap;

import static java.lang.StrictMath.round;

public class NLivedDaysStatistics implements IDeathObserver<Animal>, ITextStatistic, IAccumulateStatistics {
    private int nDead = 0;
    private int sum = 0;
    private int sumOfSums = 0;
    private int nDays = 0;
    void addAnimal(Animal animal) {
        animal.addIsDeadObserver(this);
    }

    public float getAverageLifeSpan() {
        if(nDead == 0) {
            return 0;
        }
        return (float) sum / nDead;
    }

    public float getAverageLifeSpanOfPopulation() {
        if(nDays == 0) {
            return 0;
        }
        return (float) sumOfSums / nDays;
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

    @Override
    public void updateAccumulation() {
        sumOfSums += getAverageLifeSpan();
        nDays++;
    }

    @Override
    public HashMap<String, String> getAverageResults() {
        HashMap<String, String> res = new HashMap<>();
        res.put("AverageLifeSpan", String.valueOf(round(getAverageLifeSpanOfPopulation())));
        return res;
    }
}
