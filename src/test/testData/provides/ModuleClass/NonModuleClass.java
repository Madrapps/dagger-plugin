import dagger.Module;
import dagger.Provides;
import assets.Car;

public class NonModuleClass {

    @<error descr="@provides methods can only be present within a @module or @ProducerModule">Provides</error>
    public Car getCar() {
        return new Car();
    }
}