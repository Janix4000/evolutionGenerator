package Utility.Config;

import processing.core.PApplet;
import processing.data.JSONObject;

import java.io.IOException;

public class JsonConfigLoader {
    static public Config load(String fileName, PApplet p) throws IOException {
        JSONObject json;
        try {
            json = p.loadJSONObject(fileName);
        } catch (NullPointerException e) {
            throw new IOException("Couldn't load config from " + fileName);
        }
        try{
            ConfigValidator.validate(json);
        } catch (IllegalArgumentException e) {
            throw new IOException("Parameter(s) in config file " + fileName + " are not valid\n" + e.getMessage());
        }
        try {
            return new Config(json);
        } catch (IllegalArgumentException e) {
            throw new IOException("Missing parameter(s) in config file " + fileName + "\n" + e.getMessage());
        }
    }
}
