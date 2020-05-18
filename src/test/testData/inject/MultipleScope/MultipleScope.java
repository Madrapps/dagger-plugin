import assets.Car;
import assets.PrimaryScope;

import javax.inject.Inject;
import javax.inject.Singleton;

@PrimaryScope
@Singleton
public class MultipleScope {

    @PrimaryScope
    @Singleton
    @Inject
    public Car car;

    @<error descr="A single binding may not declare more than one @Scope [@PrimaryScope, @Singleton]">Inject</error>
    MultipleScope() {

    }

    @PrimaryScope
    @Singleton
    @Inject
    public void doSomething() {

    }
}
