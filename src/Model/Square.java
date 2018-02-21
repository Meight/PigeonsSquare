package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthieu Le Boucher
 */
public class Square {
    int width;

    int height;

    List<Pigeon> pigeonsList;

    List<Food> foodList;

    public Square(int width, int height) {
        this.width = width;
        this.height = height;
        pigeonsList = new ArrayList<>();
    }

    public void addPigeon(Pigeon pigeon) {
        this.pigeonsList.add(pigeon);
    }

    public void addFood(Food food) {
        this.foodList.add(food);
    }
}
