import javax.inject.Inject;

public abstract class AbstractClass {

    @<error descr="@Inject is nonsense on the constructor of an abstract class">Inject</error>
    AbstractClass() {

    }
}
