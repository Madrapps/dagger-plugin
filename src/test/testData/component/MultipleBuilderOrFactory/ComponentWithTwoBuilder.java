import dagger.Component;

@<error descr="@Component has more than one @Component.Builder or @Component.Factory [BuilderOne, BuilderTwo]">Component</error>
interface ComponentWithTwoBuilder {

    @Component.Builder
    interface BuilderOne {
        ComponentWithTwoBuilder build();
    }

    @dagger.Component.Builder
    interface BuilderTwo {
        ComponentWithTwoBuilder build();
    }
}