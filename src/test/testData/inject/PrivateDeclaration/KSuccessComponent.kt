import javax.inject.Inject
import assets.Car;

class KPrimaryConstructor @Inject constructor() {

    @Inject
    lateinit var kField: Car

    @Inject
    fun kMethod(car: Car) {

    }
}

class KPublicPrimaryConstructor @Inject public constructor() {

    @Inject
    public lateinit var kField: Car

    @Inject
    public fun kMethod(car: Car) {

    }
}

class KProtectedPrimaryConstructor @Inject protected constructor() {

    @Inject
    protected lateinit var kField: Car

    @Inject
    protected fun kMethod(car: Car) {

    }
}

class KInternalPrimaryConstructor @Inject internal constructor() {

    @Inject
    internal lateinit var kField: Car

    @Inject
    internal fun kMethod(car: Car) {

    }
}


class KPublicSecondaryConstructor {

    @Inject
    public constructor()
}

class KSecondaryConstructor {

    @Inject
    constructor()
}

class KProtectedSecondaryConstructor {

    @Inject
    protected constructor()
}

class KInternalSecondaryConstructor {

    @Inject
    internal constructor()
}