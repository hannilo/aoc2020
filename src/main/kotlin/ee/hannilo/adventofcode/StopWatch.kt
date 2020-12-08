package ee.hannilo.adventofcode

class StopWatch(private val start: Long) {

  fun lap(): Long {
    return System.currentTimeMillis() - start
  }

  companion object {
    fun start(): StopWatch {
      return StopWatch(System.currentTimeMillis())
    }
  }
}
