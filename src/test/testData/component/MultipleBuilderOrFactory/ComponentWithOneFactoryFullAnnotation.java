import dagger.Component;

@Component
interface ComponentWithOneFactoryFullAnnotation {

    @dagger.Component.Factory
    interface MyFactory {
        ComponentWithOneFactoryFullAnnotation build();
    }
}