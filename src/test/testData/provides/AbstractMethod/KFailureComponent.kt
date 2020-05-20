import javax.inject.Inject
import dagger.Module
import dagger.Provides
import assets.Car

@Module
abstract class KAbstractMethod {

    @<error descr="@provides methods cannot be abstract">Provides</error>
    abstract fun getCar() : Car
}