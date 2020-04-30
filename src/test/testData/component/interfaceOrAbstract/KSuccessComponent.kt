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