package demo.helloworld.snippet 

import _root_.net.liftweb._
import http._
import mapper._
import S._
import SHtml._
import common._
import util._
import Helpers._
import _root_.scala.xml.{NodeSeq, Text, Group}
import _root_.java.util.Locale

class HelloWorld { 
	def lang(xhtml: Group):NodeSeq = {
	bind("showLoc", xhtml,
		"lang" -> locale.getDisplayLanguage(locale),
		"select" -> selectObj(locales.map(lo => (lo, lo.getDisplayName)),
							  definedLocale, setLocale))
	}
	private def locales =
	Locale.getAvailableLocales.toList.sort(_.getDisplayName < _.getDisplayName)

	private def setLocale(loc: Locale) = definedLocale(Full(loc))
  }

  object definedLocale extends SessionVar[Box[Locale]](Empty)
