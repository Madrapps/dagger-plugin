import javax.inject.Inject;
import javax.inject.Named;
import assets.PrimaryQualifier;
import assets.Car;

public class PrimaryConstructorNamedQualifier {

    @Named
    @<error descr="@Qualifier annotations [@Named] are not allowed on @Inject constructors">Inject</error>
    PrimaryConstructorNamedQualifier() {

    }
}
