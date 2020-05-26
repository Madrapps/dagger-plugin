import javax.inject.Inject
import dagger.Module
import dagger.Provides
import dagger.Binds
import assets.Car
import assets.CarImpl
import dagger.Lazy
import dagger.MembersInjector
import javax.inject.Provider

@Module
abstract class KWrappedFrameworkTypes {

    @Binds
    abstract fun lazyWildReturn(car: CarImpl): Set<Lazy<*>>

    @Binds
    abstract fun lazyWrappedReturn(car: CarImpl): Set<Lazy<Car>>

    @Binds
    abstract fun providerWildReturn(car: CarImpl): Set<Provider<*>>

    @Binds
    abstract fun providerWrappedReturn(car: CarImpl): Set<Provider<Car>>

    @Binds
    abstract fun membersInjectorWildReturn(car: CarImpl): Set<MembersInjector<*>>

    @Binds
    abstract fun membersInjectorWrappedReturn(car: CarImpl): Set<MembersInjector<Car>>
}