import javax.inject.Inject
import dagger.Module
import dagger.Provides
import assets.Car

@Module
class KPrivateMethod {

    @<error descr="@Provides methods cannot be private">Provides</error>
    private fun getCar() : Car {
        return Car()
    }
}