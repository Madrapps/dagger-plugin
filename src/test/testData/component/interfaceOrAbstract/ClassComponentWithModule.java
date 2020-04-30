import dagger.Component;
import assets.EmptyModule;

@<error descr="@Component may only be applied to an interface or abstract class">Component</error>(modules = {EmptyModule.class})
public class ClassComponentWithModule {

}
