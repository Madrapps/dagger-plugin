import javax.inject.Inject;

public abstract class AbstractMethod {

    @<error descr="Methods with @Inject may not be abstract">Inject</error>
    public abstract void doSomething();
}
