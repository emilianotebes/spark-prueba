package org.et.spark.example

import scalaj.http.{Http, HttpResponse}

object GoogleClient {

  def search(search: String): Either[Error, String] = {
    val url = s"https://www.google.com/search"
    val response: HttpResponse[String] = Http(url).param("q", search).asString
    response.code match {
      case 200 => Right(response.body)
      case _ => Left(SomeError(response.body))
    }
  }
}

case class SomeError(message: String) extends Error