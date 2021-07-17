package org.et.spark.example

import scalaj.http.{Http, HttpResponse}

import scala.util.{Failure, Success, Try}

object GoogleClient {

  def search(search: String): Either[Error, String] = {
    val url = s"https://www.google.com/search"

    val connectionTry = Try({
      val response: HttpResponse[String] = Http(url).param("q", search).asString
      response.code match {
        case 200 =>
          Right(response.body)
        case _ =>
          Left(SomeError(response.body))
      }
    })

    connectionTry match {
      case Failure(exception) =>
        Left(SomeError(exception.getMessage))
      case Success(value) =>
        value
      case _ =>
        Left(SomeError("WTF??"))
    }

  }
}

case class SomeError(message: String) extends Error