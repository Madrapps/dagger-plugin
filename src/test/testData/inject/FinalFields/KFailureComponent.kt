import javax.inject.Inject
import assets.Car

class KClassWithFinalField {

    @<error descr="@Inject fields may not be final"><error descr="Dagger does not support injection into private fields">Inject</error></error>
    val car: Car = Car()
}