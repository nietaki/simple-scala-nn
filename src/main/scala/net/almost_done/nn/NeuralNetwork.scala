package net.almost_done.nn


/**
 * a common trait for all neural networks. All inheriting classes need to implement [[net.almost_done.nn!teachImpl]] and `classifyImpl` methods
 * and expose their activation function via `activationFunction`
 */
trait NeuralNetwork {
  
  private val oob: String = "The `teach` and `classify` vectors' arguments' values should be within bounds of activationFunction"
  
  def activationFunction: ActivationFunction
  
  /**
   * the classification function to be implemented by subclasses
   */
  protected def classifyImpl(input: Seq[Double]): Seq[Double]
  
  /**
   * wraps the `classifyImpl` method with bounds check.
   * 
   * @param input a sequence of Doubles - the vector being classified. Note: This method will make sure the values are within the bounds 
   * defined by the `activationFunction`
   * 
   * @returns the sequence - vector of Doubles the input was classified as. The result is not rounded and to get the class the 
   * example has been classified to you have to adequately round the values, eg. using [[net.almost_done.nn.ActivationFunction]]'s methods
   */
  def classify(input: Seq[Double]): Seq[Double] = {
    require(input.forall(activationFunction.withinBounds(_)), oob)
    
    classifyImpl(input)
  }
  
  /**
   * @returns maximum correction made in the last training of the neural network. This can be used for termination criteria for training the 
   * network. 
   */
  def getMaxDelta(): Double
  
  
   /**
   * wraps the `teachImpl` method with bounds check.
   * 
   * @param input a sequence of Doubles - the vector being classified. Note: This method will make sure the values are within the bounds 
   * defined by the `activationFunction`
   * 
   * @param desiredResult the sequence - vector of Doubles the input should be classified as. This parameter will also be verfied for being within the bounds
   * defined by `activationFunction` 
   */
  def train(input: Seq[Double], desiredResult: Seq[Double]): Unit = {
    assert(input.forall(activationFunction.withinBounds(_)), oob)
    assert(desiredResult.forall(activationFunction.withinBounds(_)), oob)
    
    trainImpl(input, desiredResult)
  }
  
  /**
   * the teaching function to be implemented by subclasses. If in the course of teaching you need to invoke the classification
   * method, use `teach` not `teachImpl` for added arguments checks
   */
  protected def trainImpl(input: Seq[Double], desiredResult: Seq[Double]): Unit
  
  /*
   * Other interfaces in case it's easier to deal with Ints or Bools
   */
  /**
   * Int interface for `classify`
   */
  def classifyInt(input: Seq[Int]): Seq[Int] = {
    classify(input.map(_.toDouble)).map(activationFunction.customRound(_))
  }

   /**
   * Int interface for `teach`
   */
  def trainInt(input: Seq[Int], desiredResult: Seq[Int]): Unit = {
    val i2 = input.map(_.toDouble)
    val d2 = desiredResult.map(_.toDouble)
    train(i2, d2)
  }
  
  /**
   * Bool interface for `classify`
   */
  def classifyBool(input: Seq[Boolean]): Seq[Boolean] = {
    classify(input.map(activationFunction.fromBoolean(_).doubleValue)).map(activationFunction.toBoolean(_))
  }
  
   /**
   * Bool interface for `teach`
   */
  def trainBool(input: Seq[Boolean], desiredResult: Seq[Boolean]): Unit = {
    val i2 = input.map(activationFunction.fromBoolean(_).doubleValue)
    val d2 = desiredResult.map(activationFunction.fromBoolean(_).doubleValue)
    train(i2, d2)
  }
}