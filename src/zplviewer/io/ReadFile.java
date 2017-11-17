/*
 * Data modifica           : $Date: 2013-04-19 22:57:40 +0200 (ven, 19 apr 2013) $
 * Revisione numero        : $Revision: 447 $
 * Autore ultima modifica  : $Author: Admin $
 * Indirizzo repository    : $HeadURL: file:///N:/CodeRepository/Package2007/ZPLViewer/src/zplviewer/io/ReadFile.java $
 * Marca                   : $Id: ReadFile.java 447 2013-04-19 20:57:40Z Admin $
 *
 * Autore codice originale : $Coder: Giuseppe Pier Paolo Ucchino $
 * Nota                    : $Property: Il codice e' di esclusiva proprieta' di Giuseppe Pier Paolo Ucchino. Ogni uso non autorizzato esplicitamente e' vietato $
 * Copyright               : $Copyright: Giuseppe Pier Paolo Ucchino(c) $
 *
 */
//--------------------------------------------/
package zplviewer.io;
//--------------------------------------------/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
//--------------------------------------------/
import zplviewer.Log;
//--------------------------------------------/
//This program reads a text file line by line
//and print to the console. It uses FileOutputStream
//to read the file.
//--------------------------------------------/
public class ReadFile {
	//--------------------------------------------/
	public static StringBuilder read_file_builder(String file_name) {
		//--------------------------------------------/
		File file = new File(file_name);
		//--------------------------------------------/
		FileReader fis = null;
		//--------------------------------------------/
		BufferedReader bis = null;
		//--------------------------------------------/
		StringBuilder res = new StringBuilder();
		//--------------------------------------------/
		try {
			//--------------------------------------------/
			fis = new FileReader(file);
			bis = new BufferedReader(fis);
			//--------------------------------------------/
			int len = 10;
			//--------------------------------------------/
			char buff[] = new char[(int) Math.min(len, file.length())];
			//--------------------------------------------/
			while (bis.ready()) {
				len = bis.read(buff);
				res.append(buff, 0, len);
			}
			//--------------------------------------------/
			fis.close();
			bis.close();
			//--------------------------------------------/
		} catch (Exception reason) {
			Log.out(reason.getLocalizedMessage());
		}
		//--------------------------------------------/
		return res;
	}
	//--------------------------------------------/
	public static String read_file(String file_name) {
		return read_file_builder(file_name).toString();
	}
}