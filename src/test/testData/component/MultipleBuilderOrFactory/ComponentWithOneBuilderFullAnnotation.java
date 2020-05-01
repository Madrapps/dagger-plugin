import dagger.Component;

@Component
interface ComponentWithOneBuilderFullAnnotation {

    @dagger.Component.Builder
    interface MyBuilder {
        ComponentWithOneBuilderFullAnnotation build();
    }
}