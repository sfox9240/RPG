package Game;

/**
 * Created by piano_000 on 6/1/2015.
 */
public class Status {
    private State state;
    private int stateDuration; //A duration of -1 is meant to last forever only applies for noraml and fainted status.
    private Illness illness;
    private int illnessDuration;

    public Status(State state, int sDuration, Illness illness, int iDuration) {
        this.state = state;
        this.stateDuration = sDuration;
        this.illness = illness;
        this.illnessDuration = iDuration;

    }

    public int getStateDuration() {
        return stateDuration;
    }

    public int getIllnessDuration() {
        return illnessDuration;
    }

    public State getState() {
        return state;
    }

    public Illness getIllness() {
        return illness;
    }

    public void setState(State s, int duration) {
        state = s;
        stateDuration = duration;
    }

    public void setIllness(Illness i, int duration) {
        illness = i;
        illnessDuration = duration;
    }

    public void elapseStete(int numberOfTurns) {
        stateDuration = stateDuration - numberOfTurns;
    }

    public void elapseIllness(int numberOfTurns) {
        illnessDuration = illnessDuration - numberOfTurns;
    }
}
