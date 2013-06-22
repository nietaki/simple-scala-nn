package examples

import scala.collection.immutable.Seq

/**
 * a helper class for generating sequences consisting of zeros and ones, generating all of them, generating
 * random correct examples and verifying their parity
 */
class ZeroOneSequence(length: Int) {
  def nextRandom: Seq[Int] = {
    Range(0, length) map { _ => if(Helpers.assymetricalCoin(0.5)) 1 else 0}
  }
  
  lazy val zeros: Seq[Int] = Range(0, length).map(_ => 0)
  lazy val ones: Seq[Int] = Range(0, length).map(_ => 1)
  
  lazy val combinationCount: Int = 1 << length
  
  def allSequences: Iterator[Seq[Int]] = new ZeroOneSequence.PossibleSequencesIterator(length)
}

object ZeroOneSequence {
  /**
   * 0 - even, 1 - odd
   */
  def checkParity(s: Seq[Int]): Int = {
    assert(s.forall(p => p == 1 || p == 0))
    s.sum % 2 
  }
  
  private class PossibleSequencesIterator(length: Int) extends Iterator[Seq[Int]] {
    private var _lastSeqO: Option[Seq[Int]] = None

    def hasNext: Boolean = _lastSeqO match {
      case None => true
      case Some(s) => {
        ! s.forall(_ == 1)
      }
    } 
    def next =  _lastSeqO match {
      case None => {
        val newLast: Seq[Int] = Range(0, length).map(_ => 0)
        _lastSeqO = Some(newLast)
        newLast
      }
      case Some(s) => {
        var carry = 1; //adding the one
        val newLast = s.map { i =>
          val j = i + carry
          carry = j / 2
          j % 2
        }
        _lastSeqO = Some(newLast)
        newLast
      }
    }
  }
}