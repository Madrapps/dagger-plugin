import javax.inject.Inject
import assets.Car;

class KPrimaryConstructor @<error descr="Dagger does not support injection into private constructors">Inject</error> private constructor() {

    @<error descr="Dagger does not support injection into private fields">Inject</error>
    private lateinit var kField: Car

    @<error descr="Dagger does not support injection into private methods">Inject</error>
    private fun kMethod(car: Car) {

    }
}

class KSecondaryConstructor {

    @<error descr="Dagger does not support injection into private constructors">Inject</error>
    private constructor()
}