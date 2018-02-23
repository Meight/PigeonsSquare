package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthieu Le Boucher
 * A square is an abstraction of the viewport.
 */
public class Square {
    /**
     * Width in pixels of the viewport.
     */
    private int width;

    /**
     * Height in pixels of the viewport.
     */
    private int height;

    /**
     * List of the pigeons currently living in the square.
     */
    private List<Pigeon> pigeons;

    /**
     * List of all the food that was dropped in the square.
     */
    private List<Food> foods;

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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
