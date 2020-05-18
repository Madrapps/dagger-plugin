import assets.Car;
import assets.PrimaryScope;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SingleScope {

    @PrimaryScope
    @Singleton
    @Inject
    public Car car;

    @Inject
    SingleScope() {

    }

    @PrimaryScope
    @Singleton
    @Inject
    public void doSomething() {

    }
}
