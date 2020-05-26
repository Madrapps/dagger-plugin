import dagger.Module;
import dagger.Binds;
import assets.Car;
import assets.CarImpl;

@Module
public abstract class PrivateMethod {

    @<error descr="@Binds methods cannot be private">Binds</error>
    private Car getCar(CarImpl car) {
        return null;
    }
}