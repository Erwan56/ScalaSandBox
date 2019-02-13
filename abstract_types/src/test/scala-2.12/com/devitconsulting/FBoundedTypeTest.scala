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

  test("use wrapper") {
    object ControlAddons {

      implicit object DivingControl1 extends DivingControl[DivingControl1Input] {
        override def control(input: DivingControl1Input): Unit = ???
      }

    }

    import ControlAddons._

    trait ControlInput
    trait DivingControlInput extends ControlInput

    trait Control[T] {
      def control(input: T)
    }

    case class DivingControl1Input() extends ControlInput
    trait DivingControl[T <: DivingControlInput] extends Control[T]


    case class Executable[Input](input: Input, control: Control[Input]) {
      def execute(): Unit = control.control(input)
    }

    def asExecutable[A](a: A)(implicit s: Control[A]): Executable[A] = Executable(a, s)

    val controls = List[Control[_]](DivingControl1)
    controls.map(_.control())


    //    trait Controllable[A] {
    //      // transaction ou dom
    //      def control(a: A): Unit
    //    }
    //
    //    case class Tx()
    //    implicit object Control1 extends Controllable[Tx] {
    //      override def control(a: Tx): Unit = "control1"
    //    }
    //
    //    case class Executable[Input](input: Input, control: Control[Input]) {
    //            def execute(): Unit = control.control(input)
    //          }
    //    def asExecutable[A](a: A)(implicit s: Control[A]): Executable[A] = Executable(a, s)
    //
    //    val controlList = List[Controllable[_]](Control1)
//    controlList.map(_.control(Tx()))
  }

}