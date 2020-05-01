import dagger.Component;
import assets.EmptyModuleOne;
import assets.EmptyModuleTwo;
import assets.NormalClassOne;
import assets.NormalClassTwo;

@Component(modules = {EmptyModuleOne.class, EmptyModuleTwo.class})
public interface ComponentWithTwoModule {

}
