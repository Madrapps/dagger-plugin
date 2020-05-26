import dagger.Module;
import dagger.Binds;
import assets.Car;
import assets.CarImpl;

@Module
public abstract class ConcreteMethod {

    @<error descr="@Binds methods needs to be abstract">Binds</error>
    public Car getCar(CarImpl car) {
        return null;
    }
}