package World.Map;

import Utility.Vector2d;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class FreePositionsManager {
    Set<Vector2d> freePoses = new HashSet<>();
    IRandomPositionGenerator positionGenerator;

    FreePositionsManager(IRandomPositionGenerator positionGenerator) {
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
    }

    public Vector2d getRandomFreePosition() {
        if(isEmpty()) {
            throw new IllegalArgumentException("Cannot pop free position, because there are no free positions");
        }
        return shouldRandomise() ? getRandomisedFreePosition() : getIteratedFreePosition();
    }

    public Vector2d popRandomFreePosition() {
        Vector2d freePos = getRandomFreePosition();
        removeFreePosition(freePos);
        return  freePos;
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

    private boolean shouldRandomise() {
        int k = freePoses.size();
        return k > 2;
    }
}
