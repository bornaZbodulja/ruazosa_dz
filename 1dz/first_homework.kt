
class Zadaca() {
    var classArray: Array<Any> = emptyArray()
    //definiramo property tipa array s elementima Any

    init{

    }
    constructor(array: Array<Any>): this() {
        this.classArray = array
        //definiramo sekundardni konstruktor koji prima objekt tipa array
        //i pozivamo primarni konstruktor unutar sekundarnoga
    }

    fun returnMostOften(): Any{
        var countArray = IntArray(classArray.size, { 0 })
        //definiramo array u kojeg cemo spremati koliko se puta pojavio
        //odredeni element unutar classArray property-a same klase

        var i = 0
        for (item in classArray.iterator()){
            countArray[i] = classArray.count { it == item }
            //u countArray spremamo koliko se puta pojavio svaki element
            //unutar classArray-a
            i ++
        }
        val maxOccurrence = countArray.max()
        //trazimo maskimum u countArray-u

        val indexOfMaxElement = countArray.indexOf(maxOccurrence!!)
        //odredujemo indeks elementa koji ima najvecu frekvenciju ponavljanja

        return classArray.get(indexOfMaxElement)
        //vracamo element na tom indeksu

    }

}

fun main(){
    val exeOne : Zadaca = Zadaca(arrayOf(1, 2, 2, 3))
    val exeTwo : Zadaca = Zadaca(arrayOf("One", "One", "One", "Two", "Two"))
    val exeThree : Zadaca = Zadaca(arrayOf(3.14, 3.14, 3.14, 5.6, 6.7, 7.8))
    println(exeOne.returnMostOften()) //provjera za Int Array
    println(exeTwo.returnMostOften()) //provjera za String Array
    println(exeThree.returnMostOften()) //provjera za Float Array

}