import dagger.Module;
import dagger.Provides;
import dagger.Binds;
import assets.Car;
import assets.CarImpl;
import java.io.IOException;
import java.lang.Exception;
import java.lang.RuntimeException;
import java.lang.Error;

@Module
public abstract class MultipleCheckedMethod {

    @<error descr="@Binds methods may only throw unchecked exceptions. [Exception, IOException] not allowed">Binds</error>
    public abstract Car getCar(CarImpl car) throws Exception, Error, IOException;
}