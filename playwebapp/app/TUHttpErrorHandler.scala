import play.api.Logger
import play.api.http.HttpErrorHandler
import play.api.mvc.{RequestHeader, Result, Results}

import scala.concurrent.Future

/**
  * Created by alexanderdemin on 24/02/2017.
  */
class TUHttpErrorHandler extends HttpErrorHandler {

  val logger = Logger(this.getClass)

  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    logger.error(message)
    Future.successful(
      Results.Status(statusCode)("A client error occurred: " + message)
    )
  }

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    logger.error(exception.getMessage, exception)
    val message = exception.getMessage
    if (message == null) {
      Future.successful(Results.InternalServerError("Something wrong happened"))
    } else {
      Future.successful(Results.InternalServerError(message))
    }
  }
}
