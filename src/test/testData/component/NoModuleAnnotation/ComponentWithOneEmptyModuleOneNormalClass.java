import dagger.Component;
import assets.EmptyModuleOne;
import assets.EmptyModuleTwo;
import assets.NormalClassOne;
import assets.NormalClassTwo;

@Component(modules = {EmptyModuleOne.class, <error descr="NormalClassOne is not annotated with @Module">NormalClassOne.class</error>})
public interface ComponentWithOneEmptyModuleOneNormalClass {

}
