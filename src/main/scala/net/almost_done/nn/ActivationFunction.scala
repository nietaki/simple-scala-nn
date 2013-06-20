package net.almost_done.nn

import scala.math.abs

/**
 * A base trait for activation functions. There are two used most commonly: the 
 * [[https://en.wikipedia.org/wiki/Sigmoid_function sigmoid]] function and the 
 * [[https://en.wikipedia.org/wiki/Tanh Hyperbolic tangent]] function.
 */
protected trait ActivationFunction extends Function1[Double, Double] {
  
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
  
  def bounds: Tuple2[Int, Int]

  def customRound(result: Double): Int = {
    if(abs(result - bounds._1) < abs(result - bounds._2)) bounds._1 else bounds._2
  }
  
  def withinBounds(x: Double): Boolean = x >= bounds._1 && x <= bounds ._2
  
  def withinBounds(x: Int): Boolean = x >= bounds._1 && x <= bounds ._2
  
  def fromBoolean(x: Boolean): Int = if(x) bounds._2 else bounds._1
  
  def toBoolean(x: Double): Boolean = customRound(x) == bounds._2
  
}