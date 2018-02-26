package Model;

/**
 * @author : Matthieu Le Boucher
 */
public class PigeonFactory {
    public enum Species {
        BISET, COLOMBIN, RAMIER
    };

    public static Pigeon createPigeon(Species species, int x, int y, double speed, Square square) {
        switch (species) {
            case BISET:
                return new Biset(x, y, speed, square);
            case COLOMBIN:
                return new Colombin(x, y, speed, square);
            default:
                return new Ramier(x, y, speed, square);
        }
    }
}
