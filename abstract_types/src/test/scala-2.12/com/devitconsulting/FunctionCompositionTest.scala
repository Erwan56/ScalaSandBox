package com.devitconsulting

import java.time.LocalDate

import org.scalatest.{FunSuite, Matchers}

/**
  * Created by wawan on 13/02/19.
  */
class FunctionCompositionTest extends FunSuite with Matchers {

  test("function composition") {
    case class Customer(name: String, age: Int, oldFlag: Boolean = false, dumbNameFlag: Boolean = false)

    def setOldFlag(): (Customer) => Customer = {
      customer => {
        if (customer.age > 50)
          customer.copy(oldFlag = true)
        else
          customer
      }
    }

    def setDumbName(dumbName: String): (Customer) => Customer = {
      customer => {
        if (customer.name == dumbName)
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

  test("function composition through implicit") {
    case class Customer(name: String, age: Int, oldFlag: Boolean = false, dumbNameFlag: Boolean = false)

    implicit class CustomerRules(customer: Customer) {
      def setOldFlag(): Customer = {
        if (customer.age > 50)
          customer.copy(oldFlag = true)
        else
          customer
      }

      def setDumbName(dumbName: String): Customer = {
        if (customer.name == dumbName)
          customer.copy(dumbNameFlag = true)
        else
          customer
      }
    }


    val kevinCust = Customer("kevin", age = 10)
    val henryCust = Customer("henry", age = 52)

    println("-- old flag")
    println(kevinCust.setOldFlag())
    println(henryCust.setOldFlag())

    println("-- dumb flag")
    println(kevinCust.setDumbName("kevin"))
    println(henryCust.setDumbName("kevin"))

    println("-- old and dumb flag")
    println(kevinCust.setOldFlag().setDumbName("kevin"))
    println(henryCust.setOldFlag().setDumbName("kevin"))
  }

  test("function composition with merge") {
    case class Item(id: String, date: LocalDate, amount: Int)
    case class Product(id: String, lastDate: LocalDate, totalAmount: Int, item: List[Item])

    def lastDateRule(): (Product, Product) => (Product) = {
      (rightProduct, leftProduct) => {
        if (rightProduct.lastDate.compareTo(leftProduct.lastDate) < 0) {
          rightProduct.copy(lastDate = leftProduct.lastDate)
        }
        else
          rightProduct
      }
    }

    def lastDateRule2(leftProduct: Product): (Product) => (Product) = {
      rightProduct => {
        if (rightProduct.lastDate.compareTo(leftProduct.lastDate) < 0) {
          rightProduct.copy(lastDate = leftProduct.lastDate)
        }
        else
          rightProduct
      }
    }

    def lastDateRule3(): Product => Product => Product = {
      rightProduct => {
        leftProduct => {
          if (rightProduct.lastDate.compareTo(leftProduct.lastDate) < 0) {
            rightProduct.copy(lastDate = leftProduct.lastDate)
          }
          else
            rightProduct
        }
      }
    }

    def lastDateRule4(): (Product) => Product = {
      product => {
        product
      }
    }


    def totalAmountRule(): (Product, Product) => Product = {
      (rightProduct, leftProduct) => {
        rightProduct.copy(totalAmount = rightProduct.totalAmount + leftProduct.totalAmount)
      }
    }

    val p1 = Product(id = "P1", lastDate = LocalDate.of(2019, 1, 1), totalAmount = 10, item = List(Item("I1", date = LocalDate.of(2019, 1, 1), amount = 10)))
    val p2 = Product(id = "P2", lastDate = LocalDate.of(2019, 1, 10), totalAmount = 15, item = List(Item("I2", date = LocalDate.of(2019, 1, 10), amount = 15)))

    val curried: (Product => Product => Product) = {
      rightProduct => {
        leftProduct => {
          if (rightProduct.lastDate.compareTo(leftProduct.lastDate) < 0) {
            rightProduct.copy(lastDate = leftProduct.lastDate)
          }
          else
            rightProduct
        }
      }
    }
    val productRules = curried andThen (_ andThen curried)
    println(productRules(p1)(p2))

    /*println(List(p1, p2).fold(null) {
      case (x: Product, y: Product) => lastDateRule()(x, y)
    })*/

    val productToProduct = lastDateRule4() andThen lastDateRule4() andThen lastDateRule4()

  }
}
