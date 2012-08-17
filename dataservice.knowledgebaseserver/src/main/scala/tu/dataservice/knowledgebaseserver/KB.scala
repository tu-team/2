package tu.dataservice.knowledgebaseserver

/**
 * @author ${user.name}
 */
object KB {

  def foo(x: Array[String]) = x.foldLeft("")((a, b) => a + b)

  def main(args: Array[String]) {
    println("Hello World!")
    println("concat arguments = " + foo(args))
  }

}
