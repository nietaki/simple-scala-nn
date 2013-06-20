package helpers
import scala.collection.mutable.MutableList
import breeze.linalg._

object tester {

  
  
  val zo3 = new ZeroOneSequence(3)                //> zo3  : helpers.ZeroOneSequence = helpers.ZeroOneSequence@5e7808b9
  val zoi = zo3.allSequences                      //> zoi  : Iterator[scala.collection.immutable.Seq[Int]] = non-empty iterator
  
  while(zoi.hasNext) {
    var tmp = zoi.next
    System.out.println(tmp)
    System.out.println(ZeroOneSequence.checkParity(tmp))
  }                                               //> Vector(0, 0, 0)
                                                  //| 0
                                                  //| Vector(1, 0, 0)
                                                  //| 1
                                                  //| Vector(0, 1, 0)
                                                  //| 1
                                                  //| Vector(1, 1, 0)
                                                  //| 0
                                                  //| Vector(0, 0, 1)
                                                  //| 1
                                                  //| Vector(1, 0, 1)
                                                  //| 0
                                                  //| Vector(0, 1, 1)
                                                  //| 0
                                                  //| Vector(1, 1, 1)
                                                  //| 1
  zo3.nextRandom                                  //> res0: scala.collection.immutable.Seq[Int] = Vector(1, 0, 1)
  zo3.nextRandom                                  //> res1: scala.collection.immutable.Seq[Int] = Vector(1, 0, 0)
  
  
  var d = DenseMatrix(1, 1)                       //> d  : breeze.linalg.DenseMatrix[Int] = 1  
                                                  //| 1  
  d.map(_ * 2)                                    //> res2: breeze.linalg.DenseMatrix[Int] = 2  
                                                  //| 2  
   d                                              //> res3: breeze.linalg.DenseMatrix[Int] = 1  
                                                  //| 1  
   Range(2-1, 1, -1).inclusive                    //> res4: scala.collection.immutable.Range = Range(1)
   1.6                                            //> res5: Double(1.6) = 1.6
}