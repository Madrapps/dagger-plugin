import dagger.Module;
import dagger.Binds;
import assets.Car;
import assets.CarImpl;

@Module
public abstract class ProtectedMethod {

    @Binds
    protected abstract Car getCar(CarImpl car);
}