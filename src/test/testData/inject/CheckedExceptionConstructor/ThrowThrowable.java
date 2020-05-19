import javax.inject.Inject;
import assets.Car;

import java.io.IOException;
import java.lang.RuntimeException;
import java.lang.Exception;

public class ThrowThrowable {

    @<error descr="Dagger does not support checked exceptions [Throwable] on @Inject constructors">Inject</error>
    ThrowThrowable() throws Throwable {
        
    }
}