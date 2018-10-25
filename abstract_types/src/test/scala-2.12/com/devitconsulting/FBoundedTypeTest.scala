package com.devitconsulting

import org.scalatest.{FunSuite, Matchers}

/**
  * Created by wawan on 25/10/18.
  */
class FBoundedTypeTest extends FunSuite with Matchers {

  test("Simple F Bounded Type") {
    trait Control[A <: Control[A]] {
      def control(input: String): String
    }

    trait DivingControl[T <: DivingControl[T]] extends Control[T]

    case class BcdControl() extends DivingControl[BcdControl] {
      override def control(input: String): String = s"bcdControl: $input"
    }

    case class AirControl() extends DivingControl[AirControl] {
      override def control(input: String): String = s"airControl: $input"
    }

    def exec[A <: Control[A]](control: A, input: String): String = {
      control.control(input)
    }

    exec(BcdControl(), "bcd") shouldEqual "bcdControl: bcd"
    exec(AirControl(), "air") shouldEqual "airControl: air"

    val list = List(BcdControl(), AirControl())


    List[A forSome {type A <: Control[A]}](BcdControl(), AirControl()).map(x => println(exec(x, "sameInput")))
  }

  test("F Bounded Type WIP") {

    trait ControlInput
    trait DivingControlInput extends ControlInput
    class DivingControlConcreteInput extends DivingControlInput

    trait Control[T <: ControlInput, A <: Control[T, A]] {
      def control(input: T)
    }

    trait DivingControl[T <: DivingControlInput] extends Control[T, DivingControl[T]]

    class DivingControlConcrete extends DivingControl[DivingControlConcreteInput] {
      override def control(input: DivingControlConcreteInput): Unit = println("youpi")
    }

    //  case class BcdControlInput() extends DivingControlInput
    //  trait BcdControl extends DivingControl[BcdControlInput]
    //
    //  class BcdControl1 extends BcdControl {
    //    override def control(input: BcdControlInput): Unit = ???
    //  }


    //    exec(new DivingControlConcrete, new DivingControlConcreteInput)

    def exec[T <: ControlInput, A <: Control[T, A]](control: A, input: T) = {
      control.control(input)
    }
  }

}
