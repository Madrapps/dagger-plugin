import dagger.Module;
import dagger.Provides;
import assets.Car;
import java.io.IOException;
import java.lang.Exception;
import java.lang.RuntimeException;
import java.lang.Error;

@Module
public class MultipleUncheckedMethod {

    @Provides
    public Car getCar() throws RuntimeException, Error {
        return new Car();
    }
}