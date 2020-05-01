import dagger.Component;

@Component
interface ComponentWithOneBuilder {

    @Component.Builder
    interface MyBuilder {
        ComponentWithOneBuilder build();
    }
}