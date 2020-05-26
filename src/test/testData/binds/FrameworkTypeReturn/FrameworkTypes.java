import javax.inject.Inject;
import dagger.Module;
import dagger.Provides;
import dagger.Binds;
import assets.Car;
import assets.CarImpl;
import dagger.Lazy;
import dagger.MembersInjector;
import javax.inject.Provider;

@Module
public abstract class FrameworkTypes {

    @<error descr="@Binds methods must not return framework types [Lazy]">Binds</error>
    public abstract Lazy lazyReturn(CarImpl car);

    @<error descr="@Binds methods must not return framework types [Lazy]">Binds</error>
    public abstract Lazy<?> lazyWildReturn(CarImpl car);

    @<error descr="@Binds methods must not return framework types [Lazy]">Binds</error>
    public abstract Lazy<Car> lazyWrappedReturn(CarImpl car);

    @<error descr="@Binds methods must not return framework types [Provider]">Binds</error>
    public abstract Provider providerReturn(CarImpl car);

    @<error descr="@Binds methods must not return framework types [Provider]">Binds</error>
    public abstract Provider<?> providerWildReturn(CarImpl car);

    @<error descr="@Binds methods must not return framework types [Provider]">Binds</error>
    public abstract Provider<Car> providerWrappedReturn(CarImpl car);

    @<error descr="@Binds methods must not return framework types [MembersInjector]">Binds</error>
    public abstract MembersInjector membersInjectorReturn(CarImpl car);

    @<error descr="@Binds methods must not return framework types [MembersInjector]">Binds</error>
    public abstract MembersInjector<?> membersInjectorWildReturn(CarImpl car);

    @<error descr="@Binds methods must not return framework types [MembersInjector]">Binds</error>
    public abstract MembersInjector<Car> membersInjectorWrappedReturn(CarImpl car);
}