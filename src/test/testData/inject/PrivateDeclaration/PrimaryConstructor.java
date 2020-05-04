import javax.inject.Inject;
import assets.Car;

public class PrimaryConstructor {

    @Inject
    PrimaryConstructor() {

    }

    @Inject
    Car field;

    @Inject
    void method(Car cargo) {

    }
}
