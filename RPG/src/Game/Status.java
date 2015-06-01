package Game;

/**
 * Created by piano_000 on 6/1/2015.
 */
public enum Status {
    NORMAL(1), SLEEPING(2), PARALYZED(3), FROZEN(4), GUARDING(5), FAINTED(6);

    private int value;

    private Status(int value) {
        this.value = value;
    }
}
