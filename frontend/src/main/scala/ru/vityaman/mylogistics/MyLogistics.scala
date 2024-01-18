package ru.vityaman.mylogistics

import com.raquo.laminar.api.L._
import org.scalajs.dom

object MyLogistics {
  def Counter(label: String, initialStep: Int): HtmlElement = {
    val allowedSteps = List(1, 2, 3, 5, 10)
    val stepVar = Var(initialStep)
    val diffBus = new EventBus[Int]
    val countSignal: Signal[Int] = diffBus.events.scanLeft(initial = 0)(_ + _)

    div(
      cls("Counter"),
      p(
        "Step: ",
        select(
          value <-- stepVar.signal.map(_.toString),
          onChange.mapToValue.map(_.toInt) --> stepVar,
          allowedSteps.map { step => option(value := step.toString, step) }
        )
      ),
      p(
        label + ": ",
        b(child.text <-- countSignal),
        " ",
        // Two different ways to get stepVar's value:
        button("–", onClick.mapTo(-1 * stepVar.now()) --> diffBus),
        button("+", onClick.compose(_.sample(stepVar.signal)) --> diffBus)
      )
    )
  }

  def main(args: Array[String]): Unit = {
    val root = Counter(label = "Count", initialStep = 1)

    val container = dom.document.querySelector("#root")

    render(container, root)
  }
}
