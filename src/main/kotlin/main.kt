fun main() {
    var f: (Double)->Double = A.Companion::myFunc1
    calc(100.0) { x -> x * x * x }
    val g = Generator()
    g.addIterListener(::nextIter)
    g.addFinishedListener(::finished)
    g.calc()
    g.removeIterListener(::nextIter)
    println("Вторая попытка")
    g.calc()
    println("Третья попытка")
    g.addFinishedListener { println("УРАААА") }
    g.calc()
}

fun nextIter(i: Int, partS: Int){
    println("Завершена $i итерация. Частичная сумма=$partS")
}

fun finished(s: Int){
    println("Результат = $s")
}

class Generator{
    var sum: Int? = null
        private set

    private val iter = mutableListOf<(Int, Int)->Unit>()
    private val fin  = mutableListOf<(Int)->Unit>()

    fun addIterListener(l: (Int, Int)->Unit){
        iter.add(l)
    }

    fun addFinishedListener(l: (Int)->Unit){
        fin.add(l)
    }

    fun removeIterListener(l: (Int, Int)->Unit){
        iter.remove(l)
    }

    fun removeFinishedListener(l: (Int)->Unit){
        fin.remove(l)
    }

    fun calc(){
        var s = 0
        for (i in 1..10){
            s += i
            iter.forEach { it(i, s) }
            //Сделан очередной шаг
        }
        //Вычисления завершены
        sum = s
        fin.forEach { it(s) }
    }
}

class A {
    companion object {
        fun myFunc1(x: Double): Double {
            return x * x
        }

        fun myFunc2(x: Double): Double {
            return x * x * x
        }
    }
    fun myFunc3(x: Double): Double{
        return x*x*x*x
    }
}

fun calc(x: Double, f: (Double)->Double){
    println(f(x))
}
