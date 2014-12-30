package autowire

import scala.concurrent.Future
import autowire._
import scala.util.Properties
import utest._
import upickle._

//This test should only be run in 2.11+ since 2.10 will not enforce this annotation
object CompileTimeOnlyTests extends TestSuite{
  import scala.concurrent.ExecutionContext.Implicits.global
  // client-side implementation, and call-site
  object MyClient extends autowire.Client[String, upickle.Reader, upickle.Writer]{
    def write[Result: Writer](r: Result) = upickle.write(r)
    def read[Result: Reader](p: String) = upickle.read[Result](p)
    override def doCall(req: Request) = {
      Future(req.args("x"))
    }
  }

  trait MyApi {
    def id(x: Int) : Int
  }

 val tests = TestSuite{
   'compileTimeOnly {
//TODO Re-eanable when compileError runs late enough in the compilation process to catch this
//    val x = compileError("MyClient[MyApi].id(10)")
//    assert(x.msg.contains("You have forgotten to append .call() to the end of an autowire call."))
   }
 }
}