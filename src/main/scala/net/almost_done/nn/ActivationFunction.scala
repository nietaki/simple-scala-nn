package net.almost_done.nn

/**
 * A base trait for activation functions. There are two used most commonly: the 
 * [[https://en.wikipedia.org/wiki/Sigmoid_function sigmoid]] function and the 
 * [[https://en.wikipedia.org/wiki/Tanh Hyperbolic tangent]] function.
 */
trait ActivationFunction {
  
  /**
   * calculates the value of the function for a given argument
   * 
   * @param h argument of the activation function
   */
  def apply(h: Double): Double;
  
  /**
   * calculates the derivative of the activation function
   * 
   * @param h argument of the derivative
   */
  def derivative(h: Double): Double;

}