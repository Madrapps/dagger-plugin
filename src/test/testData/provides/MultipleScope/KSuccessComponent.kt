import javax.inject.Inject
import dagger.Module
import dagger.Provides
import assets.Car
import javax.inject.Singleton
import assets.PrimaryScope

@Module
class KSingleScope {

    @Singleton
    @Provides
    fun getCar() : Car {
        return Car()
    }
}