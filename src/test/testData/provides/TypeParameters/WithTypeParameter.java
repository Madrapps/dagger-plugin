import dagger.Module;
import dagger.Provides;
import assets.Car;

@Module
public class WithTypeParameter {

    @<error descr="@provides methods may not have type parameters">Provides</error>
    public <T> Car getCar() {
        return new Car();
    }
}