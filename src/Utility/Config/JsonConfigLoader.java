package Utility.Config;

import processing.core.PApplet;
import processing.data.JSONObject;

import java.io.IOException;

public class JsonConfigLoader {
    static public Config load(String fileName, PApplet p) throws IOException {
        try {
            JSONObject json = p.loadJSONObject(fileName);
            try {
                return new Config(json);
            } catch (RuntimeException e) {
                throw new IOException("Missing parameter(s) in config file " + fileName + "\n" + e.getMessage());
            }
        } catch (NullPointerException e) {
            throw new IOException("Couldn't load config from " + fileName);
        }
    }
}
