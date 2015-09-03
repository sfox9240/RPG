package Game;

import Actor.Actor;
import java.util.Vector;

/**
 * Created by piano_000 on 7/13/2015.
 * Version 1.0 of this only works for the party...I think
 */
public class ThreatAnalyzer {

    protected double greatestThreat = 0; //Value of the highest threat
    protected int greatestSource = -1; //Source of the highest threat
    protected Vector<Actor> team = null;
    protected Vector<Vector<Double>> threats = null;



    public ThreatAnalyzer(Vector<Actor> heroes) {
        this.team = heroes;
        threats = new Vector<Vector<Double>>();
        populateVec();
    }

    private void populateVec() {
        System.out.println(team.size());
        for(int i = 0; i < team.size(); i++) {
            threats.add(new Vector<Double>());
        }
    }

    protected void addThreat(int source, double amount) {
        threats.get(source).add(amount);
    }

    private void cleanVec() {
        for(int i = 0; i < threats.size(); i++) {
            threats.get(i).removeAllElements();
        }

    }

    protected void determineThreat() {
        double currentThreat = 0;
        for(int i = 0; i < threats.size(); i++) {
            System.out.println("threater: " + threats.get(i));

            for(int j = 0; j < threats.get(i).size(); j++) {
                currentThreat = currentThreat + threats.get(i).get(j);
                System.out.println("current threat: " + threats.get(i).get(j));
                if(currentThreat >= greatestThreat) {
                    greatestThreat = currentThreat;
                    greatestSource = i;
                }
            }
            System.out.println("max threat: " + greatestThreat);
            currentThreat = 0;
        }
        System.out.println("greatest threat: " + greatestSource);
        cleanVec();
    }

    protected int getGreatestSource() {
        return greatestSource;
    }
}

/*
0 : 8
1 : 2
1 : 4
1 : 4
2 : 6
2 : 6
 */