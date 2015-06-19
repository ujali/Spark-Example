package com.example

import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.mllib.classification.LogisticRegressionWithSGD
import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.linalg.Vectors


object MLlib extends App {

  // Turn off spark's default logger
  Logger.getLogger("org").setLevel(Level.OFF)

  // Setting up spark context 
  val conf = new SparkConf().setMaster("local[4]").setAppName("mllib") // run locally with enough threads
  val sc = new SparkContext(conf)

  val datasetOne = sc.textFile("/home/ujali/SparkProjects/Project/spark-example/Spark-Example/src/main/resources/data/spam.txt")
  val datasetTwo = sc.textFile("/home/ujali/SparkProjects/Project/spark-example/Spark-Example/src/main/resources/data/ham.txt")
  // Create a HashingTF instance to map email text to vectors of n no. of  features.
  val tf = new HashingTF()
  // Each email is split into words, and each word is mapped to one feature.
  val spam = datasetOne.map(text => tf.transform(text.split(" ")))
  val normal = datasetTwo.map(text => tf.transform(text.split(" ")))
  // Create LabeledPoint datasets for positive (spam) and negative (normal) examples.
  val positive = spam.map(features => LabeledPoint(1, features))
  val negative = normal.map(features => LabeledPoint(0, features))
  val trainingData = positive.union(negative)
  val pos = LabeledPoint(1.0, Vectors.dense(1.0, 0.0))

  trainingData.cache()
  val model = new LogisticRegressionWithSGD().run(trainingData)
  // Test on a positive example (spam) and a negative one (normal).
  val posTest = tf.transform(
    "O M G GET cheap stuff by sending money to ...".split(" "))
  val negTest = tf.transform(
    "Hi Dad, I started studying Spark the other ...".split(" "))
  //  println("Prediction for positive test example: " + model.predict(posTest))
  println("Prediction for message " + model.predict(negTest))

}
