package cn.DynamicGraph.Version

trait FilterVersion {
  def isOk(s:Long,e: Long): Boolean
}

//case class less()

case class NoneFilter() extends FilterVersion{
  override def isOk(s: Long, e: Long): Boolean = true
}

case class AtFilter(version: Long) extends FilterVersion{
  override def isOk(s: Long, e: Long): Boolean = s<= version && e>= version
}
case class LessThanFilter(version: Long) extends FilterVersion{
  override def isOk(s: Long, e: Long): Boolean = e<version
}
case class LessThanorEqualFilter(version: Long) extends FilterVersion{
  override def isOk(s: Long, e: Long): Boolean = e<=version
}
case class GreaterThanFilter(version: Long) extends FilterVersion{
  override def isOk(s: Long, e: Long): Boolean = s>version
}
case class GreaterThanorEqualFilter(version: Long) extends FilterVersion{
  override def isOk(s: Long, e: Long): Boolean = s>=version
}

case class AndFilter(left: FilterVersion,right: FilterVersion) extends FilterVersion{
  override def isOk(s: Long, e: Long): Boolean = left.isOk(s,e) && right.isOk(s,e)
}
case class AndsFilter(filters: Set[FilterVersion]) extends FilterVersion{
  override def isOk(s: Long, e: Long): Boolean = filters.map(_.isOk(s,e)).reduce(_&_)
}
case class OrFilter(left: FilterVersion,right: FilterVersion) extends FilterVersion{
  override def isOk(s: Long, e: Long): Boolean = left.isOk(s,e) || right.isOk(s,e)
}
case class OrsFilter(filters: Set[FilterVersion]) extends FilterVersion{
  override def isOk(s: Long, e: Long): Boolean = filters.map(_.isOk(s,e)).reduce(_|_)
}
