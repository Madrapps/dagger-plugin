import javax.inject.Inject
import dagger.Module
import dagger.Provides
import assets.Car

@Module
class KModuleClass {

    @Provides
    fun getCar() : Car {
        return Car()
    }
}