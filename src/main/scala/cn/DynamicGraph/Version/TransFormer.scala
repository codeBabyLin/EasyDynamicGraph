package cn.DynamicGraph.Version

import org.neo4j.cypher.internal.ir.v3_5.Predicate
import org.neo4j.cypher.internal.v3_5.expressions.{Ands, Expression, LessThanOrEqual, Ors}
import org.neo4j.cypher.internal.v3_5.expressions._
object TransFormer {

  def transFormer(p: Predicate): FilterVersion = {
    transFormer(p.expr)
  }
  def transFormer(p: Expression):FilterVersion = {
    p match {
      case Ors(exprs) => OrsFilter(exprs.map(transFormer))
      case Ands(exprs) => AndsFilter(exprs.map(transFormer))
      case Equals(lhs, rhs) =>
        val ver:Long = rhs.asInstanceOf[SignedDecimalIntegerLiteral].value
        AtFilter(ver)
      case LessThan(lhs, rhs) =>
        val ver:Long = rhs.asInstanceOf[SignedDecimalIntegerLiteral].value
        LessThanFilter(ver)
      case LessThanOrEqual(lhs, rhs) =>
        val ver:Long = rhs.asInstanceOf[SignedDecimalIntegerLiteral].value
        LessThanorEqualFilter(ver)
      case GreaterThan(lhs, rhs) =>
        val ver:Long = rhs.asInstanceOf[SignedDecimalIntegerLiteral].value
        GreaterThanFilter(ver)
      case GreaterThanOrEqual(lhs, rhs) =>
        val ver:Long = rhs.asInstanceOf[SignedDecimalIntegerLiteral].value
        GreaterThanorEqualFilter(ver)
      case x:SignedDecimalIntegerLiteral => AtFilter(x.value)
    }
  }
  def transFormer(p: Set[Predicate]):FilterVersion = {
    if(p.isEmpty) NoneFilter()
    else{
     val size = p.size
      if(size == 1) transFormer(p.head)
      else if (size == 2) AndFilter(transFormer(p.head),transFormer(p.last))
      else AndsFilter(p.map(transFormer))
   }
  }

}
