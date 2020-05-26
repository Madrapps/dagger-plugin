import javax.inject.Inject
import dagger.Module
import dagger.Provides
import assets.Car
import dagger.Lazy
import dagger.MembersInjector
import javax.inject.Provider

@Module
class KFrameworkTypes {

    @<error descr="@Provides methods must not return framework types [Lazy]">Provides</error>
    fun lazyReturn(): Lazy<*>? {
        return null
    }

    @<error descr="@Provides methods must not return framework types [Lazy]">Provides</error>
    fun lazyWrappedReturn(): Lazy<Car>? {
        return null
    }

    @<error descr="@Provides methods must not return framework types [Provider]">Provides</error>
    fun providerReturn(): Provider<*>? {
        return null
    }

    @<error descr="@Provides methods must not return framework types [Provider]">Provides</error>
    fun providerWrappedReturn(): Provider<Car>? {
        return null
    }

    @<error descr="@Provides methods must not return framework types [MembersInjector]">Provides</error>
    fun membersInjectorReturn(): MembersInjector<*>? {
        return null
    }

    @<error descr="@Provides methods must not return framework types [MembersInjector]">Provides</error>
    fun membersInjectorWrappedReturn(): MembersInjector<Car>? {
        return null
    }
}