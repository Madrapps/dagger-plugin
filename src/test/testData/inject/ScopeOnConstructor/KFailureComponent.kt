import javax.inject.Inject
import javax.inject.Singleton
import assets.PrimaryScope;
import assets.Car;

class KPrimaryConstructorSingletonScope @Singleton @<error descr="@Scope annotations [@Singleton] are not allowed on @Inject constructors; annotate the class instead">Inject</error> constructor() {

}

class KSecondaryConstructorSingletonScope {

    @Singleton
    @<error descr="@Scope annotations [@Singleton] are not allowed on @Inject constructors; annotate the class instead">Inject</error>
    constructor()
}

class KPrimaryConstructorPrimaryScope @PrimaryScope @<error descr="@Scope annotations [@PrimaryScope] are not allowed on @Inject constructors; annotate the class instead">Inject</error> constructor() {

}

class KSecondaryConstructorPrimaryScope {

    @PrimaryScope
    @<error descr="@Scope annotations [@PrimaryScope] are not allowed on @Inject constructors; annotate the class instead">Inject</error>
    constructor()
}