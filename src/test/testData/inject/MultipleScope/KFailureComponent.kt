import javax.inject.Inject
import javax.inject.Singleton
import assets.PrimaryScope;
import assets.Car

@PrimaryScope
@Singleton
class KMultipleScope @<error descr="A single binding may not declare more than one @Scope [@PrimaryScope, @Singleton]">Inject</error> constructor() {

    @PrimaryScope
    @Singleton
    @Inject
    lateinit var car: Car

    @PrimaryScope
    @Singleton
    @Inject
    fun doSomething() {

    }
}