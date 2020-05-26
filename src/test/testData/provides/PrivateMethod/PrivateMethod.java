import dagger.Module;
import dagger.Provides;
import assets.Car;

@Module
public class PrivateMethod {

    @<error descr="@Provides methods cannot be private">Provides</error>
    private Car getCar() {
        return new Car();
    }
}