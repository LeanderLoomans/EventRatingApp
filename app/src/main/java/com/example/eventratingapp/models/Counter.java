package com.example.eventratingapp.models;

/**
 * this class is responsible to increase and decrease a counter value.
 */
public class Counter {

    public int counter;

    public Counter(){}
    /**
     * Construct a new counter class
     * @param counter starting value of the counter.
     */
    public Counter(int counter){
        this.counter = counter;
    }

    /**
     * Increase counter by amount
     * @param amount the amount to increase by
     */
    public void increase(int amount){
        if(isOverFlow(amount)) return;
        this.counter += amount;
    }

    /**
     * Decrease counter by amount
     * @param amount the amount to decrease by
     */
    public void decrease(int amount){
        if(isOverFlow(amount)) return;
        this.counter -= amount;
    }

    /**
     * check if counter value will overflow.
     * @param amount amount to add or substract
     * @return true if the overflow occurs on the current counter
     */
    private boolean isOverFlow(int amount){
       return Integer.MAX_VALUE - amount == this.counter ||
               Integer.MIN_VALUE + amount == this.counter;
    }

    /**
     * return the current amount of the counter
     * @return counter
     */
    public int getCounter() {
        return this.counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
