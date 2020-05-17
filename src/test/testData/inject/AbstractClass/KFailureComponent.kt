import javax.inject.Inject

abstract class KPrimaryConstructorAbstractClass @<error descr="@Inject is nonsense on the constructor of an abstract class">Inject</error> constructor() {

}

abstract class KSecondaryConstructorAbstractClass {

    @<error descr="@Inject is nonsense on the constructor of an abstract class">Inject</error>
    constructor()
}