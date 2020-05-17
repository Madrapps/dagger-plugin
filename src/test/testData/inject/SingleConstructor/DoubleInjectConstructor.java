import javax.inject.Inject;
import assets.Car;

public class DoubleInjectConstructor {

    @<error descr="Types may only contain one @Inject constructor">Inject</error>
    DoubleInjectConstructor() {

    }

    @<error descr="Types may only contain one @Inject constructor">Inject</error>
    DoubleInjectConstructor(Car car) {
        
    }
}
