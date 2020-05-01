import dagger.Component
import assets.EmptyModuleOne

@Component
abstract class KAbstractClassComponent {

}

@Component(modules = [EmptyModuleOne::class])
abstract class KAbstractClassComponentWithModule {

}

@Component
interface KInterfaceComponent {

}

@Component(modules = [EmptyModuleOne::class])
interface KInterfaceComponentWithModule {

}

@dagger.Component
abstract class KAbstractClassComponentFullAnnotation {

}

@dagger.Component(modules = [EmptyModuleOne::class])
abstract class KAbstractClassComponentWithModuleFullAnnotation {

}

@dagger.Component
interface KInterfaceComponentFullAnnotation {

}

@dagger.Component(modules = [EmptyModuleOne::class])
interface KInterfaceComponentWithModuleFullAnnotation {

}