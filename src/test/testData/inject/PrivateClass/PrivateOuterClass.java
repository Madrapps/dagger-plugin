import javax.inject.Inject;
import assets.Car;

public class PrivateOuterClass {

    private class Private {

        @<error descr="@Inject constructors are invalid on inner classes. Did you mean to make the class static?"><error descr="Dagger does not support injection into private classes">Inject</error></error>
        Private() {

        }

        @<error descr="Dagger does not support injection into private classes">Inject</error>
        public Car car;

        @<error descr="Dagger does not support injection into private classes">Inject</error>
        void doSomething() {

        }
    }
}
