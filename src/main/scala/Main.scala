package org.et.spark.example

import org.apache.spark.{SparkConf, SparkContext}

object Main {
  def main(args: Array[String]): Unit = {
    println("Iniciando...")
    val conf = new SparkConf().setAppName("probando").setMaster("local")
    val context = new SparkContext(conf)
    val searches = Seq("hola", "chau", "otra busqueda")
    val parallel = context.parallelize(searches, 10)

    parallel.foreach(f => {
      val response = GoogleClient.search(f)
      response match {
        case Left(value) => println("ERROR" + value)
        case Right(value) => println(value)
      }
    })
  }
}
