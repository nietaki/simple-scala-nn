package test

import org.specs2.mutable._
import org.specs2.matcher._

import breeze.linalg._
import breeze.numerics._
import math._

import helpers._

class SampleSpec extends Specification {
 
  "sample" should {
    "work" in {
      0.0 must be closeTo(0.0, 0.1)
    }
  }

}