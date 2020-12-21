package World.WorldStatistics;

import java.util.HashMap;

public interface IAccumulateStatistics {
    void updateAccumulation();
    HashMap<String, String> getAverageResults();
}
