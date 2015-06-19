package com.example

import org.apache.spark._
import org.apache.spark.rdd.RDD
import org.apache.spark.graphx._

object PageRank extends App {
  val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Page Rank Algorithm")
  val sc = new SparkContext(sparkConf)
  // Load the edges as a graph
  val graph = GraphLoader.edgeListFile(sc, "/home/ujali/SparkProjects/Project/spark-example/Spark-Example/src/main/resources/data/followers.txt")
  // Run PageRank
  val ranks = graph.pageRank(0.0001).vertices
  // Join the ranks with the usernames
  val users = sc.textFile("/home/ujali/SparkProjects/Project/spark-example/Spark-Example/src/main/resources/data/users.txt").map { line =>
    val fields = line.split(",")
    (fields(0).toLong, fields(1))
  }
  val ranksByUsername = users.join(ranks).map {
    case (id, (username, rank)) => (username, rank)
  }
  // Print the result
  println(ranksByUsername.collect().mkString("\n"))
}