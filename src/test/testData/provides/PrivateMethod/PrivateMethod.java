import dagger.Module;
import dagger.Provides;
import assets.Car;

@Module
public class PrivateMethod {

    @<error descr="@provides methods cannot be private">Provides</error>
    private Car getCar() {
        return new Car();
    }
}