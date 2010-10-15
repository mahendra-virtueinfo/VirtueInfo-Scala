package bootstrap.liftweb

import net.liftweb._
import java.util.{ResourceBundle}
import util._
import Helpers._
import common._
import http._
import provider._
import sitemap._
import Loc._
import S._
import java.util.Locale
/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
	def boot {

	  // where to search snippet 
	  LiftRules.addToPackages("demo.helloworld")
	  val entries = List(Menu.i("Home") / "index")
	  LiftRules.setSiteMap(SiteMap(entries:_*)) 
	  LiftRules.early.append(makeUtf8)
	  LiftRules.resourceNames = "messages" :: Nil
	  LiftRules.localeCalculator = localeCalculator _
	  LiftRules.resourceBundleFactories prepend {
		case ("messages", locale) => ResourceBundle.getBundle("messages", locale)
	  }	

}
def localeCalculator(request : Box[HTTPRequest]): Locale = 
      request.flatMap(r => {
        def localeCookie(in: String): HTTPCookie = HTTPCookie("helloworld",Full(in),Full(S.hostName),Full(S.contextPath),Full(2629743),Empty,Empty)
        def localeFromString(in: String): Locale = { val x = in.split("_").toList; new Locale(x.head,x.last)}
        def calcLocale: Box[Locale] = 
          S.findCookie("helloworld").map(
            _.value.map(localeFromString)
          ).openOr(Full(LiftRules.defaultLocaleCalculator(request)))
        S.param("locale") match {
          case Full(null) => calcLocale
          case f@Full(selectedLocale) => 
            S.addCookie(localeCookie(selectedLocale))
            tryo(localeFromString(selectedLocale))
          case _ => calcLocale
        }
      }).openOr(Locale.getDefault())

	private def makeUtf8(req: HTTPRequest): Unit = {req.setCharacterEncoding("UTF-8")}
}
