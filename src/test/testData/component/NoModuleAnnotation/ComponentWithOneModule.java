import dagger.Component;
import assets.EmptyModuleOne;
import assets.EmptyModuleTwo;
import assets.NormalClassOne;
import assets.NormalClassTwo;

@Component(modules = {EmptyModuleOne.class})
public interface ComponentWithOneModule {

}
