import javax.inject.Inject;
import assets.Car;

public class SingleInjectConstructor {

    @Inject
    SingleInjectConstructor() {

    }

    SingleInjectConstructor(Car car) {

    }
}
