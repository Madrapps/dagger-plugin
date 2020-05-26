import javax.inject.Inject
import dagger.Module
import dagger.Provides
import dagger.Binds
import assets.Car
import assets.CarImpl
import javax.inject.Singleton
import assets.PrimaryScope

@Module
abstract class KSingleScope {

    @Singleton
    @Binds
    abstract fun getCar(car: CarImpl): Car
}