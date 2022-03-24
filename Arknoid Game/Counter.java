/**
 * @author Roi Avraham id:318778081.
 * this class count some things.
 */
public class Counter {
    private int counter;
    /**
     * Counter is constructor of this class.
     * @param counter int.
     */
    public Counter(int counter) {
        this.counter = counter;
    }
    /**
     * add number to current count.
     * @param number int.
     */
    public void increase(int number) {
        counter += number;
    }
    /**
     * subtract number from current count.
     * @param number int.
     */
    public void decrease(int number) {
        counter -= number;
    }
    /**
     * get current count.
     * @return counter.
     */
    public int getValue() {
        return counter;
    }
}
