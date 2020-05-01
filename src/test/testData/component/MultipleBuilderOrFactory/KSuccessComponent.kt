import dagger.Component

@Component
interface KComponentWithOneFactory {

    @Component.Factory
    interface MyFactory {
        fun build(): KComponentWithOneFactory
    }
}

@Component
interface KComponentWithOneFactoryFullAnnotation {

    @Component.Factory
    interface MyFactory {
        fun build(): KComponentWithOneFactoryFullAnnotation
    }
}

@Component
interface KComponentWithOneBuilder {

    @Component.Builder
    interface MyBuilder {
        fun build(): KComponentWithOneBuilder
    }
}

@Component
interface KComponentWithOneBuilderFullAnnotation {

    @Component.Builder
    interface MyBuilder {
        fun build(): KComponentWithOneBuilderFullAnnotation
    }
}
