import javax.inject.Inject;
import assets.Car;

public class PrivatePrimaryConstructor {

    @<error descr="Dagger does not support injection into private constructors">Inject</error>
    private PrivatePrimaryConstructor() {

    }

    @<error descr="Dagger does not support injection into private fields">Inject</error>
    private Car field;

    @<error descr="Dagger does not support injection into private methods">Inject</error>
    private void method(Car cargo) {

    }
}
