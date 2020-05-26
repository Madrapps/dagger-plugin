import javax.inject.Inject;
import dagger.Module;
import dagger.Provides;
import dagger.Binds;
import assets.Car;
import assets.CarImpl;
import javax.inject.Singleton;
import assets.PrimaryScope;

@Module
public abstract class MultipleScope {

    @PrimaryScope
    @Singleton
    @<error descr="@Binds methods cannot use more than one @Scope [@PrimaryScope, @Singleton]">Binds</error>
    public abstract Car getCar(CarImpl car);
}