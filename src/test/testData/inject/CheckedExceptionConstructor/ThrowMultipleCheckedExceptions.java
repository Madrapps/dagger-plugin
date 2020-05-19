import javax.inject.Inject;
import assets.Car;

import java.io.IOException;
import java.lang.RuntimeException;
import java.lang.Exception;

public class ThrowMultipleCheckedExceptions {

    @<error descr="Dagger does not support checked exceptions [Exception, IOException] on @Inject constructors">Inject</error>
    ThrowMultipleCheckedExceptions() throws Exception, IOException, RuntimeException {

    }
}