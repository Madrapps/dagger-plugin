import javax.inject.Inject
import dagger.Module
import dagger.Provides
import dagger.Binds
import assets.Car
import assets.CarImpl
import javax.inject.Singleton
import assets.PrimaryScope

@Module
abstract class KMultipleScope {

    @PrimaryScope
    @Singleton
    @<error descr="@Binds methods cannot use more than one @scope [@PrimaryScope, @Singleton]">Binds</error>
    abstract fun getCar(car: CarImpl): Car
}