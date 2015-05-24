package Items;

/**
 * Created by piano_000 on 5/24/2015.
 */
public enum Intent {
    HEAL(1), HARM(2), EQUIP(3);

    private int value;

    private Intent(int value) {
        this.value = value;
    }
}
