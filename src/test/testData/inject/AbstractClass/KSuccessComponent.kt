import javax.inject.Inject

class KPrimaryConstructorConcreteClass @Inject constructor() {

}

class KSecondaryConstructorConcreteClass {

    @Inject
    constructor()
}