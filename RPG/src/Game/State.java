package Game;

/**
 * Created by piano_000 on 1/30/2016.
 */
public enum State {
    NORMAL(1), SLEEPING(2), PARALYZED(3), FROZEN(4), GUARDING(5), FAINTED(8);
    private int value;

    State(int value) {
        this.value = value;
    }
}


