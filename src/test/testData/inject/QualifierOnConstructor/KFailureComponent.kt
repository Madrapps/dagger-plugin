import javax.inject.Inject
import javax.inject.Named
import assets.PrimaryQualifier;
import assets.Car;

class KPrimaryConstructorNamedQualifier @Named @<error descr="@Qualifier annotations [@Named] are not allowed on @Inject constructors">Inject</error> constructor() {

}

class KSecondaryConstructorNamedQualifier {

    @Named
    @<error descr="@Qualifier annotations [@Named] are not allowed on @Inject constructors">Inject</error>
    constructor()
}

class KPrimaryConstructorPrimaryQualifier @PrimaryQualifier @<error descr="@Qualifier annotations [@PrimaryQualifier] are not allowed on @Inject constructors">Inject</error> constructor() {

}

class KSecondaryConstructorPrimaryQualifier {

    @PrimaryQualifier
    @<error descr="@Qualifier annotations [@PrimaryQualifier] are not allowed on @Inject constructors">Inject</error>
    constructor()
}