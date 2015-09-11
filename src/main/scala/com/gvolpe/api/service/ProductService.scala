package com.gvolpe.api.service

import com.gvolpe.api.service.Implicits._
import org.http4s.dsl._
import org.http4s.server.HttpService

object ProductService {

  def apply(): HttpService = service

  private val service = HttpService {
    case GET -> Root =>
      Ok(List(Product(1, "Book"), Product(2, "Calc"), Product(3, "Guitar")))
    case GET -> Root / id =>
      Ok(Product(id.toLong, s"Name#$id"))
  }

}
