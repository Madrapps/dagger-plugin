import javax.inject.Inject
import dagger.Module
import dagger.Provides
import assets.Car
import javax.inject.Singleton
import javax.inject.Named
import assets.PrimaryScope
import assets.PrimaryQualifier

@Module
class KMultipleQualifier {

    @PrimaryQualifier
    @Named
    @<error descr="@provides methods may not use more than one @qualifier [@PrimaryQualifier, @Named]">Provides</error>
    fun getCar() : Car {
        return Car()
    }
}