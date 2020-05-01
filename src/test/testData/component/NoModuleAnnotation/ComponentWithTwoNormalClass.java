import dagger.Component;
import assets.EmptyModuleOne;
import assets.EmptyModuleTwo;
import assets.NormalClassOne;
import assets.NormalClassTwo;

@Component(modules = {<error descr="NormalClassOne is not annotated with @Module">NormalClassOne.class</error>, <error descr="NormalClassTwo is not annotated with @Module">NormalClassTwo.class</error>})
public interface ComponentWithTwoNormalClass {

}
