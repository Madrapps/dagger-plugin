import dagger.Module;
import dagger.Provides;
import assets.Car;

public class NonModuleClass {

    @<error descr="@Provides methods can only be present within a @Module or @ProducerModule">Provides</error>
    public Car getCar() {
        return new Car();
    }
}