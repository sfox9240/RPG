package Game;

import java.util.Vector;

/**
 * Created by piano_000 on 6/1/2015.
 */
public class Status {
    private State state;
    private int stateDuration; //A duration of -1 is meant to last forever only applies for noraml and fainted status.
    private Illness illness;
    private int illnessDuration;
    private Vector<Element> strengths;
    private Vector<Element> weaknesses;

    public Status(State state, int sDuration, Illness illness, int iDuration) {
        this.state = state;
        this.stateDuration = sDuration;
        this.illness = illness;
        this.illnessDuration = iDuration;
        strengths = new Vector<Element>();
        weaknesses = new Vector<Element>();
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

    public Vector<Element> getStrengths() {
        return strengths;
    }

    public void setStrengths(Vector<Element> e) {
        strengths = e;
    }

    public void addStrength(Element e) {
        strengths.add(e);
    }

    public void removeStrength(Element e) {
        strengths.remove(e);
    }

    public Vector<Element> getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(Vector<Element> e) {
        weaknesses = e;
    }

    public void addWeakness(Element e) {
        weaknesses.add(e);
    }

    public void removeWeakness(Element e) {
        weaknesses.remove(e);
    }
}
