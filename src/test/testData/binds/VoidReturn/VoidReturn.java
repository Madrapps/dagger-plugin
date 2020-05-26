import dagger.Module;
import dagger.Binds;
import assets.Car;
import assets.CarImpl;

@Module
public abstract class VoidReturn {

    @<error descr="@Binds methods must return a value (not void)">Binds</error>
    public abstract void getCar(CarImpl car);
}