/*
 * Data modifica           : $Date: 2013-04-19 22:57:40 +0200 (ven, 19 apr 2013) $
 * Revisione numero        : $Revision: 447 $
 * Autore ultima modifica  : $Author: Admin $
 * Indirizzo repository    : $HeadURL: file:///N:/CodeRepository/Package2007/ZPLViewer/src/zplviewer/ZPLViewer.java $
 * Marca                   : $Id: ZPLViewer.java 447 2013-04-19 20:57:40Z Admin $
 *
 * Autore codice originale : $Coder: Giuseppe Pier Paolo Ucchino $
 * Nota                    : $Property: Il codice e' di esclusiva proprieta' di Giuseppe Pier Paolo Ucchino. Ogni uso non autorizzato esplicitamente e' vietato $
 * Copyright               : $Copyright: Giuseppe Pier Paolo Ucchino(c) $
 *
 */
//--------------------------------------------/
package zplviewer;
//--------------------------------------------/
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import zplviewer.io.ReadFile;
import zplviewer.javacc.ParseZPL;
import zplviewer.javacc.TokenMgrError;
import zplviewer.javacc.ParseException;
//--------------------------------------------/
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
//--------------------------------------------/
import java.text.SimpleDateFormat;
import java.util.Vector;
//--------------------------------------------/
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
//--------------------------------------------/
public class ZPLViewer extends javax.swing.JFrame {
	//--------------------------------------------/
	private boolean prev_thread_running = false;
	//--------------------------------------------/
	private String filename = new String();
	//--------------------------------------------/
	private String AppTitle = "ZPViewer";
	//--------------------------------------------/
	private PrintPreview preview;
	//--------------------------------------------/
	public final static String LAF_CLASSNAME_TINY = "de.muntjak.tinylookandfeel.TinyLookAndFeel";
	//--------------------------------------------/
	public ZPLViewer() throws Exception {
		//--------------------------------------------/
		UIManager.setLookAndFeel(LAF_CLASSNAME_TINY);
		//--------------------------------------------/
		initComponents();
		//--------------------------------------------/
		center(this);
		//--------------------------------------------/
		//Just for logging
		//--------------------------------------------/
		Log.log = textLog;
		//--------------------------------------------/
		preview = new PrintPreview(prevPanel);
		//--------------------------------------------/
		DocumentListener listener = new DocumentListener() {
			//--------------------------------------------/
			public void changedUpdate(DocumentEvent e) {
				setModified();
			}
			//--------------------------------------------/
			public void insertUpdate(DocumentEvent e) {
				setModified();
			}
			//--------------------------------------------/
			public void removeUpdate(DocumentEvent e) {
				setModified();
			}
		};
		//--------------------------------------------/
		textInput.getDocument().addDocumentListener(listener);
		paperWidth.getDocument().addDocumentListener(listener);
		paperHeight.getDocument().addDocumentListener(listener);
		//--------------------------------------------/
		DocumentListener dotsListener = new DocumentListener() {
			//--------------------------------------------/
			public void changedUpdate(DocumentEvent e) {
				changeDots();
			}
			//--------------------------------------------/
			public void insertUpdate(DocumentEvent e) {
				changeDots();
			}
			//--------------------------------------------/
			public void removeUpdate(DocumentEvent e) {
				changeDots();
			}
		};
		//--------------------------------------------/
		dots_per_mm.getDocument().addDocumentListener(dotsListener);
		width_mm.getDocument().addDocumentListener(dotsListener);
		height_mm.getDocument().addDocumentListener(dotsListener);
	}
    //--------------------------------------------/
    private static Dimension maxWindowSize = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize();
    //--------------------------------------------/
    public static void center(Component toCenter) {
        //--------------------------------------------/
        if(toCenter == null) {
            return;
        }
        //--------------------------------------------/
        int y = (maxWindowSize.height/2) - (toCenter.getHeight()/2);
        int x = (maxWindowSize.width/2)  - (toCenter.getWidth()/2);
        //--------------------------------------------/
        toCenter.setLocation(x, y);
    }
	//--------------------------------------------/
	private void display(Vector<Label> labels) {
		preview.showPreview(labels);
	}
	//--------------------------------------------/
	private void setPaperSize(String width, String height) {
		preview.setPaperSize(width, height);
	}
	//--------------------------------------------/
	private void setScale(String scale) {
		preview.setScale(scale);
	}
	//--------------------------------------------/
	void changeDots() {
		//--------------------------------------------/
		if (dots_per_mm.getText().isEmpty()) {
			return;
		}
		//--------------------------------------------/
		if (width_mm.getText().isEmpty()) {
			return;
		}
		//--------------------------------------------/
		if (height_mm.getText().isEmpty()) {
			return;
		}
		//--------------------------------------------/
		int dots = new Integer(dots_per_mm.getText());
		//--------------------------------------------/
		int res_w = dots * new Integer(width_mm.getText());
		int res_h = dots * new Integer(height_mm.getText());
		//--------------------------------------------/
		paperWidth.setText(new Integer(res_w).toString());
		paperHeight.setText(new Integer(res_h).toString());
	}
	//--------------------------------------------/
	void setModified() {
		//--------------------------------------------/
		if (!prev_thread_running) {
			//--------------------------------------------/
			prev_thread_running = true;
			//--------------------------------------------/
			SwingUtilities.invokeLater(new Runnable() {
				//--------------------------------------------/
				public void run() {
					try {
						ParseZPL parser = new ParseZPL(new StringReader(textInput.getText()));
						parser.parse();
						setPaperSize(paperWidth.getText(), paperHeight.getText());
						setScale("" + scale.getValue());
						display(parser.getLabels());
					} catch (ParseException ex) {
						textLog.setText(ex.toString());
						prev_thread_running = false;
						return;
					} catch (TokenMgrError ex) {
						textLog.setText(ex.toString());
						prev_thread_running = false;
						return;
					} catch (NumberFormatException ex) {
						textLog.setText(ex.toString());
						prev_thread_running = false;
						return;
					}
					//--------------------------------------------/
					textLog.setText(new String());
					prev_thread_running = false;
				}
			});
		}
	}
	//--------------------------------------------/
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        scale = new javax.swing.JSlider();
        jPanel6 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        dots_per_mm = new javax.swing.JTextField();
        height_mm = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        paperHeight = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        paperWidth = new javax.swing.JTextField();
        width_mm = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        textLog = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        textInput = new javax.swing.JTextArea();
        prevPanel = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ZPLViewer");
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setPreferredSize(new java.awt.Dimension(800, 600));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(new java.awt.BorderLayout(3, 3));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "SCALE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), new java.awt.Color(0, 0, 230))); // NOI18N
        jPanel5.setLayout(new java.awt.BorderLayout());

