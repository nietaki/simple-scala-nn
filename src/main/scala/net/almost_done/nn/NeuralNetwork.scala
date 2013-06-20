package net.almost_done.nn

trait NeuralNetwork {
  
  private val oob: String = "The `teach` and `classify` vectors' arguments should be within bounds of activationFunction's value"
  
  def activationFunction: ActivationFunction
  
  def classifyImpl(input: Seq[Double]): Seq[Double]
  
  def classify(input: Seq[Double]): Seq[Double] = {
    require(input.forall(activationFunction.withinBounds(_)), oob)
    
    classifyImpl(input)
  }
  
  
  def teach(input: Seq[Double], desiredResult: Seq[Double]): Unit = {
    assert(input.forall(activationFunction.withinBounds(_)), oob)
    assert(desiredResult.forall(activationFunction.withinBounds(_)), oob)
    
    teachImpl(input, desiredResult)
  }
  
  def teachImpl(input: Seq[Double], desiredResult: Seq[Double]): Unit
  
  /*
   * Other interfaces in case it's easier to deal with Ints or Bools
   */
  
  def classifyInt(input: Seq[Int]): Seq[Int] = {
    classify(input.map(_.toDouble)).map(activationFunction.customRound(_))
  }
  
  def teachInt(input: Seq[Int], desiredResult: Seq[Int]) = {
    val i2 = input.map(_.toDouble)
    val d2 = desiredResult.map(_.toDouble)
    teach(i2, d2)
  }
}