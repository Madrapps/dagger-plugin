import javax.inject.Inject;
import assets.Car;

public class PublicPrimaryConstructor {

    @Inject
    public PublicPrimaryConstructor() {

    }

    @Inject
    public Car field;

    @Inject
    public void method(Car cargo) {

    }
}