        scale.setFont(new java.awt.Font("Tahoma", 0, 13));
        scale.setMajorTickSpacing(10);
        scale.setMinorTickSpacing(10);
        scale.setPaintLabels(true);
        scale.setPaintTicks(true);
        scale.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                scaleStateChanged(evt);
            }
        });
        jPanel5.add(scale, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel5, java.awt.BorderLayout.PAGE_END);

        jPanel6.setLayout(new java.awt.GridLayout(1, 2, 3, 0));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "DIMENSIONS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), new java.awt.Color(0, 0, 230))); // NOI18N
        jPanel4.setLayout(new java.awt.GridBagLayout());

        dots_per_mm.setColumns(4);
        dots_per_mm.setFont(new java.awt.Font("Tahoma", 0, 13));
        dots_per_mm.setText("8");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        jPanel4.add(dots_per_mm, gridBagConstraints);

        height_mm.setColumns(4);
        height_mm.setFont(new java.awt.Font("Tahoma", 0, 13));
        height_mm.setText("104");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 3);
        jPanel4.add(height_mm, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 13));
        jLabel1.setText("Paper Size");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 3, 3);
        jPanel4.add(jLabel1, gridBagConstraints);

        paperHeight.setColumns(4);
        paperHeight.setFont(new java.awt.Font("Tahoma", 0, 13));
        paperHeight.setText("832");
        paperHeight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paperHeightActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 3);
        jPanel4.add(paperHeight, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 13));
        jLabel5.setText("px");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 3);
        jPanel4.add(jLabel5, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 13));
        jLabel9.setText("mm");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 3);
        jPanel4.add(jLabel9, gridBagConstraints);

        paperWidth.setColumns(4);
        paperWidth.setFont(new java.awt.Font("Tahoma", 0, 13));
        paperWidth.setText("832");
        paperWidth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paperWidthActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 3);
        jPanel4.add(paperWidth, gridBagConstraints);

        width_mm.setColumns(4);
        width_mm.setFont(new java.awt.Font("Tahoma", 0, 13));
        width_mm.setText("104");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 3);
        jPanel4.add(width_mm, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 13));
        jLabel6.setText("px");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 3);
        jPanel4.add(jLabel6, gridBagConstraints);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 13));
        jLabel11.setText("mm");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 3);
        jPanel4.add(jLabel11, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 13));
        jLabel10.setText("Label Size");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 3, 3);
        jPanel4.add(jLabel10, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 13));
        jLabel7.setText("Dots/mm");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        jPanel4.add(jLabel7, gridBagConstraints);

        jPanel6.add(jPanel4);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "ZPL LOG", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 13), new java.awt.Color(0, 0, 230))); // NOI18N
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        textLog.setBackground(new java.awt.Color(204, 204, 204));
        textLog.setColumns(20);
        textLog.setEditable(false);
        textLog.setLineWrap(true);
        textLog.setWrapStyleWord(true);
        jScrollPane2.setViewportView(textLog);

        jPanel7.add(jScrollPane2);

        jPanel6.add(jPanel7);

        jPanel2.add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.SOUTH);

        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        jSplitPane1.setDividerLocation(250);
        jSplitPane1.setDividerSize(7);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        textInput.setColumns(20);
        textInput.setRows(5);
        textInput.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                textInputPropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(textInput);

        jSplitPane1.setLeftComponent(jScrollPane1);

        prevPanel.setBackground(new java.awt.Color(0, 102, 102));

        javax.swing.GroupLayout prevPanelLayout = new javax.swing.GroupLayout(prevPanel);
        prevPanel.setLayout(prevPanelLayout);
        prevPanelLayout.setHorizontalGroup(
            prevPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 509, Short.MAX_VALUE)
        );
        prevPanelLayout.setVerticalGroup(
            prevPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 323, Short.MAX_VALUE)
        );

        jSplitPane1.setRightComponent(prevPanel);

        jPanel3.add(jSplitPane1);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1);

        jMenu1.setText("File");
        jMenu1.setFont(new java.awt.Font("Tahoma", 0, 13));

        jMenuItem1.setFont(new java.awt.Font("Tahoma", 0, 13));
        jMenuItem1.setText("Open");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                open(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setFont(new java.awt.Font("Tahoma", 0, 13));
        jMenuItem2.setText("Save");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents
	//--------------------------------------------/
	private void textInputPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_textInputPropertyChange
		setModified();
	}//GEN-LAST:event_textInputPropertyChange
	//--------------------------------------------/
	private void paperHeightActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paperHeightActionPerformed
		setModified();
	}//GEN-LAST:event_paperHeightActionPerformed
	//--------------------------------------------/
	private void paperWidthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paperWidthActionPerformed
		setModified();
	}//GEN-LAST:event_paperWidthActionPerformed
	//--------------------------------------------/
	private void open(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_open
		//--------------------------------------------/
		JFileChooser fc = createFileChooser("openFileChooser");
		//--------------------------------------------/
		int option = fc.showOpenDialog(this);
		//--------------------------------------------/
		if (JFileChooser.APPROVE_OPTION == option) {
			filename = fc.getSelectedFile().getName();
			textInput.setText(ReadFile.read_file(filename));
			this.setTitle(AppTitle + " " + filename);
		}
	}//GEN-LAST:event_open
    //--------------------------------------------/
    public static String getYearMonthDayTimeForFilesystem() {
        return getDate(new java.util.Date(),"yyyy-MM-dd-HH-mm-ss");
    }
    //--------------------------------------------/
    public static String getDate(java.util.Date date,String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String s = sdf.format(date);
        return s;
    }
	//--------------------------------------------/
	private void save(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save
		//--------------------------------------------/
		if(filename.isEmpty() == true) {
			filename = "Label-" + getYearMonthDayTimeForFilesystem() + ".zpl";
			this.setTitle(AppTitle + " " + filename);
		}
		//--------------------------------------------/
		if (!filename.isEmpty()) {
			//--------------------------------------------/
			FileWriter fw = null;
			//--------------------------------------------/
			try {
				fw = new FileWriter(filename);
				PrintWriter out = new PrintWriter(fw);
				out.print(textInput.getText());
				out.close();
				fw.close();
			} catch (IOException reason) {
				Log.out(reason.getLocalizedMessage());
			} finally {
				try {
					fw.close();
				} catch (IOException reason) {
					Log.out(reason.getLocalizedMessage());
				}
			}
		}
	}//GEN-LAST:event_save
	//--------------------------------------------/
	private void scaleStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_scaleStateChanged
		setModified();
}//GEN-LAST:event_scaleStateChanged
	//--------------------------------------------/
	private JFileChooser createFileChooser(String name) {
		//--------------------------------------------/
		JFileChooser fc = new JFileChooser();
		//--------------------------------------------/
		fc.setDialogTitle("Open Label File");
		//--------------------------------------------/
		String textFilesDesc = "*.zpl; *.lab; *.txt";
		//--------------------------------------------/
		fc.setFileFilter(new TextFileFilter(textFilesDesc));
		return fc;
	}
	//--------------------------------------------/
	private static class TextFileFilter extends FileFilter {
		//--------------------------------------------/
		private final String description;
		//--------------------------------------------/
		TextFileFilter(String description) {
			this.description = description;
		}
		//--------------------------------------------/
		public boolean accept(File f) {
			//--------------------------------------------/
			if (f.isDirectory()) {
				return true;
			}
			//--------------------------------------------/
			String fileName = f.getName();
			//--------------------------------------------/
			int i = fileName.lastIndexOf('.');
			//--------------------------------------------/
			if ((i > 0) && (i < (fileName.length() - 1))) {
				String fileExt = fileName.substring(i + 1);
				if ("lab".equalsIgnoreCase(fileExt)) {
					return true;
				}
				if ("zpl".equalsIgnoreCase(fileExt)) {
					return true;
				}
			}
			return false;
		}
		//--------------------------------------------/
		public String getDescription() {
			return description;
		}
	}
	//--------------------------------------------/
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField dots_per_mm;
    private javax.swing.JTextField height_mm;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextField paperHeight;
    private javax.swing.JTextField paperWidth;
    private javax.swing.JPanel prevPanel;
    private javax.swing.JSlider scale;
    private javax.swing.JTextArea textInput;
    private javax.swing.JTextArea textLog;
    private javax.swing.JTextField width_mm;
    // End of variables declaration//GEN-END:variables
}
