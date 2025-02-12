package com.landoop.connect.sql

import java.text.SimpleDateFormat
import java.util.Date
import com.landoop.json.sql.JacksonJson
import com.sksamuel.avro4s.AvroSchema
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.util.Random

/**
  * Created by stefan on 17/04/2017.
  */
class GeneratorTest extends AnyWordSpec with Matchers {
  "Generator" should {
    "generate schema" in {
      val _ = Sql.parse("SELECT * FROM `order-topic`")
      val schema = AvroSchema[Product]
      val str = schema.toString
      println(str)
    }

    "generate data" in {
      val rnd = new Random(System.currentTimeMillis())
      val f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.FFF")
      val products = (1 to 4).map { i =>
        Product(i, f.format(new Date()), s"product_$i", Payment(rnd.nextDouble(), i * rnd.nextInt(3), "GBP"))
      }.map(JacksonJson.toJson).mkString(s"${System.lineSeparator()}")
      println(products)
    }
  }

}


case class Product(id: Int,
                   created: String,
                   name: String,
                   payment: Payment)

case class Payment(price: Double,
                   quantity: Int,
                   currency: String)