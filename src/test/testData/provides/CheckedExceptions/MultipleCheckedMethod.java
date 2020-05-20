import dagger.Module;
import dagger.Provides;
import assets.Car;
import java.io.IOException;
import java.lang.Exception;
import java.lang.RuntimeException;
import java.lang.Error;

@Module
public class MultipleCheckedMethod {

    @<error descr="@provides methods may only throw unchecked exceptions. [Exception, IOException] not allowed">Provides</error>
    public Car getCar() throws Exception, Error, IOException {
        return new Car();
    }
}