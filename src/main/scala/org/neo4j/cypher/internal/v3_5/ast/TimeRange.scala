package org.neo4j.cypher.internal.v3_5.ast

import org.neo4j.cypher.internal.v3_5.expressions.Expression
import org.neo4j.cypher.internal.v3_5.util.{ASTNode, InputPosition}

case class TimeRange(expression: Expression)(val position: InputPosition) extends ASTNode with ASTSlicingPhrase {
  override def name = "TimeRange" // ASTSlicingPhrase name
}

case class After(expression: Expression)(val position: InputPosition) extends ASTNode with ASTSlicingPhrase {
  override def name = "After" // ASTSlicingPhrase name
}

case class Before(expression: Expression)(val position: InputPosition) extends ASTNode with ASTSlicingPhrase {
  override def name = "Before" // ASTSlicingPhrase name
}
case class TwoParams(expression: Expression)(val position: InputPosition) extends ASTNode with ASTSlicingPhrase {
  override def name = "TwoParams" // ASTSlicingPhrase name
}
case class Between(expression: Expression)(val position: InputPosition) extends ASTNode with ASTSlicingPhrase {
  override def name = "Between" // ASTSlicingPhrase name
}

