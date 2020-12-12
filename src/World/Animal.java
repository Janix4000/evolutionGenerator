package World;

import Utility.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Animal implements IWorldElement, IPositionChangeSender {
    private Vector2d position;
    private final MapDirection mapDirection = MapDirection.N;
    private final List<IPositionChangeObserver> observers = new ArrayList<>();
    private int energy;


    // private final IWorldMap worldMap;

    public Animal() {
        this.position = new Vector2d(2, 2);
        // this.worldMap = new RectangularMap(5, 5);
        // this.worldMap.place(this);
    }
    public Animal(Vector2d initialPosition) {
        this.position = initialPosition;
    }

    public String toTestString() {
        return position.toString() + " " + mapDirection.toString();
    }


    @Override
    public String toString() {
        return switch (this.mapDirection) {
            case S -> "S";
            case E -> "E";
            case N -> "N";
            case W -> "W";
            case NE -> "Ne";
            case NW -> "NW";
            case SE -> "SE";
            case SW -> "SW";
        };
    }

    public void consumeEnergy(int x) {
        addEnergy( -x );
    }

    public void addEnergy(int x) {
        this.energy += x;
    }

    public boolean hasNoEnergy() {
        return this.energy <= 0;
    }

    public void move() {
        var add = mapDirection.toUnitVector();
        var newPos = position.add(add);
        var oldPos = this.getPosition();
        this.position = newPos;
        positionChanged(oldPos);
    }
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Animal)) {
            return false;
        }
        Animal that = (Animal) other;
        return this.position == that.position &&
                this.mapDirection == that.mapDirection;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void addPositionObserver(IPositionChangeObserver observer) {
        this.observers.add(observer);
    }
    public void removePositionObserver(IPositionChangeObserver observer) {
        this.observers.remove(observer);
    }

    @Override
    public IWorldElement getWorldElement() {
        return this;
    }

    private void positionChanged(Vector2d oldPos) {
        for(var observer : this.observers) {
            observer.positionChanged(oldPos, this.getPosition(), this);
        }
    }

}
