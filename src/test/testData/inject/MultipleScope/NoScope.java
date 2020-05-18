import assets.Car;
import assets.PrimaryScope;

import javax.inject.Inject;
import javax.inject.Singleton;

public class NoScope {

    @PrimaryScope
    @Singleton
    @Inject
    public Car car;

    @Inject
    NoScope() {

    }

    @PrimaryScope
    @Singleton
    @Inject
    public void doSomething() {

    }
}
