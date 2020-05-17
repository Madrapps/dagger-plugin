import javax.inject.Inject;
import assets.Car;

public class StaticField {

    @<error descr="Dagger does not support injection into static fields">Inject</error>
    public static Car car;
}
