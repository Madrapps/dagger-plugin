import dagger.Component
import assets.EmptyModuleOne

@<error descr="@Component may only be applied to an interface or abstract class">Component</error>
class KClassComponent {

}

@<error descr="@Component may only be applied to an interface or abstract class">Component</error>(modules = [EmptyModuleOne::class])
class KClassComponentWithModule {

}

@<error descr="@Component may only be applied to an interface or abstract class">dagger.Component</error>
class KClassComponentFullAnnotation {

}

@<error descr="@Component may only be applied to an interface or abstract class">dagger.Component</error>(modules = [EmptyModuleOne::class])
class KClassComponentWithModuleFullAnnotation {

}