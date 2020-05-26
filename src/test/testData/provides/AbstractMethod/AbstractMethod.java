import dagger.Module;
import dagger.Provides;
import assets.Car;

@Module
public abstract class AbstractMethod {

    @<error descr="@Provides methods cannot be abstract">Provides</error>
    public abstract Car getCar();
}