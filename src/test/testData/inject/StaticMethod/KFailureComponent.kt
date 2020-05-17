import javax.inject.Inject

class KCompanionMethod {

    companion object {
        @<error descr="Dagger does not support injection into static methods">Inject</error>
        fun staticCompanionMethod() {

        }
    }
}

object KObjectMethod {

    @<error descr="Dagger does not support injection into static methods">Inject</error>
    fun staticObjectMethod() {

    }
}