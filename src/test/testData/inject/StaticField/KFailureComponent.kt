import javax.inject.Inject
import assets.Car

class KCompanionField {

    companion object {
        @<error descr="Dagger does not support injection into static fields">Inject</error>
        lateinit var car: Car
    }
}

object KObjectField {

    @<error descr="Dagger does not support injection into static fields">Inject</error>
    lateinit var car: Car
}