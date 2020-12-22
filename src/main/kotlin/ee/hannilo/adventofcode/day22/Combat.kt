package ee.hannilo.adventofcode.day22

import java.lang.IllegalStateException
import java.util.*

/**
 * This could be made faster by stripping down the class usage and using primitives/enums.
 *
 * Part 2 takes ~3s so NBD.
 * */
class Combat(val printer: (String?) -> Unit = ::println) {

  private val seenRecursions = mutableMapOf<Int, CombatHand>()
  private var recursedGames = 2
  /** pretty numbers */
  private var shortcutsTaken = 0

  fun play(p1: CombatHand, p2: CombatHand): CombatHand {

    val q1 = LinkedList(p1.hand)
    val q2 = LinkedList(p2.hand)

    var round = 1

    while (q1.isNotEmpty() && q2.isNotEmpty()) {
      printer("-- Round $round --")
      printer("${p1.player}'s deck: $q1")
      printer("${p2.player}'s deck: $q2")
      val card1 = q1.poll()
      val card2 = q2.poll()
      printer("${p1.player} plays: $card1")
      printer("${p2.player} plays: $card2")

      val winner =
        if (card1 > card2) Triple(p1.player, q1, listOf(card1, card2)) else Triple(p2.player, q2, listOf(card2, card1))
      winner.second.addAll(winner.third)
      printer("${winner.first} wins the round!")
      printer("")
      round++
    }

    val winner = if (q1.size > q2.size) p1.player else p2.player
    val winnerHand = if (q1.size > q2.size) q1 else q2
    printer("== Post-game results==")
    printer("${p1.player}'s deck: $q1")
    printer("${p2.player}'s deck: $q2")
    return CombatHand(winner, winnerHand)
  }

  fun playRecursive(p1: CombatHand, p2: CombatHand, game: Int = 1, fromGame: Int = 1): CombatHand {
    printer("=== Game $game ===\n")

    val hash = Objects.hash(p1, p2)
    val hashReverse = Objects.hash(p2, p1)

    //could also check max card and deck size for subgames and skip almost everything
    listOf(hash, hashReverse).forEach { h ->
      seenRecursions[h]?.let {
        printer("This game or its reverse has already been played before... ${it.player} wins.")
        shortcutsTaken++
        return it
      }
    }

    val handSet = HashSet<String>()

    val q1 = LinkedList(p1.hand)
    val q2 = LinkedList(p2.hand)

    var round = 1

    while (q1.isNotEmpty() && q2.isNotEmpty()) {
      if (round != 1) printer("")
      printer("-- Round $round (Game $game) --")
      printer("${p1.player}'s deck: ${q1.joinToString(", ")}")
      printer("${p2.player}'s deck: ${q2.joinToString(", ")}")

      val roundId = "$q1+$q2"
      if (handSet.contains(roundId)) {
        printer("The automatic winner of game $game is player ${p1.player}!")
        return CombatHand(p1.player, q1)
      } else {
        handSet.add(roundId)
      }

      val card1 = q1.poll()
      val card2 = q2.poll()
      printer("${p1.player} plays: $card1")
      printer("${p2.player} plays: $card2")

      val winningHand = if (q1.size >= card1 && q2.size >= card2) {
        printer("Playing a sub-game to determine the winner...\n")
        //the quantity of cards copied is equal to the number on the card they drew to trigger the sub-game
        playRecursive(
          CombatHand(p1.player, q1.take(card1)),
          CombatHand(p2.player, q2.take(card2)),
          recursedGames++,
          game
        )
      } else {
        if (card1 > card2) p1 else p2
      }

      val roundWinner = when (winningHand.player) {
        p1.player -> Triple(p1.player, q1, listOf(card1, card2))
        p2.player -> Triple(p2.player, q2, listOf(card2, card1))
        else -> throw IllegalStateException("Invalid player ${winningHand.player}")
      }

      roundWinner.second.addAll(roundWinner.third)
      printer("${roundWinner.first} wins round $round of game $game!")
      round++
    }

    val winner :CombatHand
    val loser :CombatHand
    if (q1.size > q2.size) {
      winner = CombatHand(p1.player, q1)
      loser = CombatHand(p2.player, q2)
    } else {
      loser = CombatHand(p1.player, q1)
      winner = CombatHand(p2.player, q2)
    }


    if (game == 1) {
      printer("== Post-game results ==")
      printer("Shortcuts taken: $shortcutsTaken")
      printer("${p1.player}'s deck: $q1")
      printer("${p2.player}'s deck: $q2")
    } else {
      printer("The winner of game $game is ${winner.player}!\n")
      printer("...anyway, back to game $fromGame.")
      seenRecursions[hash] = winner
      seenRecursions[hashReverse] = loser
    }
    return winner
  }

  fun calculateScore(hand: CombatHand): Int {
    return hand.hand.reversed().reduceIndexed { index, acc, i ->
      acc + (index + 1) * i
    }
  }
}
