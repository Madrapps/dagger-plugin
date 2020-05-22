import dagger.Module;
import dagger.Binds;
import assets.Car;
import assets.CarImpl;

@Module
public abstract class WithTypeParameter {

    @<error descr="@Binds methods may not have type parameters">Binds</error>
    public abstract <T> Car getCar(CarImpl car);
}