import javax.inject.Inject;
import assets.Car;

public class NotPrivateClass {

    class NonPrivate {

        @<error descr="@Inject constructors are invalid on inner classes. Did you mean to make the class static?">Inject</error>
        NonPrivate() {

        }

        @Inject
        public Car car;

        @Inject
        void doSomething() {

        }
    }
}
