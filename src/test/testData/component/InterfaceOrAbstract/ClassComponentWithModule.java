import dagger.Component;
import assets.EmptyModuleOne;

@<error descr="@Component may only be applied to an interface or abstract class">Component</error>(modules = {EmptyModuleOne.class})
public class ClassComponentWithModule {

}
