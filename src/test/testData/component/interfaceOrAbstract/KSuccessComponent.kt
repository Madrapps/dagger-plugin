import dagger.Component
import assets.EmptyModule

@Component
abstract class KAbstractClassComponent {

}

@Component(modules = [EmptyModule::class])
abstract class KAbstractClassComponentWithModule {

}

@Component
interface KInterfaceComponent {

}

@Component(modules = [EmptyModule::class])
interface KInterfaceComponentWithModule {

}

@dagger.Component
abstract class KAbstractClassComponentFullAnnotation {

}

@dagger.Component(modules = [EmptyModule::class])
abstract class KAbstractClassComponentWithModuleFullAnnotation {

}

@dagger.Component
interface KInterfaceComponentFullAnnotation {

}

@dagger.Component(modules = [EmptyModule::class])
interface KInterfaceComponentWithModuleFullAnnotation {

}