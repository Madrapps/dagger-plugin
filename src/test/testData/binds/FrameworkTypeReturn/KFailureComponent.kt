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
abstract class KFrameworkTypes {

    @<error descr="@Binds methods must not return framework types [Lazy]">Binds</error>
    abstract fun lazyWildReturn(car: CarImpl): Lazy<*>

    @<error descr="@Binds methods must not return framework types [Lazy]">Binds</error>
    abstract fun lazyWrappedReturn(car: CarImpl): Lazy<Car>

    @<error descr="@Binds methods must not return framework types [Provider]">Binds</error>
    abstract fun providerWildReturn(car: CarImpl): Provider<*>

    @<error descr="@Binds methods must not return framework types [Provider]">Binds</error>
    abstract fun providerWrappedReturn(car: CarImpl): Provider<Car>

    @<error descr="@Binds methods must not return framework types [MembersInjector]">Binds</error>
    abstract fun membersInjectorWildReturn(car: CarImpl): MembersInjector<*>

    @<error descr="@Binds methods must not return framework types [MembersInjector]">Binds</error>
    abstract fun membersInjectorWrappedReturn(car: CarImpl): MembersInjector<Car>
}