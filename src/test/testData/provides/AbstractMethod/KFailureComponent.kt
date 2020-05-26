import javax.inject.Inject
import dagger.Module
import dagger.Provides
import assets.Car

@Module
abstract class KAbstractMethod {

    @<error descr="@Provides methods cannot be abstract">Provides</error>
    abstract fun getCar() : Car
}