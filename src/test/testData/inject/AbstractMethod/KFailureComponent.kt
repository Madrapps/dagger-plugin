import javax.inject.Inject

abstract class KAbstractMethod {

    @<error descr="Methods with @Inject may not be abstract">Inject</error>
    abstract fun doSomething()
}