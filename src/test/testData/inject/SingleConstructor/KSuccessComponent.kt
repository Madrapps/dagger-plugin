import javax.inject.Inject
import assets.Car;

class KOnePrimary @Inject constructor() {

}

class KOneSecondary {

    @Inject
    constructor(car: Car) {
    }
}

class KOnePrimarySecondary @Inject constructor() {

    constructor(car: Car) : this() {

    }
}

class KPrimaryTwoSecondary {

    @Inject
    constructor(car: Car) {
    }

    constructor(car1: Car, car2: Car) {}
}

class KOneSecondaryOneSecondary {

    constructor(car: Car) {}

    @Inject
    constructor(car1: Car, car2: Car) {
    }
}