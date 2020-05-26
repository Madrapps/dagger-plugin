import javax.inject.Inject;
import dagger.Module;
import dagger.Provides;
import assets.Car;
import javax.inject.Singleton;
import assets.PrimaryScope;

@Module
public class MultipleScope {

    @PrimaryScope
    @Singleton
    @<error descr="@Provides methods cannot use more than one @Scope [@PrimaryScope, @Singleton]">Provides</error>
    public Car getCar() {
        return new Car();
    }
}