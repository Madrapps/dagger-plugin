import javax.inject.Inject
import dagger.Module
import dagger.Binds
import assets.Car
import assets.CarImpl


@Module
abstract class KConcreteMethod {

    @<error descr="@Binds methods needs to be abstract">Binds</error>
    fun getCar(car: CarImpl) : Car? {
        return null
    }
}