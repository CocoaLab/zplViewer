/*
 * Data modifica           : $Date: 2013-04-19 22:57:40 +0200 (ven, 19 apr 2013) $
 * Revisione numero        : $Revision: 447 $
 * Autore ultima modifica  : $Author: Admin $
 * Indirizzo repository    : $HeadURL: file:///N:/CodeRepository/Package2007/ZPLViewer/src/zplviewer/PrintPreview.java $
 * Marca                   : $Id: PrintPreview.java 447 2013-04-19 20:57:40Z Admin $
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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
//--------------------------------------------/
import java.util.Vector;
//--------------------------------------------/
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
//--------------------------------------------/
/**
 * Add print preview functionality to any Swing Text component.
 *
 * <p>When active, the print preview frame temporary replaces on-screen the 
 * text component's window.</p>
 * 
 * <p>Keyboard shortcuts are available for zooming print preview images in and
 * out and for invoking the standard print dialog for the text component.</p>
 */
//--------------------------------------------/
public class PrintPreview {
	//--------------------------------------------/
	// Container hosting the text component
	//--------------------------------------------/
	private Container textHost = null;
	//--------------------------------------------/
	// Pane holding the print preview images
	//--------------------------------------------/
	private final JPanel previewPane;
	//--------------------------------------------/
	// Background thread for generating the print preview images
	//--------------------------------------------/
	private Thread previewThread = null;
	//--------------------------------------------/
	// Distance between images in the print preview pane
	//--------------------------------------------/
	private static final int GAP = 5;
	//--------------------------------------------/
	// Scale factor for the print preview images
	//--------------------------------------------/
	private double previewScale = 1.0;
	//--------------------------------------------/
	int paperWidth  = 800;
	int paperHeight = 600;
	//--------------------------------------------/
	void setPaperSize(String width, String height) {
		paperWidth = new Integer(width);
		paperHeight = new Integer(height);
	}
	//--------------------------------------------/
	void setScale(String scale) {
		previewScale = new Double(scale) / 100.0;
	}
	//--------------------------------------------/
	private void generatePreview(Vector<Label> labels) {
		//--------------------------------------------/
		for (Label label : labels) {
			//--------------------------------------------/
			if (label == null) {
				continue;
			}
			//--------------------------------------------/
			if (label.fields.isEmpty()) {
				continue;
			}
			//--------------------------------------------/
			final Render2DLabel img = new Render2DLabel(label, paperWidth, paperHeight);
			//--------------------------------------------/
			img.render();
			//--------------------------------------------/
			final Image image = img.getImage();
			//--------------------------------------------/
			SwingUtilities.invokeLater(new Runnable() {
				//--------------------------------------------/
				public void run() {
					addPage(image);
				}
			});
		}
	}
	//--------------------------------------------/
	// Scale the generated image and add it to the print preview pane
	//--------------------------------------------/
	private void addPage(final Image pageImage) {
		//--------------------------------------------/
		// Create a component representing the scaled page preview image
		//--------------------------------------------/
		JPanel p = new JPanel() {
			//--------------------------------------------/
			Image scaled = null;
			//--------------------------------------------/
			Dimension d = new Dimension();
			//--------------------------------------------/
			// Return preview dimensions using the current scale factor
			//--------------------------------------------/
			public Dimension getPreferredSize() {
				//--------------------------------------------/
				int w = (int) (pageImage.getWidth(null) * previewScale);
				int h = (int) (pageImage.getHeight(null) * previewScale);
				//--------------------------------------------/
				Log.out("Current size width: " + w + " height " + h);
				//--------------------------------------------/
				if ((d.width != w) || (d.height != h)) {
					//--------------------------------------------/
					// Cached dimensions are invalid, re-scale the image
					//--------------------------------------------/
					scaled = pageImage.getScaledInstance(d.width = w, d.height = h, Image.SCALE_SMOOTH);
					setPreferredSize(new Dimension(d.width + 2 * GAP, d.height + 2 * GAP));
				}
				return super.getPreferredSize();
			}
			//--------------------------------------------/
			// Minimum preview size is the same as the preferred size
			//--------------------------------------------/
			@Override
			public Dimension getMinimumSize() {
				return getPreferredSize();
			}
			//--------------------------------------------/
			// Maximum preview size is the same as the preferred size
			//--------------------------------------------/
			@Override
			public Dimension getMaximumSize() {
				return getPreferredSize();
			}
			//--------------------------------------------/
			// Clear the drawing area and paint the scaled image
			//--------------------------------------------/
			@Override
			public void paintComponent(Graphics g) {
				//--------------------------------------------/
				g.setColor(previewPane.getBackground());
				g.fillRect(0, 0, d.width + 2 * GAP, d.height + 2 * GAP);
				g.setColor(Color.WHITE);
				g.fillRect(GAP, GAP, d.width, d.height);
				if (scaled != null) {
					g.drawImage(scaled, GAP, GAP, d.width, d.height, null);
				}
			}
		};
		//--------------------------------------------/
		// Add scaled image to the print preview pane
		//and re-layout the pane
		//--------------------------------------------/
		previewPane.removeAll();
		previewPane.add(p);
		previewPane.revalidate();
		previewPane.repaint();
	}
	//--------------------------------------------/
	// The PrintPreview public API methods.
	//--------------------------------------------/
	public void showPreview(Vector<Label> labels) {
		//--------------------------------------------/
		if (labels == null) {
			throw new IllegalArgumentException("text component required");
		}
		//--------------------------------------------/
		if (previewThread == null) {
			generatePreview(labels);
		}
	}
	//--------------------------------------------/
	public void closePreview() {
		if (previewThread != null) {
			previewThread.interrupt();
			previewThread = null;
		}
	}
	//--------------------------------------------/
	public void setScale(double scale) {
		this.previewScale = scale;
		previewPane.revalidate();
		previewPane.repaint();
	}
	//--------------------------------------------/
	public PrintPreview(JPanel parent) {
		previewPane = parent;
		previewPane.setLayout(new BoxLayout(previewPane, BoxLayout.Y_AXIS));

	}
}
