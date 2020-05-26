import javax.inject.Inject
import dagger.Module
import dagger.Provides
import dagger.Binds
import assets.Car
import assets.CarImpl

@Module
abstract class KVoidReturn {

    @<error descr="@Binds methods must return a value (not void)">Binds</error>
    abstract fun getCar(car: CarImpl)
}