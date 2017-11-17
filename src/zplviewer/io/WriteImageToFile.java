/*
 * Data modifica           : $Date: 2013-04-19 22:57:40 +0200 (ven, 19 apr 2013) $
 * Revisione numero        : $Revision: 447 $
 * Autore ultima modifica  : $Author: Admin $
 * Indirizzo repository    : $HeadURL: file:///N:/CodeRepository/Package2007/ZPLViewer/src/zplviewer/io/WriteImageToFile.java $
 * Marca                   : $Id: WriteImageToFile.java 447 2013-04-19 20:57:40Z Admin $
 *
 * Autore codice originale : $Coder: Giuseppe Pier Paolo Ucchino $
 * Nota                    : $Property: Il codice e' di esclusiva proprieta' di Giuseppe Pier Paolo Ucchino. Ogni uso non autorizzato esplicitamente e' vietato $
 * Copyright               : $Copyright: Giuseppe Pier Paolo Ucchino(c) $
 *
 */
//--------------------------------------------/
package zplviewer.io;
//--------------------------------------------/
import java.io.*;
//--------------------------------------------/
import java.awt.image.RenderedImage;
//--------------------------------------------/
import javax.imageio.ImageIO;
//--------------------------------------------/
import zplviewer.Log;
//--------------------------------------------/
public class WriteImageToFile {
	//--------------------------------------------/
	public static Boolean WriteImage(RenderedImage img, String format, String filename) {
		try {
			ImageIO.write(img, format, new File(filename));
			return true;
		} catch (IOException reason) {
			Log.out(reason.getLocalizedMessage());
			return false;
		}
	}
}
