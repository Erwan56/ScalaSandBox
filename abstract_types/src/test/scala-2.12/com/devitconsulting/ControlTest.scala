package com.devitconsulting

import org.scalatest.{FunSuiteLike, Suite}

import ControlTest._
/**
  * Created by wawan on 22/10/18.
  */
class ControlTest extends Suite with FunSuiteLike {
  test("control test") {
    /*val controls = List[DivingControl](new BcdInflatorControl, new BcdValvesControl, new AirTankControl)
    controls.map(control => {
      val divingEquipment = DivingEquipment(Bcd("inflator", List("Valve")), Tank("air"))
      control.execute(getControlInput(control, Diver("John"), divingEquipment))
    })*/
//    val control: DivingControl = new BcdInflatorControl()
//    control.execute(new BcdDivingInput)
  }

  private def getControlInput(control: DivingControl, diver: Diver, divingEquipment: DivingEquipment) : DivingInput = {
    control match {
//      case BcdInflatorControl => new BcdDivingInput
      case _ => new BcdDivingInput
    }
  }
}

object ControlTest {

  case class Bcd(inflator: String, valves: List[String])

  case class Tank(air: String)

  case class Diver(name: String)

  case class DivingEquipment(bcd: Bcd, tank: Tank)

  trait DivingInput
  class BcdDivingInput extends DivingInput
  class AirControlInput extends DivingInput

  trait Control {
    type Input
    def execute(input: Input): Unit
  }

  abstract class DivingControl extends Control {
    type Input <: DivingInput
  }

  abstract class BcdControl extends DivingControl {
    type Input = BcdDivingInput
  }

  class BcdInflatorControl extends BcdControl {
    override def execute(input: BcdDivingInput): Unit = ???
  }

  class BcdValvesControl extends BcdControl {
    override def execute(input: BcdDivingInput): Unit = ???
  }

  abstract class AirControl extends DivingControl {
    type Input = AirControlInput
  }

  class AirTankControl extends AirControl {
    override def execute(input: AirControlInput): Unit = ???
  }
}
