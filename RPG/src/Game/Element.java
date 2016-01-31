package Game;

/**
 * Created by piano_000 on 5/21/2015.
 */
public enum Element {
    FIRE(1), ICE(2), LIGHT(3), DARK(4), POISON(5), PARALYZE(6);


    private int value;

    private Element(int value) {
        this.value = value;
    }
}