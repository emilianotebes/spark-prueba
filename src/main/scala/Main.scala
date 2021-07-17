package org.et.spark.example

import org.apache.log4j.LogManager
import org.apache.spark.{SparkConf, SparkContext}

object Main {
  val LOGGER = LogManager.getRootLogger

  def main(args: Array[String]): Unit = {
    LOGGER.info("Iniciando...")

    val conf = new SparkConf().setAppName("probando").setMaster("local[2]")
    val sparkContext = new SparkContext(conf)
    val searches = Seq("hola", "chau", "otra busqueda", "otra mas", "Messi", "futbol")
    val parallel = sparkContext.parallelize(searches)

    parallel.foreach(f => {
      LOGGER.info(s"INICIO $f")

      val response = GoogleClient.search(f)
      response match {
        case Left(value) => LOGGER.info("ERROR" + value)
        case Right(value) => LOGGER.info(s"SEARCH RESULT FOR $f")
      }
      LOGGER.info(s"FIN $f")
    })
  }
}
