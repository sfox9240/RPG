package Game;

/**
 * Created by piano_000 on 6/1/2015.
 */
public enum Illness {
    NONE(1), POISON(2), FREEZING(3), BURNING(4);

    private int value;

    Illness(int value) {
        this.value = value;
    }
}
