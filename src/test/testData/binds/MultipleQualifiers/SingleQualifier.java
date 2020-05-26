import javax.inject.Inject;
import dagger.Module;
import dagger.Provides;
import dagger.Binds;
import assets.Car;
import assets.CarImpl;
import javax.inject.Singleton;
import javax.inject.Named;
import assets.PrimaryScope;
import assets.PrimaryQualifier;

@Module
public abstract class SingleQualifier {

    @PrimaryQualifier
    @Binds
    public abstract Car getCar(CarImpl car);
}