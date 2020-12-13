package World;

import java.util.*;

public class AnimalCell {
    private final List<Animal> cell = new ArrayList<>();
    public int size() {
        return cell.size();
    }
    public void add(Animal animal) {
        cell.add(animal);
    }

    public void remove (Animal animal) {
        cell.remove(animal);
    }

    List<Animal> getTop(int n) {
        if(n > cell.size()) {
            throw new IllegalArgumentException("There is only " + cell.size() + " animals in the cell, wanted top " + n);
        }
        validateSort();
        var topN = new ArrayList<Animal>(n);
        return cell.subList(0, n);
    }
    List<Animal> getBestAnimals() {
        if(cell.isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("Cannot take any best animal, because there is no animals in the cell");
        }
        validateSort();
        var bestEnergy = cell.get(0).getEnergy();
        int n = 0;
        for(var animal : cell) {
            if(animal.getEnergy() != bestEnergy) {
                break;
            }
            ++n;
        }
        return cell.subList(0, n);
    }

    private void validateSort() {
        cell.sort(Animal::compareTo);
    }

}
