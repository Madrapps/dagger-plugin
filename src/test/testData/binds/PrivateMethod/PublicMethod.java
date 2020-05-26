import dagger.Module;
import dagger.Binds;
import assets.Car;
import assets.CarImpl;

@Module
public abstract class PublicMethod {

    @Binds
    public abstract Car getCar(CarImpl car);
}