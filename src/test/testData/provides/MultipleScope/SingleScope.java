import javax.inject.Inject;
import dagger.Module;
import dagger.Provides;
import assets.Car;
import javax.inject.Singleton;
import assets.PrimaryScope;

@Module
public class SingleScope {

    @Singleton
    @Provides
    public Car getCar() {
        return new Car();
    }
}