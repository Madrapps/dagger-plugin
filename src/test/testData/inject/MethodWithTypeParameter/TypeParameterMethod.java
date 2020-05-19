import javax.inject.Inject;

public class TypeParameterMethod {

    @<error descr="Methods with @Inject may not declare type parameters">Inject</error>
    public <T> void typeParameter1() {

    }

    @<error descr="Methods with @Inject may not declare type parameters">Inject</error>
    public <T> void typeParameter2(T t) {

    }

    @<error descr="Methods with @Inject may not declare type parameters">Inject</error>
    public <T, K> void typeParameters3(T t) {

    }
}
