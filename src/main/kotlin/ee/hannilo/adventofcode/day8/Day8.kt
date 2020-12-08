package ee.hannilo.adventofcode.day8

import ee.hannilo.adventofcode.StopWatch
import mu.KotlinLogging
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException
import kotlin.collections.ArrayList

/**
 * Compute while avoiding recursion, not intcode 2.0 ready
 * */
class Day8(list: List<String>) {

  private val rom: ArrayList<Operation> = ArrayList(
    list.map { parseOperation(it) }
      .toMutableList()
      .also { logger.debug { "Loaded ${it.size} instructions" } }
  )

  /**
   * Runs the ROM stack
   * */
  fun run(): ComputationResult {
    return run(rom, 0)
  }

  /**
   * Runs the given stack
   * */
  fun run(stack: ArrayList<Operation>, startIdx: Int, prevVisited: Collection<Int> = emptySet()): ComputationResult {
    val timer = StopWatch.start()

    var pointer = startIdx
    var lastPointer = pointer
    var acc = 0
    val visited = mutableSetOf<Int>().apply { if (prevVisited.isNotEmpty()) this.addAll(prevVisited) }

    logger.debug { "Running computation, starting at: $startIdx, visited: $visited" }
    while (pointer < stack.size) {
      val op = stack[pointer]
      logger.trace { "@$pointer ($op): $acc" }
      if (visited.contains(pointer)) {
        logger.debug { "Infinite loop @$pointer ($op), acc=$acc" }
        return ComputationResult(acc, stack[pointer], pointer, nextPointer(pointer, op))
      }
      visited.add(pointer)

      if (op.instruction == Instruction.ACC) {
        acc += op.argument
      }
      lastPointer = pointer
      pointer = nextPointer(pointer, op)
    }

    logger.debug { "Finished with success @$lastPointer (${stack[lastPointer]}), took ${timer.lap()} ms" }
    return ComputationResult(acc, stack[lastPointer], lastPointer, pointer, pointer == rom.size)
  }

  /**
   * Walks the original stack until an instruction is found, which if altered results in a terminating program.
   *
   * Only 1 invalid instruction is allowed.
   * @return Pair(index, new instruction) or *null* if none is found/program already finishes
   * */
  //first solution was to brute force one after another
  //could be solved by mapping the queue to a graph and altering vertices
  //could be altered to keep the accumulator as well
  fun findFrameToAlter(): Pair<Int, Instruction>? {
    if (run().finished) {
      logger.warn { "Tried to alter an already successful program!" }
      return null
    }

    val timer = StopWatch.start()
    var instructionPointer = 0
    var runs = 0
    val visited = mutableSetOf<Int>()

    while (instructionPointer < rom.size) {
      visited.add(instructionPointer)
      val originalOp = rom[instructionPointer]
      if (
        originalOp.instruction == Instruction.ACC
        || originalOp.instruction == Instruction.NOP && originalOp.argument == 0 // would turn to JMP 0, inf loop
        || originalOp.instruction == Instruction.JMP && originalOp.argument == 1 // would turn to NOP 1, no change in behavior
      ) {
        instructionPointer = nextPointer(instructionPointer, originalOp)
        continue
      }

      val newInstruction = when (originalOp.instruction) {
        Instruction.JMP -> Instruction.NOP
        Instruction.NOP -> Instruction.JMP
        else -> throw IllegalStateException("Can't alter ${originalOp.instruction}")
      }
      logger.debug { "Replacing $originalOp with $newInstruction @$instructionPointer" }
      runs++

      //we only need to check the original stack from the frame _after_ the altered frame,
      //passing in all the currently visited frames to identify loops
      val result = run(
        getRom(),
        nextPointer(instructionPointer, Operation(newInstruction, originalOp.argument)),
        visited
      )
      if (result.finished) {
        logger.debug {
          "Found working replacement @$instructionPointer (${originalOp.instruction} -> $newInstruction) " +
              "after $runs runs, took ${timer.lap()} ms"
        }
        return Pair(instructionPointer, newInstruction)
      }

      instructionPointer = nextPointer(instructionPointer, originalOp)
    }

    return null
  }


  private fun nextPointer(pointer: Int, op: Operation): Int {
    return when (op.instruction) {
      Instruction.JMP -> pointer + op.argument
      Instruction.NOP -> pointer + 1
      Instruction.ACC -> pointer + 1
    }
  }

  /**
   * Sets a new instruction at the given index, does not alter the argument
   * */
  fun setRomInstruction(idx: Int, instruction: Instruction) {
    setStackInstruction(rom, idx, instruction)
  }

  fun setStackInstruction(stack: ArrayList<Operation>, idx: Int, instruction: Instruction) {
    stack[idx] = Operation(instruction, rom[idx].argument)
  }

  /**
   * @return A copy of the ROM stack
   * */
  fun getRom() = ArrayList(rom.map { it.copy() })

  companion object {
    private val logger = KotlinLogging.logger {}

    fun parseOperation(str: String): Operation {
      return Operation.OPERATION_REGEX.matchEntire(str).let {
        it?.destructured?.let { (ins, arg) ->
          Operation(Instruction.fromCode(ins), arg.toInt())
        } ?: throw IllegalArgumentException("Failed to parse $str as Operation")
      }
    }
  }
}
