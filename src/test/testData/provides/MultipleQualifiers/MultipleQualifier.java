import javax.inject.Inject;
import dagger.Module;
import dagger.Provides;
import assets.Car;
import javax.inject.Singleton;
import javax.inject.Named;
import assets.PrimaryScope;
import assets.PrimaryQualifier;

@Module
public class MultipleQualifier {

    @PrimaryQualifier
    @Named
    @<error descr="@Provides methods may not use more than one @Qualifier [@PrimaryQualifier, @Named]">Provides</error>
    public Car getCar() {
        return new Car();
    }
}