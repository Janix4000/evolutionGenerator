package World.WorldStatistics;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class JsonAverageStatisticsSaver {
    public static void save(WorldStatistics statistics, String fileName) throws IOException {
        var results = statistics.getMapOfResults();
        org.json.JSONObject json = new org.json.JSONObject(results);
        try (PrintWriter myFile = new PrintWriter(fileName, StandardCharsets.UTF_8)) {
            myFile.println(json.toString(4));
        }
    }
}
