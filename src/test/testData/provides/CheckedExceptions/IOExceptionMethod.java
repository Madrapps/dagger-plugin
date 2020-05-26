import dagger.Module;
import dagger.Provides;
import assets.Car;
import java.io.IOException;
import java.lang.Exception;
import java.lang.RuntimeException;
import java.lang.Error;

@Module
public class IOExceptionMethod {

    @<error descr="@Provides methods may only throw unchecked exceptions. [IOException] not allowed">Provides</error>
    public Car getCar() throws IOException {
        return new Car();
    }
}