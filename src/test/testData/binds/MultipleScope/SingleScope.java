import javax.inject.Inject;
import dagger.Module;
import dagger.Provides;
import dagger.Binds;
import assets.Car;
import assets.CarImpl;
import javax.inject.Singleton;
import assets.PrimaryScope;

@Module
public abstract class SingleScope {

    @Singleton
    @Binds
    public abstract Car getCar(CarImpl car);
}