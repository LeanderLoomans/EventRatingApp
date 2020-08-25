package com.example.eventratingapp.models;

/**
 * EventRating POJO
 */
public class EventRating {

    /**
     * amount of green score
     */
    public Counter green;

    /**
     * amount of yellow score
     */
    public Counter yellow;

    /**
     * amount of red score
     */
    public Counter red;

    /**
     * create a new EventRating
     */
    public EventRating(){
        this.green = new Counter(0);
        this.yellow = new Counter(0);
        this.red = new Counter(0);
    }

    /**
     * create a EventRating object with scores
     * @param green amount of green scores
     * @param yellow amount of yellow scores
     * @param red amount of red scores
     */
    public EventRating(int green, int yellow, int red) {
       this.green = new Counter(green);
       this.yellow = new Counter(yellow);
       this.red = new Counter(red);
    }

    /**
     * @return int total amount of green, yellow and red score
     */
    public int _getTotalScore() {
        return this.green.getCounter() +
                this.yellow.getCounter() +
                this.red.getCounter();
    }

}
