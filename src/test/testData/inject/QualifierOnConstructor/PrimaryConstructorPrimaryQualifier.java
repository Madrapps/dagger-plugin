import javax.inject.Inject;
import javax.inject.Named;
import assets.PrimaryQualifier;
import assets.Car;

public class PrimaryConstructorPrimaryQualifier {

    @PrimaryQualifier
    @<error descr="@Qualifier annotations [@PrimaryQualifier] are not allowed on @Inject constructors">Inject</error>
    PrimaryConstructorPrimaryQualifier() {

    }
}
