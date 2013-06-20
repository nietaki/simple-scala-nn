package sample

import java.lang.System.out.println
import scala.util.Random
import Helpers.time
import net.almost_done.nn.FeedForwardNeuralNetwork
import net.almost_done.nn.SigmoidFunction
import net.almost_done.nn.FeedForwardNeuralNetwork
import net.almost_done.nn.SigmoidFunction


object Runner {
  def main(args: Array[String]): Unit = {
    time {
      //choose the example you want to run
      xorExample()
//      parityExample()
    } 
  }
  
  /**
   * A classic, simple example of a neural network is a network is a network calculating the XOR
   * function. A network in the simplest form is not able to do it - we need a network with 
   * at least two neurons in the hidden layer. Let's see how it works.
   */
  def xorExample(): Unit = {
    
    //we will be using the boolean interface, hence the boolean values
    val possibleInputs = List(List(false, false),
                              List(false, true),
                              List(true, false),
                              List(true, true))
                              
    /*
     * List of neuron counts for each layer:
     * two in the input layer, two in the hidden layer, one in the output layer
     * 
     * the bias neurons are hidden "under the hood"
     */
    val neuronsInLayers = List(2, 3, 1)
    
    val sigmoid = new SigmoidFunction(1.9)
    
    val gamma = 0.8
    //creating the Neural network
    val xornn = new FeedForwardNeuralNetwork(neuronsInLayers, sigmoid, gamma)
    
    /**
     * now let's see how many times we have to teach the network all the examples in order for it
     * to calculate the xor correctly
     */
    
    var it: Int = 0;
    var correctAnswersCount: Int = 0;
    
    val r = new Random()
    while(correctAnswersCount != 4 && it <= 10000) {
      //teaching the nn
      for(ex <- possibleInputs) {
        xornn.teachBool(ex, Seq(ex.head ^ ex.last))
      }
      
      //checking if it gives the correct answers already
      val correctAnswers = for (
          pi <- possibleInputs;
          answer = xornn.classifyBool(pi).head;
          correctAnswer = pi.head ^ pi.last
      ) yield answer == correctAnswer
      
      correctAnswersCount = correctAnswers.count(b => b)
      System.out.println("%d iteration: %d correct answers" format (it, correctAnswersCount))
      it += 1;
    }
    
    if(it > 10000){
      System.out.println("the system has reached a local optimum without hitting the correct solution - this can also happen :)")
    }
    
  }
  
  def parityExample(): Unit = {
    val random = new Random()
    
    val seqLen = 8
    val zos = new ZeroOneSequence(seqLen)

    //how many teaching iterations do you want?
    val iterationCount = 600
    
    //every how many iterations do you want to see the debug output?
    val statsPeriod = 10
    
    /*
     * you can include multiple configurations to see how they compare, just edit the lists
     */ 
    // how many neurons should there be in the hidden layer?
    val hiddenLayerSizes = List(20)
    val betas = List(2.0)
    val gammas = List(0.16)
    
    // do you want to use the bias neurons?
    val biases = List(true)
    
    /*
     *  if you want to run the whole test scenario multiple times, change the passCount
     * 
     *  initially the weights are initialized randomly, so some passes might get good results
     *  sooner than others 
     */
    val passCount = 1 
    
    val total_passes = hiddenLayerSizes.length * betas.length * gammas.length * biases.length * passCount 
    println("total pass count: " + total_passes)
    
    for (
      hiddenSize <- hiddenLayerSizes;
      beta <- betas;
      gamma <- gammas;
      bias <- biases
    ) {
      println( "hidden layers: %d, beta: %f gamma: %f, bias: %B" format(hiddenSize, beta, gamma, bias))
      Range(0, passCount).foreach{ pass => 
        println("pass: " + pass)
        val nn = new FeedForwardNeuralNetwork(List(seqLen, hiddenSize, 1), new SigmoidFunction(beta), gamma, bias)
        
        Range(0, iterationCount).inclusive.foreach{ iteration =>
          /*
           * in each iteration we get all the possible sequences, shuffle them, and feed them to the
           * neural network along with the correct parity of the sequence
           */
          val teachingExamples = random.shuffle(zos.allSequences)
          teachingExamples.foreach { teachingSequence =>
            nn.teachInt(teachingSequence, Seq(ZeroOneSequence.checkParity(teachingSequence)))
          }
          if(iteration % statsPeriod == 0) {
            var successCount = 0;
            zos.allSequences.foreach { seq =>
              if(ZeroOneSequence.checkParity(seq) == nn.classifyInt(seq).head)
                successCount += 1
            }
            val successRate: Double = 1.0 * successCount / zos.combinationCount
            println("success rate after %d iterations: %f" format (iteration, successRate))
          }
        }
      }
    }
  }
} 