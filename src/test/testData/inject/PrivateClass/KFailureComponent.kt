import javax.inject.Inject
import assets.Car

class KOuterClass {

    private class KPrivateClass {

        @<error descr="Dagger does not support injection into private classes">Inject</error>
        constructor() {}

        @<error descr="Dagger does not support injection into private classes">Inject</error>
        lateinit var car: Car

        @<error descr="Dagger does not support injection into private classes">Inject</error>
        fun doSomething() {

        }

    }
}