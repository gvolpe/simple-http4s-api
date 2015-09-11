package com.gvolpe.api

import com.gvolpe.api.service.{HomeService, UserService, ProductService}
import org.http4s.server.blaze.BlazeBuilder

object Api extends App {

  BlazeBuilder.bindHttp(8080)
    .mountService(HomeService(), "/")
    .mountService(UserService(), "/users")
    .mountService(ProductService(), "/products")
    .run
    .awaitShutdown()

}
