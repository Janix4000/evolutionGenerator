package Utility.Config;

import processing.core.PApplet;
import processing.data.JSONObject;

public class JsonConfigLoader {
    static public Config load(String fileName, PApplet p) {
        JSONObject json = p.loadJSONObject(fileName);

        return new Config(json);
    }
}
