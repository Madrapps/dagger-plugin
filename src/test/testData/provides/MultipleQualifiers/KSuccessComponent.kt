import javax.inject.Inject
import dagger.Module
import dagger.Provides
import assets.Car
import javax.inject.Singleton
import javax.inject.Named
import assets.PrimaryScope
import assets.PrimaryQualifier

@Module
class KSingleQualifier {

    @PrimaryQualifier
    @Provides
    fun getCar() : Car {
        return Car()
    }
}