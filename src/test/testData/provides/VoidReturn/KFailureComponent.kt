import javax.inject.Inject
import dagger.Module
import dagger.Provides
import assets.Car

@Module
class KVoidReturn {

    @<error descr="@Provides methods must return a value (not void)">Provides</error>
    fun voidMethod() {

    }
}