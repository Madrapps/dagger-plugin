import javax.inject.Inject;
import dagger.Module;
import dagger.Provides;
import dagger.Binds;
import assets.Car;
import assets.CarImpl;
import dagger.Lazy;
import dagger.MembersInjector;
import javax.inject.Provider;
import java.util.Set;

@Module
public abstract class WrappedFrameworkTypes {

    @Binds
    public abstract Set<Lazy> lazyReturn(CarImpl car);

    @Binds
    public abstract Set<Lazy<?>> lazyWildReturn(CarImpl car);

    @Binds
    public abstract Set<Lazy<Car>> lazyWrappedReturn(CarImpl car);

    @Binds
    public abstract Set<Provider> providerReturn(CarImpl car);

    @Binds
    public abstract Set<Provider<?>> providerWildReturn(CarImpl car);

    @Binds
    public abstract Set<Provider<Car>> providerWrappedReturn(CarImpl car);

    @Binds
    public abstract Set<MembersInjector> membersInjectorReturn(CarImpl car);

    @Binds
    public abstract Set<MembersInjector<?>> membersInjectorWildReturn(CarImpl car);

    @Binds
    public abstract Set<MembersInjector<Car>> membersInjectorWrappedReturn(CarImpl car);
}