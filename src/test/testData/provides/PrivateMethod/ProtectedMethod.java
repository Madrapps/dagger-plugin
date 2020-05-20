import dagger.Module;
import dagger.Provides;
import assets.Car;

@Module
public class ProtectedMethod {

    @Provides
    protected Car getCar() {
        return new Car();
    }
}