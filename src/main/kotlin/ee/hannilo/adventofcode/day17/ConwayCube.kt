package ee.hannilo.adventofcode.day17

interface ConwayCube {
  val key: String
  val neighborRanges: List<IntRange>
  val neighbors: List<ConwayCube>
}

data class ConwayCube3D(
  val x: Int,
  val y: Int,
  val z: Int
) : ConwayCube {

  constructor(key: String) : this(key.split(",")[0].toInt(), key.split(",")[1].toInt(), key.split(",")[2].toInt())

  override val key = "$x,$y,$z"
  override val neighborRanges = listOf(x - 1..x + 1, y - 1..y + 1, z - 1..z + 1)
  override val neighbors: List<ConwayCube3D> by lazy {
    neighborRanges[0].flatMap { dx ->
      neighborRanges[1].flatMap { dy ->
        neighborRanges[2].mapNotNull { dz ->
          ConwayCube3D(dx, dy, dz).takeUnless { it.key == this.key }
        }
      }
    }
  }
}

data class ConwayCube4D(
  val x: Int,
  val y: Int,
  val z: Int,
  val w: Int,
) : ConwayCube {

  constructor(key: String) : this(
    key.split(",")[0].toInt(),
    key.split(",")[1].toInt(),
    key.split(",")[2].toInt(),
    key.split(",")[3].toInt()
  )

  override val key = "$x,$y,$z,$w"
  override val neighborRanges = listOf(x - 1..x + 1, y - 1..y + 1, z - 1..z + 1, w - 1..w + 1)
  override val neighbors: List<ConwayCube4D> by lazy {
    neighborRanges[0].flatMap { dx ->
      neighborRanges[1].flatMap { dy ->
        neighborRanges[2].flatMap { dz ->
          neighborRanges[3].mapNotNull { dw ->
            ConwayCube4D(dx, dy, dz, dw).takeUnless { it.key == this.key }
          }
        }
      }
    }
  }
}
