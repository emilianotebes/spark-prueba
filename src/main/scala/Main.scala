package org.et.spark.example

import org.apache.log4j.LogManager
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Main {
  val LOGGER = LogManager.getRootLogger

  def main(args: Array[String]): Unit = {
    LOGGER.info("Iniciando...")

    val conf = new SparkConf().setAppName("probando").setMaster("local[8]")
    val sparkContext = new SparkContext(conf)

    val startTime = System.nanoTime
    val parallel: RDD[String] = sparkContext
      .textFile("/home/emiliano/Documents/loremIpsum.txt", 10)
      .flatMap(f => f.split("\\s+"))
      .filter(f => f != null && f.nonEmpty)

    parallel.foreach(f => {
      LOGGER.info(s"INICIO $f")

      val response = GoogleClient.search(f)
      response match {
        case Left(err) => LOGGER.error("ERROR" + err.getMessage)
        case Right(value) => LOGGER.info(s"SEARCH RESULT FOR $f")
      }
      LOGGER.info(s"FIN $f")
    })

    val endTime = System.nanoTime
    val duration = endTime - startTime

    LOGGER.info("TOTAL DURATION: " + duration)
  }
}
