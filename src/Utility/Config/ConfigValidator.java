package Utility.Config;

import processing.data.JSONObject;

import java.util.ArrayList;
import java.util.List;

abstract class ConfigValidation {
    protected abstract boolean isValid(JSONObject json);

    public String validate(JSONObject json) {
        if(isValid(json)) {
            return "";
        } else {
            return notValidMessage;
        }
    }
    private final String notValidMessage;
    protected ConfigValidation(String notValidMessage) {
        this.notValidMessage = notValidMessage;
    }
}

class MapSizeValidation extends ConfigValidation {
    public MapSizeValidation() {
        super("Width and height of map cannot be negative values.");
    }
    @Override
    protected boolean isValid(JSONObject json) {
        int width = json.getInt("width");
        int height = json.getInt("height");
        return (width*height > 0 && width + height > 0);
    }
}
class NStartingAnimalsValidation extends ConfigValidation {
    public NStartingAnimalsValidation() {
        super("There should be at least two animals at the start of the simulation");
    }

    @Override
    protected boolean isValid(JSONObject json) {
        int nStartingAnimals = json.getInt("nStartingAnimals");
        return  nStartingAnimals >= 2;
    }
}

class StartingEnergyValidation extends ConfigValidation {
    public StartingEnergyValidation() {
        super("Starting energy should be positive");
    }

    @Override
    protected boolean isValid(JSONObject json) {
        int startingEnergy = json.getInt("startEnergy");
        return  startingEnergy > 0;
    }
}

class MoveEnergyValidation extends ConfigValidation {
    public MoveEnergyValidation() {
        super("Move energy should be positive");
    }

    @Override
    protected boolean isValid(JSONObject json) {
        int moveCost = json.getInt("moveEnergy");
        return  moveCost > 0;
    }
}

class GrassEnergyValidation extends ConfigValidation {
    public GrassEnergyValidation() {
        super("Grass energy should be positive");
    }

    @Override
    protected boolean isValid(JSONObject json) {
        int grassEnergy = json.getInt("plantEnergy");
        return  grassEnergy > 0;
    }
}

class JungleRatioValidation extends ConfigValidation {
    public JungleRatioValidation() {
        super("Jungle ratio should be between 0 and 1");
    }

    @Override
    protected boolean isValid(JSONObject json) {
        float jungleRatio = json.getFloat("jungleRatio");
        return  0 < jungleRatio && jungleRatio < 1;
    }
}


public class ConfigValidator {
    private static List<ConfigValidation> getValidations() {
        List<ConfigValidation> validations = new ArrayList<>();
        validations.add(new MapSizeValidation());
        validations.add(new NStartingAnimalsValidation());
        validations.add(new GrassEnergyValidation());
        validations.add(new JungleRatioValidation());
        validations.add(new StartingEnergyValidation());
        validations.add(new MoveEnergyValidation());
        return validations;
    }
    public static void validate(JSONObject jsonConfig) {
        StringBuilder message = new StringBuilder();
        var validations = getValidations();
        try {
             {
                 for(var v : validations) {
                     if(message.length() > 0) {
                         message.append("\n");
                     }
                     message.append(v.validate(jsonConfig));
                 }
            }
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("There are no parameters in got json config in Config constructor\n" + e.getMessage());
        }
        if( message.length() > 0 ) {
            throw new IllegalArgumentException("Parameters fo jsonConfig are not valid:\n" + message.toString());
        }
    }
}
