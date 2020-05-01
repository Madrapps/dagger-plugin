import dagger.Component;

@<error descr="@Component has more than one @Component.Builder or @Component.Factory [FactoryOne, FactoryTwo]">Component</error>
interface ComponentWithTwoFactory {

    @Component.Factory
    interface FactoryOne {
        ComponentWithTwoFactory build();
    }

    @dagger.Component.Factory
    interface FactoryTwo {
        ComponentWithTwoFactory build();
    }
}