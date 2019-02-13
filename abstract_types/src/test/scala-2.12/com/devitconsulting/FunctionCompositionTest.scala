package com.devitconsulting

import org.scalatest.{FunSuite, Matchers}

/**
  * Created by wawan on 13/02/19.
  */
class FunctionCompositionTest extends FunSuite with Matchers {

  test("function composition") {
    case class Customer(name: String, age: Int, oldFlag: Boolean = false, dumbNameFlag: Boolean = false)

    def setOldFlag(): (Customer) => Customer = {
      customer => {
        if(customer.age > 50)
          customer.copy(oldFlag = true)
        else
          customer
      }
    }

    def setDumbName(dumbName: String): (Customer) => Customer = {
      customer => {
        if(customer.name == dumbName)
          customer.copy(dumbNameFlag = true)
        else
          customer
      }
    }

    val kevinCust = Customer("kevin", age = 10)
    val henryCust = Customer("henry", age = 52)

    println("-- old flag")
    println(setOldFlag()(kevinCust))
    println(setOldFlag()(henryCust))

    println("-- dumb flag")
    println(setDumbName("kevin")(kevinCust))
    println(setDumbName("kevin")(henryCust))

    println("-- old and dumb flag")
    val customerRules = setOldFlag() andThen setDumbName("kevin")
    println(customerRules(kevinCust))
    println(customerRules(henryCust))
  }
}
