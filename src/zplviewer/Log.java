/*
 * Data modifica           : $Date: 2013-04-19 22:57:40 +0200 (ven, 19 apr 2013) $
 * Revisione numero        : $Revision: 447 $
 * Autore ultima modifica  : $Author: Admin $
 * Indirizzo repository    : $HeadURL: file:///N:/CodeRepository/Package2007/ZPLViewer/src/zplviewer/Log.java $
 * Marca                   : $Id: Log.java 447 2013-04-19 20:57:40Z Admin $
 *
 * Autore codice originale : $Coder: Giuseppe Pier Paolo Ucchino $
 * Nota                    : $Property: Il codice e' di esclusiva proprieta' di Giuseppe Pier Paolo Ucchino. Ogni uso non autorizzato esplicitamente e' vietato $
 * Copyright               : $Copyright: Giuseppe Pier Paolo Ucchino(c) $
 *
 */
//--------------------------------------------/
package zplviewer;
//--------------------------------------------/
import javax.swing.JTextArea;
//--------------------------------------------/
public class Log {
	//--------------------------------------------/
	public static JTextArea log = null;
	//--------------------------------------------/
	public static void out(String logMessage) {
		if(log != null) log.setText(logMessage);
	}
}
