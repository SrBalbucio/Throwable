package balbucio.throwable.runnables;

import java.util.Optional;

public interface ThrowableReturn<T> {

    public T get() throws Exception;
}
