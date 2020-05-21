import javax.inject.Inject
import dagger.Module
import dagger.Provides
import assets.Car

@Module
class KVoidReturn {

    @<error descr="@provides methods must return a value (not void)">Provides</error>
    fun voidMethod() {

    }
}