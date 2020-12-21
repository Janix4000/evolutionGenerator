package Utility.Config;
import processing.data.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
public class ConfigValidator {
    private static Map<Function<JSONObject, Boolean>, String> getValidations() {
        var validations = new HashMap<Function<JSONObject, Boolean>, String>();

        addSizeValidations(validations);
        addNStartingAnimalsValidations(validations);
        addEnergyValidations(validations);

        return validations;
    }

    private static void addEnergyValidations(HashMap<Function<JSONObject, Boolean>, String> validations) {
        validations.put(json -> {
            int startingEnergy = json.getInt("startEnergy");
            return  startingEnergy > 0;
        }, "Starting energy should be positive.");

        validations.put(json -> {
            int moveCost = json.getInt("moveEnergy");
            return  moveCost > 0;
        }, "Move energy should be positive.");

        validations.put(json -> {
            int grassEnergy = json.getInt("plantEnergy");
            return  grassEnergy > 0;
        }, "Grass energy should be positive.");
    }

    private static void addNStartingAnimalsValidations(HashMap<Function<JSONObject, Boolean>, String> validations) {
        validations.put(json -> {
            int nStartingAnimals = json.getInt("nStartingAnimals");
            return  nStartingAnimals >= 2;
        }, "There should be at least two animals at the start of the simulation.");

        validations.put(json -> {
            int nStartingAnimals = json.getInt("nStartingAnimals");
            int width = json.getInt("width");
            int height = json.getInt("height");
            return  nStartingAnimals <= width * height;
        }, "There are more starting animals than possible positions on the map.");
    }

    private static void addSizeValidations(HashMap<Function<JSONObject, Boolean>, String> validations) {
        validations.put(json -> {
            float jungleRatio = json.getFloat("jungleRatio");
            return  0 < jungleRatio && jungleRatio < 1;
        }, "Jungle ratio should be between 0 and 1");

        validations.put(json -> {
            int width = json.getInt("width");
            int height = json.getInt("height");
            return (width > 0 && height > 0);
        }, "Width and height of map cannot be negative values.");
    }

    public static void validate(JSONObject jsonConfig) {
        StringBuilder message = new StringBuilder();
        var validations = getValidations();
        try {
             for(var entry : validations.entrySet()) {
                 final var validation = entry.getKey();
                 if(!validation.apply(jsonConfig)) {
                     final var msg = entry.getValue();
                     if(message.length() > 0) {
                         message.append("\n");
                     }
                     message.append(msg);
                 }
             }
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("There are no certain parameters in given json config in Config constructor.\n" + e.getMessage());
        }
        if( message.length() > 0 ) {
            throw new IllegalArgumentException("Parameters in jsonConfig are not valid:\n" + message.toString());
        }
    }
}
