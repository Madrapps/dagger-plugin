import dagger.Component;

@<error descr="@Component has more than one @Component.Builder or @Component.Factory [FactoryOne, BuilderOne]">Component</error>
interface ComponentWithOneFactoryOneBuilder {

    @Component.Factory
    interface FactoryOne {
        ComponentWithOneFactoryOneBuilder build();
    }

    @dagger.Component.Builder
    interface BuilderOne {
        ComponentWithOneFactoryOneBuilder build();
    }
}