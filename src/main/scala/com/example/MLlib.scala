package com.example

import org.apache.log4j.Logger
import org.apache.log4j.Level
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.classification.LogisticRegressionWithSGD

/**
 * @author ujali
 * Logistic Regression for Titanic Disaster
 */
object MLlib extends App {

  Logger.getLogger("org").setLevel(Level.OFF)

  // Set up Spark configuration and context

  val conf = new SparkConf().setMaster("local[*]").setAppName("example")
  val sc = new SparkContext(conf)

  // Load data set from resources

  val trainDataset = sc.textFile("/home/ujali/Projects/spark-stuff/spark-example/src/main/resources/data/train.csv")

  // HashingTF to convert text into vector feature

  val hashingTF = new HashingTF()

  //Data is split 
  val inputFeature = trainDataset.map(targetValue => hashingTF.transform(targetValue.split(",")))
  //create labeled points for data set
  //  val x = inputFeature.foreach(println)
  val one = inputFeature.map(feature => LabeledPoint(1, feature))
  val zero = inputFeature.map(feature => LabeledPoint(0, feature))
  val trainingSet = zero.union(one)

  val x = one.foreach(println)
  val y = zero.foreach(println)
  trainingSet.cache()

  val model = new LogisticRegressionWithSGD().run(trainingSet)
  val testDataset = sc.textFile("/home/ujali/Projects/spark-stuff/spark-example/src/main/resources/data/test.csv").map(_.split(",").toSeq)
  val result = hashingTF.transform(testDataset)

  val prediction = model.predict(result)
  //  prediction.foreach(println)

}
