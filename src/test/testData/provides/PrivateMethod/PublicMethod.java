import dagger.Module;
import dagger.Provides;
import assets.Car;

@Module
public class PublicMethod {

    @Provides
    public Car getCar() {
        return new Car();
    }
}