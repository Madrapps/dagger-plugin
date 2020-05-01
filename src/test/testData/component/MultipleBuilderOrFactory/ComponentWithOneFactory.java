import dagger.Component;

@Component
interface ComponentWithOneFactory {

    @Component.Factory
    interface MyFactory {
        ComponentWithOneFactory build();
    }
}