import dagger.Module;
import dagger.Provides;

@Module
public class VoidReturn {

    @<error descr="@Provides methods must return a value (not void)">Provides</error>
    public void voidMethod() {

    }
}