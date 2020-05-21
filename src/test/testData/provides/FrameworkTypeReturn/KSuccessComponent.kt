import javax.inject.Inject
import dagger.Module
import dagger.Provides
import assets.Car
import dagger.Lazy
import dagger.MembersInjector
import javax.inject.Provider

@Module
class KWrappedFrameworkTypes {

    @Provides
    fun lazySetReturn(): Set<Lazy<*>>? {
        return null
    }

    @Provides
    fun lazyWrappedSetReturn(): Set<Lazy<Car>>? {
        return null
    }

    @Provides
    fun providerSetReturn(): Set<Provider<*>>? {
        return null
    }

    @Provides
    fun providerWrappedSetReturn(): Set<Provider<Car>>? {
        return null
    }

    @Provides
    fun membersInjectorSetReturn(): Set<MembersInjector<*>>? {
        return null
    }

    @Provides
    fun membersInjectorWrappedSetReturn(): Set<MembersInjector<Car>>? {
        return null
    }
}