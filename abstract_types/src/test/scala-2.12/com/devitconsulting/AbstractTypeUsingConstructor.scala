package com.devitconsulting

import org.scalatest.{FunSuiteLike, Suite}

/**
  * Created by wawan on 24/10/18.
  */
class AbstractTypeUsingConstructor extends Suite with FunSuiteLike {
  test("control test") {
//    val control = new BcdInflatorControl()
//    control.control(new BcdControlInput())
  }

}


//trait ControlInput[A <: ControlInput[A]] {
//  def create: A
//}
//
//trait DivingControlInput[A] extends ControlInput[DivingControlInput[A]]
//
//class BcdControlInput extends DivingControlInput[BcdControlInput] {
//  override def create: BcdControlInput = ???
//}
//
//trait Control {
//  def control[A <: ControlInput[A] ](input: A)
//}
//
//trait DivingControl extends Control {
//
//}
//
//trait BcdControl extends DivingControl {
//
//}
//
//class BcdInflatorControl extends BcdControl {
//  override def control[A <: ControlInput[A]](input: A): Unit = ???
//}
