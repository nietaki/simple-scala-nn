package net.almost_done.nn

import scala.math.tanh

case class HyperbolicTangent(beta: Double) extends ActivationFunction {
  
  def apply(h: Double): Double = {
    tanh(beta * h)
  }
  
  def derivative(h: Double): Double = {
    val f = apply(h)
    beta * (1.0 - f * f)
  }
  
  def bounds: Tuple2[Int, Int] = (-1, 1)
}