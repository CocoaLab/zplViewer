/*
 * Data modifica           : $Date: 2013-04-19 22:57:40 +0200 (ven, 19 apr 2013) $
 * Revisione numero        : $Revision: 447 $
 * Autore ultima modifica  : $Author: Admin $
 * Indirizzo repository    : $HeadURL: file:///N:/CodeRepository/Package2007/ZPLViewer/src/zplviewer/Label.java $
 * Marca                   : $Id: Label.java 447 2013-04-19 20:57:40Z Admin $
 *
 * Autore codice originale : $Coder: Giuseppe Pier Paolo Ucchino $
 * Nota                    : $Property: Il codice e' di esclusiva proprieta' di Giuseppe Pier Paolo Ucchino. Ogni uso non autorizzato esplicitamente e' vietato $
 * Copyright               : $Copyright: Giuseppe Pier Paolo Ucchino(c) $
 *
 */
//--------------------------------------------/
package zplviewer;
//--------------------------------------------/
import java.util.Vector;
//--------------------------------------------/
public class Label {
	//--------------------------------------------/
	public static class XY {
		//--------------------------------------------/
		public int x;
		public int y;
		//--------------------------------------------/
		public XY(int x, int y) {
			this.x = x;
			this.y = y;
		}
		//--------------------------------------------/
		public XY() {
			x = 0;
			y = 0;
		}
		//--------------------------------------------/
		public String toString() {
			//--------------------------------------------/
			String res = new String();
			//--------------------------------------------/
			res = "x: " + x + " y: " + y;
			//--------------------------------------------/
			return res;
		}
	}
	//--------------------------------------------/
	public static class Font {
		//--------------------------------------------/
		public String name = new String();
		//--------------------------------------------/
		public String orientation = new String();
		//--------------------------------------------/
		public String height = new String();
		public String width  = new String();
		//--------------------------------------------/
		public Font clone() {
			//--------------------------------------------/
			Font font = new Font();
			//--------------------------------------------/
			font.name        = new String(name);
			font.orientation = new String(orientation);
			font.height      = new String(height);
			font.width       = new String(width);
			//--------------------------------------------/
			return font;
		}
	}
	//--------------------------------------------/
	public static class BarcodeDefaults {
		public String width  = new String();
		public String narrow = new String();
		public String height = new String();
	}
	//--------------------------------------------/
	public static class GraphicalBox {
		//--------------------------------------------/
		public int width;
		public int height;
		public int thickness;
		public char color;
		//--------------------------------------------/
		public GraphicalBox() {
			//--------------------------------------------/
			width  = 1;
			height = 1;
			//--------------------------------------------/
			thickness = 1;
			//--------------------------------------------/
			color = 'B';
		}
	}
	//--------------------------------------------/
	public static class Field extends XY {
		//--------------------------------------------/
		public String data = new String();
		//--------------------------------------------/
		public Font font = new Font();
		//--------------------------------------------/
		public BarcodeDefaults bd = new BarcodeDefaults();
		//--------------------------------------------/
		public String code128_orientation     = new String();
		public String code128_height          = new String();
		public String code128_interpolation   = new String();
		public String code128_ucc_check_digit = new String();
		public String code128_mode            = new String();
		//--------------------------------------------/
		public GraphicalBox gb = null;
		//--------------------------------------------/
		public String code39_orientation       = new String();
		public String code39_height            = new String();
		public String code39_interpolation     = new String();
		public String code39_mod43_check_digit = new String();
		//--------------------------------------------/
		public Boolean isSerialsation    = false;
		public Boolean isGraphicalSymbol = false;
		public Boolean isCode128         = false;
		public Boolean isCode39          = false;
		//--------------------------------------------/
		public Field(int xx, int yy) {
			super(xx, yy);
		}
		//--------------------------------------------/
		public String toString() {
			//--------------------------------------------/
			String res = new String();
			//--------------------------------------------/
			res += super.toString() + " data '" + data + "'";
			//--------------------------------------------/
			return res;
		}
	}
	//--------------------------------------------/
	public XY LabelHome = new XY();
	//--------------------------------------------/
	public Vector<Field> fields = new Vector<Field>();
	//--------------------------------------------/
	public Font currentFont = new Font();
	//--------------------------------------------/
	public BarcodeDefaults bd = new BarcodeDefaults();
	//--------------------------------------------/
	public Label() {
	}
	//--------------------------------------------/
	public void setLabelHome(XY xy) {
		LabelHome = xy;
	}
	//--------------------------------------------/
	public void addField(Field field) {
		fields.add(field);
	}
	//--------------------------------------------/
	public String toString() {
		//--------------------------------------------/
		String res = new String();
		//--------------------------------------------/
		if (LabelHome != null) {
			res += "LH " + LabelHome.toString() + "\n";
		}
		//--------------------------------------------/
		for (Field field : fields) {
			res += "FO " + field.toString() + "\n";
		}
		//--------------------------------------------/
		return res;
	}
}
