import javax.inject.Inject

class KTypeParameterMethod {

    @<error descr="Methods with @Inject may not declare type parameters">Inject</error>
    fun <T> typeParameter1() {

    }

    @<error descr="Methods with @Inject may not declare type parameters">Inject</error>
    fun <T> typeParameter2(t: T) {

    }

    @<error descr="Methods with @Inject may not declare type parameters">Inject</error>
    fun <T, K> typeParameter3(t: T) {

    }
}