import javax.inject.Inject;

public class StaticMethod {

    @<error descr="Dagger does not support injection into static methods">Inject</error>
    public static void doSomething() {

    }
}
