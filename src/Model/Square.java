package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthieu Le Boucher
 */
public class Square {
    int width;

    int height;

    List<Pigeon> pigeons;

    List<Food> foods;

    public Square(int width, int height) {
        this.width = width;
        this.height = height;
        pigeons = new ArrayList<>();
    }

    public void addPigeon(Pigeon pigeon) {
        this.pigeons.add(pigeon);
    }

    public void addFood(Food food) {
        this.foods.add(food);
    }

    /**
     * Starts the threads underlying behind the Pigeon abstraction.
     */
    public void animatePigeons() {
        for(Pigeon pigeon : pigeons) {
            pigeon.start();
        }
    }
}
