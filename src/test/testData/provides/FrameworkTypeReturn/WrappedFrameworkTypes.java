import javax.inject.Inject;
import dagger.Module;
import dagger.Provides;
import assets.Car;
import dagger.Lazy;
import dagger.MembersInjector;
import javax.inject.Provider;
import java.util.Set;

@Module
public class WrappedFrameworkTypes {

    @Provides
    public Set<Lazy> lazyReturn() {
        return null;
    }

    @Provides
    public Set<Lazy<?>> lazyWildReturn() {
        return null;
    }

    @Provides
    public Set<Lazy<Car>> lazyWrappedReturn() {
        return null;
    }

    @Provides
    public Set<Provider> providerReturn() {
        return null;
    }

    @Provides
    public Set<Provider<?>> providerWildReturn() {
        return null;
    }

    @Provides
    public Set<Provider<Car>> providerWrappedReturn() {
        return null;
    }

    @Provides
    public Set<MembersInjector> membersInjectorReturn() {
        return null;
    }

    @Provides
    public Set<MembersInjector<?>> membersInjectorWildReturn() {
        return null;
    }

    @Provides
    public Set<MembersInjector<Car>> membersInjectorWrappedReturn() {
        return null;
    }
}