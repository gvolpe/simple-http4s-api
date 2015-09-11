package com.gvolpe.api

import com.gvolpe.api.service.ProductService
import org.http4s.server.blaze.BlazeBuilder

object Api extends App {

  BlazeBuilder.bindHttp(8080)
    .mountService(ProductService(), "/")
    .run
    .awaitShutdown()

}
