
package testing;

public class OperationCounter {

    private long operations;

    public OperationCounter() {
        operations = 0;
    }

    public void increment() {
        operations++;
    }

    public void add(long count) {
        operations += count;
    }

    public long getOperations() {
        return operations;
    }

    public void reset() {
        operations = 0;
    }
}
