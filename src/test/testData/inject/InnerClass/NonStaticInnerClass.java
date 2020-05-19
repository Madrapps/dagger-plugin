import javax.inject.Inject;

public class NonStaticInnerClass {

    public class InnerClass {

        @<error descr="@Inject constructors are invalid on inner classes. Did you mean to make the class static?">Inject</error>
        InnerClass() {

        }
    }
}
