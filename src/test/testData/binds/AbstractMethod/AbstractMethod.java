import dagger.Module;
import dagger.Binds;
import assets.Car;
import assets.CarImpl;

@Module
public abstract class AbstractMethod {

    @Binds
    public abstract Car getCar(CarImpl car);
}