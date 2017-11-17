/*
 * Data modifica           : $Date: 2013-04-19 22:57:40 +0200 (ven, 19 apr 2013) $
 * Revisione numero        : $Revision: 447 $
 * Autore ultima modifica  : $Author: Admin $
 * Indirizzo repository    : $HeadURL: file:///N:/CodeRepository/Package2007/ZPLViewer/src/zplviewer/Render2DLabel.java $
 * Marca                   : $Id: Render2DLabel.java 447 2013-04-19 20:57:40Z Admin $
 *
 * Autore codice originale : $Coder: Giuseppe Pier Paolo Ucchino $
 * Nota                    : $Property: Il codice e' di esclusiva proprieta' di Giuseppe Pier Paolo Ucchino. Ogni uso non autorizzato esplicitamente e' vietato $
 * Copyright               : $Copyright: Giuseppe Pier Paolo Ucchino(c) $
 *
 */
//--------------------------------------------/
package zplviewer;
//--------------------------------------------/
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
//--------------------------------------------/
import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code128Encoder;
import org.jbarcode.encode.Code39Encoder;
import org.jbarcode.encode.InvalidAtributeException;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.WidthCodedPainter;
//--------------------------------------------/
public class Render2DLabel extends RenderLabel {
	//--------------------------------------------/
	BufferedImage img;
	//--------------------------------------------/
	Graphics2D dc;
	//--------------------------------------------/
	int width;
	int height;
	//--------------------------------------------/
	int paper_width  = 800;
	int paper_height = 600;
	//--------------------------------------------/
	enum Orientation {
		NORMAL,
		ROTATED,
		INVERTED,
		BOTTOM
	}
	//--------------------------------------------/
	public Render2DLabel(Label label, int paper_width, int paper_height) {
		//--------------------------------------------/
		super(label);
		//--------------------------------------------/
		this.paper_height = paper_height;
		this.paper_width  = paper_width;
		//--------------------------------------------/
		width  = paper_width;
		height = paper_height;
		//--------------------------------------------/
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		//--------------------------------------------/
		dc = (Graphics2D) img.getGraphics();
		dc.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
	//--------------------------------------------/
	public Image getImage() {
		return img;
	}
	//--------------------------------------------/
	Font getFontForField(Label.Field field) {
		//--------------------------------------------/
		Font font = null;
		//--------------------------------------------/
		String font_name = dc.getFont().getName();
		//--------------------------------------------/
		double factor = 1;
		//--------------------------------------------/
		int type = Font.PLAIN;
		//--------------------------------------------/
		if (!field.font.name.isEmpty()) {
			if (field.font.name.equals("O")) {
				font_name = "Times";
				factor = 0.7;
			} else if (field.font.name.equals("0")) {
				font_name = "Times";
				factor = 0.848;
				type = Font.BOLD;
			} else {
				font_name = "Serif";
			}
		}
		//--------------------------------------------/
		Log.out("Font name:" + font_name);
		//--------------------------------------------/
		int font_height = dc.getFont().getSize();
		//--------------------------------------------/
		if (!field.font.height.isEmpty()) {
			font_height = (int) (new Integer(field.font.height) * factor);
		}
		//--------------------------------------------/
		font = new Font(font_name, type, font_height);
		//--------------------------------------------/
		return font;
	}
	//--------------------------------------------/
	Boolean renderGraphicalBox(Label.Field field, Boolean simulate) {
		//--------------------------------------------/
		if (field.gb == null) {
			return false;
		}
		//--------------------------------------------/
		int ox = field.x + label.LabelHome.x;
		int oy = field.y + label.LabelHome.y;
		//--------------------------------------------/
		Color fg = Color.BLACK;
		//--------------------------------------------/
		if (field.gb.color == 'B') {
			fg = Color.BLACK;
		} else if (field.gb.color == 'W') {
			fg = Color.WHITE;
		}
		//--------------------------------------------/
		if (!simulate) {
			dc.setColor(fg);
		}
		//--------------------------------------------/
		if (field.gb.width == 0 || field.gb.height == 0) {
			if (field.gb.width == 0) {
				field.gb.width = field.gb.thickness;
			} else if (field.gb.height == 0) {
				field.gb.height = field.gb.thickness;
			}
			//--------------------------------------------/
			if (simulate) {
				if (width < ox + field.gb.width) {
					width = ox + field.gb.width;
				}
				if (height < oy + field.gb.height) {
					height = oy + field.gb.height;
				}
			} else {
				dc.fillRect(ox, oy, field.gb.width, field.gb.height);
			}
		} else {
			//--------------------------------------------/
			for (int i = 0; i < field.gb.thickness; i++) {
				//--------------------------------------------/
				//das müssen wir hier so machen, da die box irgendwo
				//im zebra code kommen kann
				//--------------------------------------------/
				int xx = ox + i;
				int yy = oy + i;
				//--------------------------------------------/
				int w = field.gb.width - (i * 2);
				int h = field.gb.height - (i * 2);
				//--------------------------------------------/
				if (h < 0) {
					h = 1;
				}
				//--------------------------------------------/
				if (w < 0) {
					w = 1;
				}
				//--------------------------------------------/
				if (simulate) {
					if (width < xx + w) {
						width = xx + w;
					}
					if (height < yy + h) {
						height = yy + h;
					}
				} else {
					dc.drawRect(xx, yy, w, h);
				}
			}
		}

		return true;
	}
	//--------------------------------------------/
	public void doSimulation() {
		//--------------------------------------------/
		width  = paper_width;
		height = paper_height;
		//--------------------------------------------/
		for (Label.Field field : label.fields) {
			//--------------------------------------------/
			if (renderGraphicalBox(field, true)) {
				continue;
			}
			//--------------------------------------------/
			if (drawBarcode(field, true)) {
				continue;
			}
			//--------------------------------------------/
			drawText(field, true);
		}
	}
	//--------------------------------------------/
	public void do_graphical_symbol(Label.Field field) {
		if (field.isGraphicalSymbol) {
			if (field.data.equals("A")) {
				field.data = "®";
			} else if (field.data.equals("B")) {
				field.data = "©";
			} else if (field.data.equals("C")) {
				field.data = "\u2122";
			} else if (field.data.equals("D")) {
				field.data = "(UL)";
			} else if (field.data.equals("E")) {
				field.data = "(CA)";
			}

		}
	}
	//--------------------------------------------/
	Orientation getOrientation(String o) {
		//--------------------------------------------/
		Orientation res = Orientation.NORMAL;
		//--------------------------------------------/
		if (o.equals("N")) {
			res = Orientation.NORMAL;
		} else if (o.equals("R")) {
			res = Orientation.ROTATED;
		} else if (o.equals("I")) {
			res = Orientation.INVERTED;
		} else if (o.equals("B")) {
			res = Orientation.BOTTOM;
		} else {
			Log.out("Unknown Orientation: " + res);
		}
		//--------------------------------------------/
		return res;
	}
	//--------------------------------------------/
	public void drawText(Label.Field field, Boolean simulation) {
		//--------------------------------------------/
		Font font = getFontForField(field);
		//--------------------------------------------/
		if (font != null) {
			dc.setFont(font);
		}
		//--------------------------------------------/
		int ox = field.x + label.LabelHome.x;
		int oy = field.y + label.LabelHome.y;
		//--------------------------------------------/
		Orientation o = getOrientation(field.font.orientation);
		//--------------------------------------------/
		double r = 0;
		//--------------------------------------------/
		char arr[] = field.data.toCharArray();
		//--------------------------------------------/
		if (o == Orientation.NORMAL) {
			//--------------------------------------------/
			oy += dc.getFontMetrics().getHeight();
			//--------------------------------------------/
			if (simulation) {
				ox += dc.getFontMetrics().charsWidth(arr, 0, arr.length);
				oy += dc.getFontMetrics().getHeight();
			}
		} else if (o == Orientation.ROTATED) {
			//--------------------------------------------/
			r = Math.toRadians(90);
			//--------------------------------------------/
			if (simulation) {
				oy += dc.getFontMetrics().charsWidth(arr, 0, arr.length);
				ox += dc.getFontMetrics().getHeight();
			}
		} else if (o == Orientation.INVERTED) {
			r = Math.toRadians(180);
			ox += dc.getFontMetrics().charsWidth(arr, 0, arr.length);
		} else if (o == Orientation.BOTTOM) {
			r = Math.toRadians(270);
		}
		//--------------------------------------------/
		if (simulation) {
			//--------------------------------------------/
			if (width < ox) {
				width = ox;
			}
			//--------------------------------------------/
			if (height < oy) {
				height = oy;
			}
		} else {
			//--------------------------------------------/
			AffineTransform rotator = new AffineTransform();
			//--------------------------------------------/
			rotator.rotate(r);
			//--------------------------------------------/
			Font rotateFont = font.deriveFont(rotator);
			//--------------------------------------------/
			dc.setFont(rotateFont);
			//--------------------------------------------/
			Log.out("Drawing '" + field.data + " at: " + ox + "x" + oy + "Height: " + font.getSize());
			//--------------------------------------------/
			dc.drawString(field.data, ox, oy);
		}
	}
	//--------------------------------------------/
	Boolean drawCode128(Label.Field field, Boolean simulate) {
		//--------------------------------------------/
		if (!field.isCode128) {
			return false;
		}
		//--------------------------------------------/
		JBarcode jbc = new JBarcode(Code128Encoder.getInstance(),WidthCodedPainter.getInstance(),BaseLineTextPainter.getInstance());
		//--------------------------------------------/
		double bheight = new Double(field.code128_height);
		//--------------------------------------------/
		if (bheight <= 0) {
			bheight = new Double(field.bd.height);
		}
		//--------------------------------------------/
		double bwidth = 0.2;
		//--------------------------------------------/
		if (!field.bd.width.isEmpty()) {
			bwidth = new Double(field.bd.width) * 0.3;
		}
		//--------------------------------------------/
		if (field.code128_interpolation.equals("N")) {
			jbc.setShowText(false);
		}
		//--------------------------------------------/
		//Moltiplicare perché setBarHeight calcolato in
		//millimetri. Ma noi in pixel
		//--------------------------------------------/
		jbc.setBarHeight(bheight * JBarcode.MIN_XDIMENSION);
		//--------------------------------------------/
		try {
			//--------------------------------------------/
			jbc.setXDimension(bwidth);
			//--------------------------------------------/
			BufferedImage bi = jbc.createBarcode(field.data);
			//--------------------------------------------/
			int x = field.x + label.LabelHome.x;
			int y = field.y + label.LabelHome.y;
			//--------------------------------------------/
			Orientation o = getOrientation(field.code128_orientation);
			//--------------------------------------------/
			if (o == Orientation.BOTTOM) {
				bi = rotate90SX(bi);
			} else if (o == Orientation.ROTATED) {
				bi = rotate90DX(bi);
			}
			//--------------------------------------------/
			if (simulate) {
				//--------------------------------------------/
				if (width < x + bi.getWidth()) {
					width = x + bi.getWidth();
				}
				if (height < y + bi.getHeight()) {
					height = y + bi.getHeight();
				}
			} else {
				dc.drawImage(bi, null, x, y);
			}
		} catch (InvalidAtributeException reason) {
			Log.out(reason.getLocalizedMessage());
		}
		//--------------------------------------------/
		return true;
	}
	//--------------------------------------------/
	Boolean drawCode39(Label.Field field, Boolean simulation) {
		//--------------------------------------------/
		if (!field.isCode39) {
			return false;
		}
		//--------------------------------------------/
		JBarcode jbc = new JBarcode(Code39Encoder.getInstance(),WidthCodedPainter.getInstance(),BaseLineTextPainter.getInstance());
		//--------------------------------------------/
		double bheight = new Double(field.code39_height);
		//--------------------------------------------/
		if (bheight <= 0) {
			bheight = new Double(field.bd.height);
		}
		//--------------------------------------------/
		double bwidth = 0.2;
		//--------------------------------------------/
		if (!field.bd.width.isEmpty()) {
			bwidth = new Double(field.bd.width) * 0.3;
		}
		//--------------------------------------------/
		if (field.code39_interpolation.equals("N")) {
			jbc.setShowText(false);
		}
		//--------------------------------------------/
		//Moltiplicare perché setBarHeight calcolato
		//in millimetri. Ma noi in pixel
		//--------------------------------------------/
		jbc.setBarHeight(bheight * JBarcode.MIN_XDIMENSION);
		//--------------------------------------------/
		try {
			//--------------------------------------------/
			jbc.setXDimension(bwidth);
			//--------------------------------------------/
			BufferedImage bi = jbc.createBarcode(field.data);
			//--------------------------------------------/
			int x = field.x + label.LabelHome.x;
			int y = field.y + label.LabelHome.y;
			//--------------------------------------------/
			Orientation o = getOrientation(field.code39_orientation);
			//--------------------------------------------/
			if (o == Orientation.BOTTOM) {
				bi = rotate90SX(bi);
			} else if (o == Orientation.ROTATED) {
				bi = rotate90DX(bi);
			}
			//--------------------------------------------/
			dc.drawImage(bi, null, x, y);
			//--------------------------------------------/
		} catch (InvalidAtributeException reason) {
			Log.out(reason.getLocalizedMessage());
		}
		//--------------------------------------------/
		return true;
	}
	//--------------------------------------------/
	public BufferedImage rotate90DX(BufferedImage bi) {
		//--------------------------------------------/
		int iwidth  = bi.getWidth();
		int iheight = bi.getHeight();
		//--------------------------------------------/
		BufferedImage biFlip = new BufferedImage(iheight, iwidth, bi.getType());
		//--------------------------------------------/
		for (int i = 0; i < iwidth; i++) {
			for (int j = 0; j < iheight; j++) {
				biFlip.setRGB(iheight - 1 - j, iwidth - 1 - i, bi.getRGB(i, j));
			}
		}
		//--------------------------------------------/
		return biFlip;
	}
	//--------------------------------------------/
	public BufferedImage rotate90SX(BufferedImage bi) {
		//--------------------------------------------/
		int iwidth  = bi.getWidth();
		int iheight = bi.getHeight();
		//--------------------------------------------/
		BufferedImage biFlip = new BufferedImage(iheight, iwidth, bi.getType());
		//--------------------------------------------/
		for (int i = 0; i < iwidth; i++) {
			for (int j = 0; j < iheight; j++) {
				biFlip.setRGB(j, i, bi.getRGB(i, j));
			}
		}
		//--------------------------------------------/
		return biFlip;
	}
	//--------------------------------------------/
	Boolean drawBarcode(Label.Field field, Boolean simulation) {
		//--------------------------------------------/
		if (drawCode128(field, simulation)) {
			return true;
		}
		//--------------------------------------------/
		if (drawCode39(field, simulation)) {
			return true;
		}
		//--------------------------------------------/
		return false;
	}
	//--------------------------------------------/
	public void render() {
		//--------------------------------------------/
		doSimulation();
		//--------------------------------------------/
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		//--------------------------------------------/
		dc = (Graphics2D) img.getGraphics();
		//--------------------------------------------/
		dc.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//--------------------------------------------/
		drawBackground();
		//--------------------------------------------/
		dc.setColor(new Color(0, 0, 0));
		//--------------------------------------------/
		for (Label.Field field : label.fields) {
			//--------------------------------------------/
			if (renderGraphicalBox(field, false)) {
				continue;
			}
			//--------------------------------------------/
			do_graphical_symbol(field);
			//--------------------------------------------/
			if (drawBarcode(field, false)) {
				continue;
			}
			//--------------------------------------------/
			drawText(field, false);
		}
		//--------------------------------------------/
		dc.dispose();
	}
	//--------------------------------------------/
	void drawBackground() {
		dc.setColor(new Color(120, 120, 120, 255));
		dc.fillRect(0, 0, width, height);
		dc.setColor(Color.white);
		dc.fillRect(0, 0, paper_width, paper_height);
	}
}
