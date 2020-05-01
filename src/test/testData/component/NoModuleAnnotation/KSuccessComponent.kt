import dagger.Component
import assets.EmptyModuleOne
import assets.EmptyModuleTwo

@Component(modules = [EmptyModuleOne::class])
interface KComponentWithOneModule {

}

@Component(modules = [EmptyModuleOne::class, EmptyModuleTwo::class])
interface KComponentWithTwoModule {

}