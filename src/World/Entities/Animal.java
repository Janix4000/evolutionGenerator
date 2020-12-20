package World.Entities;

import Utility.Rectangle;
import Utility.Vector2d;
import World.IDeathObserver;
import World.IPositionChangeObserver;
import World.Map.IWorldMap;
import Utility.MapDirection;
import processing.core.PGraphics;

import java.util.ArrayList;
import java.util.List;

import static Utility.MapDirection.*;
import static java.lang.Integer.min;

public class Animal implements IWorldElement, IPositionChangeSender<Animal>, Comparable<Animal> {

    private final List<IPositionChangeObserver<Animal>> positionObservers = new ArrayList<>();
    private final List<IDeathObserver<Animal>> isDeadObservers = new ArrayList<>();
    private static int NEXT_ID = 0;
    private final int id;
    private final IWorldMap boundaries;

    private Vector2d position;
    private MapDirection mapDirection = N;


    private int maxEnergy = 1;
    private int energy = 1;

    private final AnimalGenome genome;
    private int nChildren = 0;
    private int birthDay;


    public Animal(IWorldMap boundaries) {
        this.boundaries = boundaries;
        genome = new AnimalGenome();
        this.id = NEXT_ID++;
    }
    public Animal(IWorldMap boundaries, Vector2d initialPosition) {
        this(boundaries);
        setPosition(initialPosition);
    }

    public Animal(Animal par0, Animal par1) {
        this(par0.boundaries);
        setMaxEnergy(par1.maxEnergy);
        setEnergy(0);

        par0.assignChild(this);
        par1.assignChild(this);
    }

    public int getNChildren() {
        return nChildren;
    }

    public void setBirthDay(int birthDay) {
        this.birthDay = birthDay;
    }
    public int getBirthDay() {
        return birthDay;
    }

    public String getGenomeString() {
        return genome.toString();
    }
    private void assignChild(Animal child) {
        nChildren++;
        int e = energy / 4;
        consumeEnergy(e);
        child.addEnergy(e);
    }

    private void setEnergy(int energy) {
        this.energy = min(energy, maxEnergy);
    }

    public void setMaxEnergy(int maxEnergy) {
        this.maxEnergy = maxEnergy;
        energy = maxEnergy / 2;
    }

    public void setPosition(Vector2d newPos) {
        var oldPos = this.getWorldPosition();
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
        setEnergy(energy + x);
    }

    public boolean hasNoEnergy() {
        return this.energy <= 0;
    }

    public boolean canBreed() {
        return energy >= maxEnergy / 2;
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
    public Vector2d getWorldPosition() {
        return position;
    }

    @Override
    public void draw(PGraphics graphics, Rectangle box) {
        var pos = box.position;
        var size = box.size;
        graphics.noStroke();
        graphics.fill(0, 0, (float) energy / maxEnergy * 255);
        graphics.ellipse(pos.x + (float) size.x / 2, pos.y + (float) size.y / 2, size.x , size.y);
        graphics.text(Integer.toString(energy), pos.x, pos.y);
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
            observer.positionChanged(oldPos, this.getWorldPosition(), this);
        }
    }

    public void addIsDeadObserver(IDeathObserver<Animal> observer) {
        isDeadObservers.add(observer);
    }
    public void removeIsDeadObserver(IDeathObserver<Animal> observer) {
        isDeadObservers.remove(observer);
    }

    public void kill(int deathDay) {
        isDeadObservers.forEach(observer -> observer.senderIsDead(this, deathDay));
    }

    public int getEnergy() {
        return energy;
    }

    @Override
    public int compareTo(Animal that) {
        return -(this.energy - that.energy);
    }

    public Integer getId() {
        return id;
    }
}
