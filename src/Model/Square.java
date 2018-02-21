package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthieu Le Boucher
 */
public class Square {
    List<Pigeon> pigeonsList;

    public Square() {
        pigeonsList = new ArrayList<>();
    }

    public void addPigeon(Pigeon pigeon) {
        this.pigeonsList.add(pigeon);
    }


}
