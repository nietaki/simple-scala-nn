package helpers

import java.io.File
import General._
import net.almost_done.nn.FeedForwardNeuralNetwork
import System.out._
import breeze.linalg._
import scala.util.Random
import scala.collection.mutable.Buffer


object Runner {
  def main(args: Array[String]): Unit = {
    time {
//      testRun()
//      normalRun()
//      statsGen()
//      beginnings()
//      pilotazowe()
//      carefulTest()
//      test()
//      statsGen()
//        drawBests()
    } 
  }
  
  def beginnings() = {

    
    val nnxor = new FeedForwardNeuralNetwork(List(2, 2, 1), 0.8, 1.0)
//    printOr()
    println("w: " + nnxor.w)
    println()
    println("h: " + nnxor.h)
    println()
    println("V: " + nnxor.V)
    println()
    println("delta: " + nnxor.delta)
    
//    nnor.w(0) := new DenseMatrix(3,3, Array[Double](1., -2., 3., -4., 5., -6., 7., 8., 9.))
//    nnor.w(1) := new DenseMatrix(1,3, Array[Double](1., -2., -3.))
    
    def or(x: Double, y: Double): Double = math.max(x,y)
    def xor(x: Double, y: Double): Double = {
      if (x == y) 0.0 else 1.0
    }
    
    
    val onezero = List(
                    List(0, 0),
                    List(0, 1),
                    List(1, 0),
                    List(1, 1)
                  ).map(_.map(_.toDouble))
    
    System.out.println("before teaching")
    def printOr() = {
      for(c <- onezero) {
        System.out.println(c.toString +": " + nnxor.classify(c))
      }
    }
    printOr()
    
    for(i <- Range(0,10000); c <- onezero ) {
      val result = List(xor(c.head, c.last))
      nnxor.teach(c, result)
//      System.out.println(c.toString + " " + result)
    }
    
    System.out.println("after teaching")
    printOr()
    println("w: " + nnxor.w)
    println()
    println("h: " + nnxor.h)
    println()
    println("V: " + nnxor.V)
    println()
    println("delta: " + nnxor.delta)
    
  }
  
  def pilotazowe() = {
    val seqLen = 8
    
//    val nnParity = new NeuralNetwork(List(seqLen, seqLen * seqLen, seqLen * seqLen, 1), 1.0, 1.0)
    val nnParity = new FeedForwardNeuralNetwork(List(seqLen, 256, 1), 1.2, 0.20)
    val zos = new ZeroOneSequence(seqLen)
    
    var  curSuccessProcentage: Double = 0.0
    var it: Int = 0;
    

    
    for(i <- Range(0, 10000); sekwencjaDoNauki <- new Random().shuffle(zos.allSequences.toList) ) {
      //teaching another random sequence
      var r = zos.nextRandom
      nnParity.teachInt(sekwencjaDoNauki, Seq(ZeroOneSequence.checkParity(sekwencjaDoNauki)))
      
//      println(r)
//      println(ZeroOneSequence.checkParity(r) )
      
      var successCount = 0
      for(s <- zos.allSequences) {
        
        var result = nnParity.classifyInt(s).head
//        println(nnParity.classify(s.map(_.toDouble)).head)
        
        if(result == ZeroOneSequence.checkParity(s))
          successCount += 1
      }
      curSuccessProcentage =  successCount.toDouble / zos.combinationCount.toDouble
      
      System.out.println("it " + i.toString + ": " + curSuccessProcentage)
      
      it += 1
    }
    
  }
  
  def statsGen(): Unit = {
    
    val outDir = "out/"
    val random = new Random()
    val seqLen = 8
    val zos = new ZeroOneSequence(seqLen)


    // configuration we don't change
    val iterationCount = 5000
    val statsPeriod = 10

    
    // configuration we DO change
//    val hiddenSizes = List(16, 20, 24)
    val hiddenSizes = List(20)
//    val betas = List(1.2, 2.0, 2.5)
    val betas = List(2.0)
    val gammas = List(0.16)
//    val gammas = List(0.2)
    val biases = List(true)
    val passCount = 20 

    
    //debug output
    val total_passes = hiddenSizes.length * betas.length * gammas.length * biases.length * passCount 
    println("total pass count: " + total_passes)
    
    //TODO: random values, not permutations
    for (
      hiddenSize <- hiddenSizes;
      beta <- betas;
      gamma <- gammas;
      bias <- biases
    ) yield {
//      val cummulativeName = "hl-" + hiddenSize + "_beta-" + beta + "  multiplier.simpleName + "_" + selector.simpleName + "_" + optimizer.simpleName + "_" + mu + "_" + lambda
      val cummulativeName = "hl-%d_beta-%f_gamma-%f_bias-%B_RANDOM" format(hiddenSize, beta, gamma, bias)
      println(cummulativeName)
      val f = new File(outDir + cummulativeName + ".csv")
      Range(0, passCount).inclusive.foreach{ pass => 
        println("pass: " + pass)
        val nn = new FeedForwardNeuralNetwork(List(seqLen, hiddenSize, 1), beta, gamma, bias)
        val progress: Buffer[Double] = Buffer()
        
        Range(0, iterationCount).inclusive.foreach{ iteration =>
          //FIXME, THIS IS TEMPORARY:
//          val teachingExamples = random.shuffle(zos.allSequences.toList)
          val teachingExamples = zos.allSequences.toList.map{_ => zos.nextRandom}
          //teaching
          teachingExamples.foreach { seq =>
            nn.teachInt(seq, Seq(ZeroOneSequence.checkParity(seq)))
          }
          //verifying
          var successCount = 0;
          zos.allSequences.foreach { seq =>
            if(ZeroOneSequence.checkParity(seq) == nn.classifyInt(seq).head)
              successCount += 1
          }
          var successRate: Double = 1. * successCount / zos.combinationCount
          if(iteration % statsPeriod == 0) {
            println("success rate after %d iterations: %f" format (iteration, successRate))
            progress += successRate
          }
        }
        
      }
    }
    
  }
  
  
  
} 