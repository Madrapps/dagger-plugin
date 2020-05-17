import javax.inject.Inject;
import assets.Car;

public class ClassWithFinalField {

    @<error descr="@Inject fields may not be final">Inject</error>
    public final Car car = new Car();
}
