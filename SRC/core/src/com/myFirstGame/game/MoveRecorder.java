package com.myFirstGame.game;

/**
 * Created by chris on 3/3/15.
 */
public class MoveRecorder {

    private final int FRAMES = 1200;

    public class Move{
        public boolean w;
        public boolean s;
        public boolean a;
        public boolean d;
        public boolean up;
        public boolean down;
        public boolean left;
        public  boolean right;
        public boolean glitchFlag;

        public boolean isActive() {
            return active;
        }

        private boolean active;

        Move() {
            this.w = false;
            this.s = false;
            this.a = false;
            this.d = false;
            this.up = false;
            this.down = false;
            this.left = false;
            this.right = false;
            this.active = false;
            this.glitchFlag = false;
        }

        public void set(boolean w, boolean s, boolean a, boolean d, boolean up, boolean down, boolean left, boolean right){
            this.w = w;
            this.s = s;
            this.a = a;
            this.d = d;
            this.up = up;
            this.down = down;
            this.left = left;
            this.right = right;
            this.active = true;
        }
    }

    Move[] movesArray;
    int pointer;

    public MoveRecorder() {
        movesArray = new Move[FRAMES];
        pointer = 0;
        for(int i = 0; i < FRAMES; i ++) movesArray[i] = new Move();
    }

    public MoveRecorder(MoveRecorder mr) {
        this();
        for(int i = 0; i < FRAMES; i++){
            Move m = mr.get(i);
            this.movesArray[i].set(m.w, m.s, m.a, m.d, m.up, m.down, m.left, m.right);
            this.movesArray[i].active = m.active;
        }
        this.pointer = mr.pointer;
    }

    public Move get(int index){ return movesArray[index]; }

    public int record(boolean w, boolean s, boolean a, boolean d, boolean up, boolean down, boolean left, boolean right){
        if(!this.movesArray[pointer].active) {
            this.movesArray[pointer].set(w, s, a, d, up, down, left, right);
            step();
        }
        else return -1;
        return 0;
    }

    public Move read() throws RuntimeException{
        int safety = -2;
        while(! movesArray[pointer].active){

            safety++; step();
            if(safety > FRAMES) {
                RuntimeException e = new RuntimeException("No Active Moves");
                throw e;
            }
        }
        Move m = movesArray[pointer];
        step();
        return m;
    }

    public void setGlitch(){
        movesArray[pointer].glitchFlag = true;
        movesArray[pointer].active = true;
    }

    private void step(){
        pointer = (pointer+1) % FRAMES;
    }

    public void clear(){
        for(int i = 0; i < FRAMES; i ++) movesArray[i].active = false;
        pointer = 0;
    }

    @Override
    public String toString() {
        String s = "";
        for(int i = 0; i < FRAMES; i ++)
            if (movesArray[i].active) s += "1";
            else s += "0";
        return s;
    }
}
