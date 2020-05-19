import javax.inject.Inject;
import assets.Car;

import java.io.IOException;
import java.lang.RuntimeException;

public class ThrowRuntimeException {

    @Inject
    ThrowRuntimeException() throws RuntimeException {
        
    }
}