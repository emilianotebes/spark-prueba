package org.et.spark.example

import org.apache.log4j.LogManager
import org.apache.spark.{SparkConf, SparkContext}

object Main {
  val LOGGER = LogManager.getRootLogger

  def main(args: Array[String]): Unit = {
    LOGGER.info("Iniciando...")

    val conf = new SparkConf().setAppName("probando").setMaster("local[8]")
    val sparkContext = new SparkContext(conf)
    val searches = Seq("hola", "chau", "otra busqueda", "otra mas", "Messi", "futbol")
    val parallel = sparkContext.parallelize(searches)

    val startTime = System.nanoTime

    parallel.foreach(f => {
      LOGGER.info(s"INICIO $f")

      val response = GoogleClient.search(f)
      response match {
        case Left(value) => LOGGER.info("ERROR" + value)
        case Right(value) => LOGGER.info(s"SEARCH RESULT FOR $f")
      }
      LOGGER.info(s"FIN $f")
    })

    val endTime = System.nanoTime
    val duration = endTime - startTime

    LOGGER.info("TOTAL DURATION: " + duration)
  }
}
