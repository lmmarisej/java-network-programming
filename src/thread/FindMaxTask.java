package thread;

import java.util.concurrent.Callable;

class FindMaxTask implements Callable<Integer> {

    private final int[] data;
    private final int start;
    private final int end;

    FindMaxTask(int[] data, int start, int end) {
        this.data = data;
        this.start = start;
        this.end = end;
    }

    public Integer call() {
        int max = Integer.MIN_VALUE;
        for (int i = start; i < end; i++) {
            if (data[i] > max) max = data[i];
        }
        return max;
    }
}