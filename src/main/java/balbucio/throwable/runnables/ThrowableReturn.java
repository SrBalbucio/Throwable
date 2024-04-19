package balbucio.throwable.runnables;

import java.util.Optional;

public interface ThrowableReturn {

    public Optional<Object> run() throws Exception;
}
