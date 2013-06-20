package net.almost_done.nn

import breeze.linalg._
import scala.collection.mutable.Buffer
import helpers.General._
import System.out._

/**
 * layerCounts describes the number of neurons in each layer
 * layerCounts(0) is the number of input neurons (NOT including the weighing unit - 0)
 * layerCounts(layerCounts.length - 1) is the number of output neurons 
 * 
 * beta - steepness of the sigmoid function
 * gamma - scaling parameter of corrections
 */
class FeedForwardNeuralNetwork(_neuronCounts: List[Int], val beta: Double, val gamma: Double, val useBias: Boolean = true) {
  
  val BIAS_VALUE: Double = if(useBias) 1.0 else 0.0
  
  val layerCount = _neuronCounts.size
  val M = layerCount - 1
  val neuronCounts = _neuronCounts.map( _ + 1).updated(M, _neuronCounts.last)
    
  /**
   * neuron states
   */
  val V: Buffer[DenseVector[Double]] = (neuronCounts map { layerCount => DenseVector.ones[Double](layerCount)}).toBuffer
  
  //pobudzenia
  val h: Buffer[DenseVector[Double]] = (neuronCounts map { layerCount => DenseVector.ones[Double](layerCount)}).toBuffer
  //odchylenia
  val delta: Buffer[DenseVector[Double]] = (neuronCounts map { layerCount => DenseVector.ones[Double](layerCount)}).toBuffer
  
  
  private val _w = for(ns <- neuronCounts.sliding(2)) yield { 
    assert(ns.length == 2)
    val prevSize = ns.head
    val nextSize = ns.last
    
    DenseMatrix.rand(nextSize, prevSize).map(x => 1. - 2. * x ) //random weights at the beginning
  }
  
   /**
   * (layerCount - 1) matrices of connection weights between layers
   * 
   * the sum (of influences) for i+1 can be calculated by w(i) * V(i) [matrix multiplication]
   */
  val w = _w.toBuffer
  
  
  def classify(input: Seq[Double]): Seq[Double] ={
    assert(input.length == V(0).length - 1)
    
    V(0) := DenseVector((BIAS_VALUE +: input) : _*) //setting the input layer values
//    V(0) := DenseVector((0.0 +: input) : _*) //setting the input layer values
    
    //forward propagation
    for(i <- Range(0, M)) {
      h(i+1) := w(i) * V(i)
      
      V(i+1) := h(i+1).map(x => sigmoid(beta, x))
      if(i+1 != M) {
        h(i+1)(0) = Double.MaxValue
        V(i+1)(0) = BIAS_VALUE
      }
    }
    
    V(M).toArray.toSeq
  }
  
  def classifyInt(input: Seq[Int]): Seq[Int] = {
    classify(input.map(i => 1.0 * i)).map(math.round(_).toInt)
  }
  
  def teachInt(input: Seq[Int], desiredResult: Seq[Int]) = {
    val i2 = input.map(i => 1.0 * i)
    val d2 = desiredResult.map(i => 1.0 * i)
    teach(i2, d2)
  }
  
  def teach(input: Seq[Double], desiredResult: Seq[Double]) = {
    assert(input.length == V(0).length - 1)
    assert(desiredResult.length == V(M).length)
    
    assert(desiredResult.forall(d => (d >= 0.0 && d <= 1.0)))
    
    classify(input) //sets _V and h
    
    val eta: DenseVector[Double] = DenseVector(desiredResult : _*)
    
    //backpropagation - last layer
    delta(M) := h(M).map(sigmoidPrim(beta, _)) :* (eta - V(M))
//    println("h(M): " + h(M) + " sigmoid'(h(M)): " + h(M).map(sigmoidPrim(beta, _)) + " (eta, V(M)) " + eta  + V(M) + " delta(M) " + delta(M))
    
    //backpropagation - rest of the layers
    for(m <- Range(M-1, 1, -1).inclusive) {
      delta(m) := h(m).map(sigmoidPrim(beta, _)) :* (w(m).t * delta(m+1)) 
      delta(m)(0) = 0.
    }
    
    //adjust the w's
    for(m <- Range(1, M).inclusive) {
      val added = (delta(m) * V(m-1).t :* gamma)
      w(m-1) := w(m-1) + added
    }
  }
}