// Explore a simple class

println("UW Homework: Simple Kotlin")

// write a "whenFn" that takes an arg of type "Any" and returns a String
fun whenFn(word: Any): String {
  when(word) {
    "Hello" -> return("world")
    0 -> return("zero")
    1 -> return("one")
    in 2..10 -> return("low number")
    is Int -> return("a number")
    is String -> return("Say what?")
  }
  return("I don't understand")
}

// write an "add" function that takes two Ints, returns an Int, and adds the values
fun add(x: Int, y: Int): Int {
  return(x+y)
}

// write a "sub" function that takes two Ints, returns an Int, and subtracts the values
fun sub(x: Int, y: Int): Int {
  return(x-y)
}

// write a "mathOp" function that takes two Ints and a function (that takes two Ints and returns an Int), returns an Int, and applies the passed-in-function to the arguments
fun mathOp(x: Int, y: Int, z: (Int, Int) -> Int): Int {
  return(z(x, y))
}

// write a class "Person" with first name, last name and age
class Person constructor(val firstName: String, val lastName: String, var age: Int) {
  override fun hashCode(): Int {
    return(firstName.hashCode() * lastName.hashCode() + age)
  }
  fun equals(x: Person): Boolean {
    return(this.hashCode() == x.hashCode())
  }
  val debugString: String
    get() = "[Person firstName:${firstName} lastName:${lastName} age:${age}]"
}

// write a class "Money"
class Money(var amount: Int, var currency: String) {
  init {
    if (amount < 0) {
      throw Exception("Error: Amount cannot be less than 0")
    }
    if (currency != "USD" && currency != "EUR" && currency != "CAN" && currency != "GBP") {
      throw Exception("Error: Currency type")
    }
  }
  fun convert(result: String): Money {
    var indicator = 1.0
    when (this.currency) {
      "USD" -> when (result) {
        "USD" -> indicator = 1.0
        "GBP" -> indicator = 0.5
        "EUR" -> indicator = 1.5
        "CAN" -> indicator = 1.25
      }
      "GBP" -> when (result) {
        "USD" -> indicator = 2.0
        "GBP" -> indicator = 1.0
        "EUR" -> indicator = 3.0
        "CAN" -> indicator = 2.5
      }
      "EUR" -> when (result) {
        "EUR" -> indicator = 1.0
        "USD" -> indicator = 0.666
        "GBP" -> indicator = 0.333
        "CAN" -> indicator = 0.8333
      }
      "CAN" -> when (result) {
        "USD" -> indicator = 0.8
        "GBP" -> indicator = 0.4
        "EUR" -> indicator = 1.2
        "CAN" -> indicator = 1.0
      }
    }
    var temp = this.amount * indicator
    return Money(temp.toInt(), result)
  }
  operator fun plus(x: Money) :Money {
    var temp = x.convert(this.currency)
    return(Money(temp.amount+this.amount, this.currency))
  }
}

// ============ DO NOT EDIT BELOW THIS LINE =============

print("When tests: ")
val when_tests = listOf(
    "Hello" to "world",
    "Howdy" to "Say what?",
    "Bonjour" to "Say what?",
    0 to "zero",
    1 to "one",
    5 to "low number",
    9 to "low number",
    17.0 to "I don't understand"
)
for ((k,v) in when_tests) {
    print(if (whenFn(k) == v) "." else "!")
}
println("")

print("Add tests: ")
val add_tests = listOf(
    Pair(0, 0) to 0,
    Pair(1, 2) to 3,
    Pair(-2, 2) to 0,
    Pair(123, 456) to 579
)
for ( (k,v) in add_tests) {
    print(if (add(k.first, k.second) == v) "." else "!")
}
println("")

print("Sub tests: ")
val sub_tests = listOf(
    Pair(0, 0) to 0,
    Pair(2, 1) to 1,
    Pair(-2, 2) to -4,
    Pair(456, 123) to 333
)
for ( (k,v) in sub_tests) {
    print(if (sub(k.first, k.second) == v) "." else "!")
}
println("")

print("Op tests: ")
print(if (mathOp(2, 2, { l,r -> l+r} ) == 4) "." else "!")
print(if (mathOp(2, 2, ::add ) == 4) "." else "!")
print(if (mathOp(2, 2, ::sub ) == 0) "." else "!")
print(if (mathOp(2, 2, { l,r -> l*r} ) == 4) "." else "!")
println("")


print("Person tests: ")
val p1 = Person("Ted", "Neward", 47)
print(if (p1.firstName == "Ted") "." else "!")
p1.age = 48
print(if (p1.debugString == "[Person firstName:Ted lastName:Neward age:48]") "." else "!")
println("")

print("Money tests: ")
val tenUSD = Money(10, "USD")
val twelveUSD = Money(12, "USD")
val fiveGBP = Money(5, "GBP")
val fifteenEUR = Money(15, "EUR")
val fifteenCAN = Money(15, "CAN")
val convert_tests = listOf(
    Pair(tenUSD, tenUSD),
    Pair(tenUSD, fiveGBP),
    Pair(tenUSD, fifteenEUR),
    Pair(twelveUSD, fifteenCAN),
    Pair(fiveGBP, tenUSD),
    Pair(fiveGBP, fifteenEUR)
)
for ( (from,to) in convert_tests) {
    print(if (from.convert(to.currency).amount == to.amount) "." else "!")
}
val moneyadd_tests = listOf(
    Pair(tenUSD, tenUSD) to Money(20, "USD"),
    Pair(tenUSD, fiveGBP) to Money(20, "USD"),
    Pair(fiveGBP, tenUSD) to Money(10, "GBP")
)
for ( (pair, result) in moneyadd_tests) {
    print(if ((pair.first + pair.second).amount == result.amount &&
              (pair.first + pair.second).currency == result.currency) "." else "!")
}
println("")
