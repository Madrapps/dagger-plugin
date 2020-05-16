import javax.inject.Inject
import javax.inject.Singleton
import assets.PrimaryScope;
import assets.Car;

class KPrimaryConstructor @Inject constructor() {

}

class KSecondaryConstructor {

    @Inject
    constructor()
}