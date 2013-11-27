package TP7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Automaton{
    private HashMap<String, State> nodes = new HashMap<String, State>();
    private State initialState;

    public Automaton(String s, boolean acceptance){
        State state = new State(s, acceptance);
        this.addState(state);
        this.initialState = state;
    }

    public void addState(String state, boolean acceptance){
        addState(new State(state, acceptance) );
    }

    private void addState(State state){
        if( !nodes.containsKey(state.name) ){
            nodes.put(state.name, state);
        }
    }

    private void addTransition(String v, String w, char c){
        State origin = nodes.get(v);
        State dest = nodes.get(w);
        if(origin != null && dest != null && !origin.equals(dest)){
            for(Transition trans : origin.adj){
                if(trans.neighbor.name.equals(w)){
                    return;
                }
            }
        }
        origin.adj.add(new Transition(dest, c));
    }

    public boolean accepted(String s){
        State current = initialState;
        for(Character c : s.toCharArray()){
            if(current.admits(c)){
                current = current.nextState(c);
            }else{
                return false;
            }
        }
        return current.acceptance;
    }

    private class State{
        String name;
        boolean acceptance = false;
        boolean visited = false;
        List<Transition> adj = new ArrayList<Transition>();

        public State(String state, boolean acceptance){
            this.name = state;
            this.acceptance = acceptance;
        }

        public boolean admits(char c){
            for(Transition t : adj){
                if(t.c == c) return true;
            }
            return false;
        }

        public State nextState(char c){
            for(Transition t : adj){
                if(t.c == c) return t.neighbor;
            }
            return null;
        }

        public int hashCode(){
            return name.hashCode();
        }

        public boolean equals(Object obj){
            if(obj == null || obj.getClass() != getClass()) return false;
            return name.equals( ((State)obj).name);
        }
    }

    private class Transition{
        char c;
        State neighbor;

        public Transition(State neighbor, char c){
            this.c = c;
            this.neighbor = neighbor;
        }
    }
}
