# Simple Scala Neural Networks #
Simple Scala NN is a scala implementation of (for now) feed-forward neural networks. It is designed to be simple (easy to use, understand and modify), flexible (arbitrary number of neurons and hidden layers, different activation functions), and reasonably fast (vectorized and clutter-free). 

## Usage ##
The interface is dead simple

```scala
import net.almost_done.nn._

// specify how many neurons you want in input layer, hidden layers and output layer
val neuronsInLayers = List(2, 3, 1)

// choose the activation function
val sigmoid = new SigmoidFunction(1.9)

val gamma = 0.8

// create the neural network
val nn: NeuralNetwork = new FeedForwardNeuralNetwork(neuronsInLayers, sigmoid, gamma)

// train the network using normalized data, usually you would do this multiple times
val input = List(0.95, 0.1)
vl result = List(1.0)
nn.train(input, result)

// classify other examples
val classification: Seq[Double] = nn.classify(List(0.3, 0.7))
```

More comprehensive examples in the [examples](https://github.com/nietaki/simple-scala-nn/tree/master/src/main/scala/examples) package. The could should also contain decent comments that can be helpful.

## Q&A ##

### What exactly does it do? ###
It can create and train feed-forward neural networks (with bias) using backpropagation, as well as provides interfaces to create new ones.

### What is it for? There are plenty other, more advanced machine learning libraries! ###
It's mostly for people who want to understand how neural networks work. While writing your own library based on literature is a good approach if you have the time and persistance, going through a nice, simple implementation is the second best thing. It is also a fully functional implementation and there's no reason why it couldn't be used in real projects. 

I essentially wrote it for practice and liked how it turned out enough to want to share it. 

### Is it going to be developed further? ###
I will probably be adding some simple neural network efficiency testing framework and perhaps some form of recursive neural networks. If you're interested in something else, let me know or create an issue. 

I am naturally accepting pull requests.

### Where is the .jar? ###
Not there yet, see [issue 4](https://github.com/nietaki/simple-scala-nn/issues/4).

## Dependencies ##
The library uses [breeze.math](https://github.com/scalanlp/breeze) linear algebra functionalities and [specs2](http://etorreborre.github.io/specs2/) for any testing it might contain.

## License ##
The code is released under [MIT license](https://en.wikipedia.org/wiki/MIT_License), which essentially means you can use it for everything as long as you preserve the author and the copyright notice in the files you'll be using.
