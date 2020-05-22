import javax.inject.Inject
import dagger.Module
import dagger.Binds
import assets.Car
import assets.CarImpl

@Module
abstract class KWithTypeParameter {

    @<error descr="@Binds methods may not have type parameters">Binds</error>
    abstract fun <T> getCar(car: CarImpl) : Car
}