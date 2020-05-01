import dagger.Component

@<error descr="@Component has more than one @Component.Builder or @Component.Factory [FactoryOne, FactoryTwo]">Component</error>
interface KComponentWithTwoFactory {

    @Component.Factory
    interface FactoryOne {
        fun build(): KComponentWithTwoFactory
    }

    @dagger.Component.Factory
    interface FactoryTwo {
        fun build(): KComponentWithTwoFactory
    }
}

@<error descr="@Component has more than one @Component.Builder or @Component.Factory [BuilderOne, BuilderTwo]">Component</error>
interface KComponentWithTwoBuilder {

    @Component.Builder
    interface BuilderOne {
        fun build(): KComponentWithTwoBuilder
    }

    @dagger.Component.Builder
    interface BuilderTwo {
        fun build(): KComponentWithTwoBuilder
    }
}

@<error descr="@Component has more than one @Component.Builder or @Component.Factory [FactoryOne, BuilderOne]">Component</error>
interface KComponentWithOneFactoryOneBuilder {

    @Component.Factory
    interface FactoryOne {
        fun build(): KComponentWithOneFactoryOneBuilder
    }

    @dagger.Component.Builder
    interface BuilderOne {
        fun build(): KComponentWithOneFactoryOneBuilder
    }
}
