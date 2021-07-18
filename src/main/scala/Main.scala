package org.et.spark.example

import org.apache.log4j.LogManager
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Main {
  val LOGGER = LogManager.getRootLogger

  def main(args: Array[String]): Unit = {
    var counter = 0;

    LOGGER.info("Iniciando...")

    val conf = new SparkConf().setAppName("probando").setMaster("local[32]")
    val sparkContext = new SparkContext(conf)

    val startTime = System.nanoTime
    val parallel: RDD[String] = sparkContext
      //      .textFile("/home/emiliano/Documents/loremIpsum.txt", 20)
      .textFile("src/main/resources/lala.txt", 20)
      .flatMap(f => f.split("\\s+"))
      .filter(f => f != null && f.nonEmpty)

    parallel.map(f => {
      LOGGER.info(s"INICIO $f")

      val response = GoogleClient.search(f)
      val bla = response match {
        case Left(err) =>
          LOGGER.error("ERROR" + err.getMessage)
          None
        case Right(value) =>
          LOGGER.info(s"SEARCH RESULT FOR $f: $value")
          Some(value)
      }
      LOGGER.info(s"FIN $f")
      bla
    }).collect()

    val endTime = System.nanoTime
    val duration = endTime - startTime

    LOGGER.info("TOTAL DURATION: " + duration)
  }
}
