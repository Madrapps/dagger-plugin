import javax.inject.Inject
import assets.Car;

class KOnePrimaryOneSecondary @<error descr="Types may only contain one @Inject constructor">Inject</error> constructor() {

    @<error descr="Types may only contain one @Inject constructor">Inject</error>
    constructor(car: Car) : this() {}
}

class KOnePrimaryTwoSecondary @<error descr="Types may only contain one @Inject constructor">Inject</error> constructor() {

    @<error descr="Types may only contain one @Inject constructor">Inject</error>
    constructor(car: Car) : this() {}

    @<error descr="Types may only contain one @Inject constructor">Inject</error>
    constructor(car1: Car, car2: Car) : this() {}
}

class KTwoSecondary {

    @<error descr="Types may only contain one @Inject constructor">Inject</error>
    constructor(car: Car) {}

    @<error descr="Types may only contain one @Inject constructor">Inject</error>
    constructor(car1: Car, car2: Car) {}
}