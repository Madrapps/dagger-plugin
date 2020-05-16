import javax.inject.Inject;
import javax.inject.Singleton;
import assets.PrimaryScope;
import assets.Car;

public class PrimaryConstructorPrimaryScope {

    @PrimaryScope
    @<error descr="@Scope annotations [@PrimaryScope] are not allowed on @Inject constructors; annotate the class instead">Inject</error>
    PrimaryConstructorPrimaryScope() {

    }
}
