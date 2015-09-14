package com.gvolpe.api

import java.net.URLDecoder

import com.gvolpe.api.service.{HomeService, UserService, ProductService}
import org.http4s.server.blaze.BlazeBuilder

object Api extends App {

  BlazeBuilder.bindHttp(8080)
    .mountService(HomeService(), "/")
    .mountService(UserService(), "/users")
    .mountService(ProductService(), "/products")
    .run
    .awaitShutdown()

  def parseUrlParameters(url: String) = {
    url.split("&").map( v => {
      val m =  v.split("=", 2).map(s => URLDecoder.decode(s, "UTF-8"))
      m(0) -> m(1)
    }).toMap
  }

}
