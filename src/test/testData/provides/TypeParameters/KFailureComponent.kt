import javax.inject.Inject
import dagger.Module
import dagger.Provides
import assets.Car

@Module
class KWithTypeParameter {

    @<error descr="@provides methods may not have type parameters">Provides</error>
    fun <T> getCar() : Car {
        return Car()
    }
}