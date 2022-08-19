package assignment1;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.lang.*;
import java.util.Random;

import javax.swing.*;
import com.itextpdf.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;


public class GUI implements ActionListener{
	JFrame window;
	JTextArea textArea;
	JScrollPane scrollPane;
	JMenuBar menuBar;
	JMenu menuFile, menuEdit, menuHelp, menuPrint;
	JMenuItem
	iNew, iOpen, iSave, iSearch, iSaveAs,iPrint, //items for File menu
	iSelect, iCopy, iPaste, iCut,	// items for Edit menu
	iAbout;	// items for help menu 
	GUI()
	{
		window = new JFrame();  
        
        createWindow();
		createTextArea();
		createMenuBar();
		createFileItem();
		createSCPC();
		createAbout();

		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedDate = dateTime.format(format);
		window.setTitle(formattedDate);

		window.setVisible(true);
	}

	public static void main(String[] args) {
		new GUI();
	}

	public void createWindow() {
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDate = myDateObj.format(myFormatObj);

		window.setSize(800,600);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void createTextArea() {
		textArea = new JTextArea();
		scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		window.add(scrollPane);
	}
	
	public void createMenuBar() {
		menuBar = new JMenuBar();
		window.setJMenuBar(menuBar);
		
		menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		menuEdit = new JMenu("Edit");
		menuBar.add(menuEdit);
		
		menuHelp = new JMenu("Help");
		menuBar.add(menuHelp);
	}
	
	public void createFileItem() {
		iSaveAs = new JMenuItem("save as PDF");
		iSaveAs.addActionListener(this);
		menuFile.add(iSaveAs);
		
		
		iPrint = new JMenuItem("print");
		iPrint.addActionListener(this);
		menuFile.add(iPrint);
	}
	
	public void createSCPC() {
		iSelect = new JMenuItem("select all");
		iSelect.addActionListener(this);
		menuEdit.add(iSelect);
		
		iCopy = new JMenuItem("copy");
		iCopy.addActionListener(this);
		menuEdit.add(iCopy);
		
		iPaste = new JMenuItem("paste");
		iPaste.addActionListener(this);
		menuEdit.add(iPaste);
		
		iCut = new JMenuItem("cut");
		iCut.addActionListener(this);
		menuEdit.add(iCut);
	}
	
	public void createAbout() {
		iAbout = new JMenuItem("About");
		menuHelp.add(iAbout);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JMenuItem item = (JMenuItem) e.getSource();
		if ( item == iSaveAs ) {
			String text = textArea.getText();
			Document doc = new Document();
			File file = new File("C:\\Users\\MyName\\filename.Pdf"); //file path here
			try {
				PdfWriter.getInstance(doc, new FileOutputStream(file));
				doc.open();
				doc.add(new Paragraph(text));
				System.out.println(file + " file created successfully.");
			} catch (DocumentException | FileNotFoundException ex) {
				ex.printStackTrace();
			} finally {
				doc.close();
			}
		}
		else if ( item == iSelect ) {
			textArea.selectAll();
		}
		else if ( item == iCopy ) {
			textArea.copy();
		}
		else if ( item == iPaste ) {
			textArea.paste();
		}
		else if ( item == iCut ) {
			textArea.cut();
		}
	}
}
