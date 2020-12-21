package World.Map;

import Utility.Vector2d;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Integer.max;

public class FreePositionsInRegionsManager {
    private final Set<Vector2d> freePoses = new HashSet<>();
    private final IRandomPositionGenerator positionGenerator;
    private int maxNCells = 0;

    public FreePositionsInRegionsManager(IRandomPositionGenerator positionGenerator) {
        this.positionGenerator = positionGenerator;
    }


    boolean isEmpty() {
        return freePoses.size() == 0;
    }

    void removeFreePosition(Vector2d pos) {
        freePoses.remove(pos);
    }
    void insertFreePosition(Vector2d pos) {
        freePoses.add(pos);
        maxNCells = max(maxNCells, freePoses.size());
    }

    public Vector2d getRandomFreePosition() {
        if(isEmpty()) {
            throw new IllegalArgumentException("Cannot pop free position, because there are no free positions");
        }
        return shouldRandomise() ? getRandomisedFreePosition() : getIteratedFreePosition();
    }

    private Vector2d getIteratedFreePosition() {
        int idx = ThreadLocalRandom.current().nextInt(0, freePoses.size());
        int i = 0;
        for(var pos : freePoses) {
            if(i == idx) {
                return pos;
            } else {
                i++;
            }
        }
        throw new IllegalArgumentException("No free poses?");
    }

    private Vector2d getRandomisedFreePosition() {
        Vector2d freePos;
        do {
            freePos = positionGenerator.getRandomValidPosition();
        } while(!freePoses.contains(freePos));
        return freePos;
    }

    private boolean shouldRandomise() { // O(sqrt(n))
        int k = freePoses.size();
        int n = maxNCells;
        return 2 * n < k * k;
    }
}
