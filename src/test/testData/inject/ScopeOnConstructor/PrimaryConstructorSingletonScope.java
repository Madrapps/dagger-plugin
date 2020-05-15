import javax.inject.Inject;
import javax.inject.Singleton;
import assets.PrimaryScope;
import assets.Car;

public class PrimaryConstructorSingletonScope {

    @Singleton
    @<error descr="@Scope annotations [@Singleton] are not allowed on @Inject constructors; annotate the class instead">Inject</error>
    PrimaryConstructorSingletonScope() {

    }
}
