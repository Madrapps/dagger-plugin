import javax.inject.Inject;
import dagger.Module;
import dagger.Provides;
import assets.Car;
import dagger.Lazy;
import dagger.MembersInjector;
import javax.inject.Provider;

@Module
public class FrameworkTypes {

    @<error descr="@provides methods must not return framework types [Lazy]">Provides</error>
    public Lazy lazyReturn() {
        return null;
    }

    @<error descr="@provides methods must not return framework types [Lazy]">Provides</error>
    public Lazy<?> lazyWildReturn() {
        return null;
    }

    @<error descr="@provides methods must not return framework types [Lazy]">Provides</error>
    public Lazy<Car> lazyWrappedReturn() {
        return null;
    }

    @<error descr="@provides methods must not return framework types [Provider]">Provides</error>
    public Provider providerReturn() {
        return null;
    }

    @<error descr="@provides methods must not return framework types [Provider]">Provides</error>
    public Provider<?> providerWildReturn() {
        return null;
    }

    @<error descr="@provides methods must not return framework types [Provider]">Provides</error>
    public Provider<Car> providerWrappedReturn() {
        return null;
    }

    @<error descr="@provides methods must not return framework types [MembersInjector]">Provides</error>
    public MembersInjector membersInjectorReturn() {
        return null;
    }

    @<error descr="@provides methods must not return framework types [MembersInjector]">Provides</error>
    public MembersInjector<?> membersInjectorWildReturn() {
        return null;
    }

    @<error descr="@provides methods must not return framework types [MembersInjector]">Provides</error>
    public MembersInjector<Car> membersInjectorWrappedReturn() {
        return null;
    }
}