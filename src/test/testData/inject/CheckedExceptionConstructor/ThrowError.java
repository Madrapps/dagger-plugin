import javax.inject.Inject;
import assets.Car;

import java.io.IOException;
import java.lang.RuntimeException;

public class ThrowError {

    @Inject
    ThrowError() throws Error {

    }
}