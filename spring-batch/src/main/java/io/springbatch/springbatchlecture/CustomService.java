package io.springbatch.springbatchlecture;

public class CustomService<T> {

    private int cnt = 0;

    public T customRead() {

        if (cnt == 100) {
            return null;
        }
        return (T) ("item" + cnt++);
    }
}
