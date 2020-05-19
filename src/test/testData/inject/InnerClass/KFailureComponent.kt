import javax.inject.Inject

class KInnerClassInsideClass {

    inner class KInnerClass @<error descr="@Inject constructors are invalid on inner classes. Did you mean to make the class static?">Inject</error> constructor() {

    }
}