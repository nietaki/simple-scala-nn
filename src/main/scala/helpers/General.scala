package helpers

import breeze.linalg._
import breeze.numerics._
import math.exp


object General {

  def assymetricalCoin(p: Double): Boolean = (math.random < p)
  
  def time(f: => Unit)={
    val s = System.currentTimeMillis
    f
    print("execution time in seconds: ")
    println((System.currentTimeMillis - s) * 1.0 / 1000.0)
  }
  
  def sigmoid(beta: Double, h: Double) = {
    1. / (1. + exp(-2. *  beta * h))
  }
  
  def sigmoidPrim(beta: Double, h: Double) = {
    val fx = sigmoid(beta, h)
    2. * beta * fx * (1 - fx)
  }
}