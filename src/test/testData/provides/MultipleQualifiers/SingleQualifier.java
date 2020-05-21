import javax.inject.Inject;
import dagger.Module;
import dagger.Provides;
import assets.Car;
import javax.inject.Singleton;
import javax.inject.Named;
import assets.PrimaryScope;
import assets.PrimaryQualifier;

@Module
public class SingleQualifier {

    @PrimaryQualifier
    @Provides
    public Car getCar() {
        return new Car();
    }
}