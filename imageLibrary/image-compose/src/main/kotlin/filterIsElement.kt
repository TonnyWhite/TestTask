import nl.adaptivity.xmlutil.dom.Element
import nl.adaptivity.xmlutil.dom.Node

fun Sequence<Node>.filterIsElement(): Sequence<Element> =
    filterIsInstance<Element>()