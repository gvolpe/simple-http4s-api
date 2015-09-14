package com.gvolpe.api.service

import org.http4s.{Request, Status, Uri}

class ProductServiceSpec extends HttpServiceSpec {

  "Product Service" should {

    val service = ProductService()

    "Throw a NumberFormatException when trying to parse the Product ID from the url" in {
      val request = new Request(uri = Uri(path = "/unknown"))

      intercept[NumberFormatException] {
        service.run(request).run
      }
    }

    "Not find the unknown url request" in {
      val request = new Request(uri = Uri(path = "/123/unknown"))
      val response = service.run(request).run

      response.status should be (Status.NotFound)
    }

    "Get the list of products" in {
      val request = new Request()
      val response = service.run(request).run

      response.status should be (Status.Ok)
      val expected = """ [{"id":1,"name":"Book"},{"id":2,"name":"Calc"},{"id":3,"name":"Guitar"}] """.trim
      response.body.asString should be (expected)
    }

    "Get the product by Id" in {
      val productId = 123
      val request = new Request(uri = Uri(path = s"/$productId"))
      val response = service.run(request).run

      response.status should be (Status.Ok)
      val expected = s""" {"id":$productId,"name":"Name#$productId"} """.trim
      response.body.asString should be (expected)
    }

  }

}
