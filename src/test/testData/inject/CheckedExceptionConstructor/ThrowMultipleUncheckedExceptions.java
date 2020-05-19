import javax.inject.Inject;
import assets.Car;

import java.io.IOException;
import java.lang.RuntimeException;

public class ThrowMultipleUncheckedExceptions {

    @Inject
    ThrowMultipleUncheckedExceptions() throws Error, RuntimeException {

    }
}