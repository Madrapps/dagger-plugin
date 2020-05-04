import javax.inject.Inject;
import assets.Car;

public class ProtectedPrimaryConstructor {

    @Inject
    public ProtectedPrimaryConstructor() {

    }

    @Inject
    protected Car field;

    @Inject
    protected void method(Car cargo) {

    }
}
