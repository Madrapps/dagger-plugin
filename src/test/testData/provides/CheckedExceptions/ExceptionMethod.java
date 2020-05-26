import dagger.Module;
import dagger.Provides;
import assets.Car;
import java.io.IOException;
import java.lang.Exception;
import java.lang.RuntimeException;
import java.lang.Error;

@Module
public class ExceptionMethod {

    @<error descr="@Provides methods may only throw unchecked exceptions. [Exception] not allowed">Provides</error>
    public Car getCar() throws Exception {
        return new Car();
    }
}