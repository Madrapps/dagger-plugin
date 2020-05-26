import dagger.Module;
import dagger.Binds;
import assets.Car;
import assets.CarImpl;

public abstract class NonModuleClass {

    @<error descr="@Binds methods can only be present within a @Module or @ProducerModule">Binds</error>
    public abstract Car getCar(CarImpl car);
}