package World.Entities;

import Utility.Vector2d;
import World.IDeathObserver;
import World.IPositionChangeObserver;
import World.Map.IWorldMap;
import Utility.MapDirection;

import java.util.ArrayList;
import java.util.List;

import static Utility.MapDirection.*;

public class Animal implements IWorldElement, IPositionChangeSender<Animal>, Comparable<Animal> {
    private Vector2d position;
    private MapDirection mapDirection = N;
    private final List<IPositionChangeObserver<Animal>> positionObservers = new ArrayList<>();
    private final List<IDeathObserver<Animal>> isDeadObservers = new ArrayList<>();
    private int energy = 1;
    private final IWorldMap boundaries;
    private final AnimalGenome genome;

    // private final IWorldMap worldMap;

    public Animal(IWorldMap boundaries) {
        this(boundaries, new Vector2d(0 ,0 ));
    }
    public Animal(IWorldMap boundaries, Vector2d initialPosition) {
        this.boundaries = boundaries;
        setPosition(initialPosition);
        genome = new AnimalGenome();
    }

    private void setPosition(Vector2d newPos) {
        var oldPos = this.getPosition();
        newPos = boundaries.getProperNextPosition(newPos);
        this.position = newPos;
        positionChanged(oldPos);
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
        setPosition(newPos);
    }

    public void randomlyRotate() {
        mapDirection = mapDirection.next(genome.getRandomRotation());
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
    @Override
    public Vector2d getPosition() {
        return position;
    }
    @Override
    public void addPositionObserver(IPositionChangeObserver<Animal> observer) {
        this.positionObservers.add(observer);
    }
    @Override
    public void removePositionObserver(IPositionChangeObserver<Animal> observer) {
        this.positionObservers.remove(observer);
    }

    @Override
    public IWorldElement getWorldElement() {
        return this;
    }

    private void positionChanged(Vector2d oldPos) {
        for(var observer : this.positionObservers) {
            observer.positionChanged(oldPos, this.getPosition(), this);
        }
    }

    public void addIsDeadObserver(IDeathObserver<Animal> observer) {
        isDeadObservers.add(observer);
    }
    public void removeIsDeadObserver(IDeathObserver<Animal> observer) {
        isDeadObservers.remove(observer);
    }

    public void kill() {
        isDeadObservers.forEach(observer -> observer.senderIsDead(this));
    }

    public int getEnergy() {
        return energy;
    }

    @Override
    public int compareTo(Animal that) {
        return -(this.energy - that.energy);
    }
}
