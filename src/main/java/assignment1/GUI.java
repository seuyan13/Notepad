/*
 * 159251 Software Engineering Design and Construction
 * Assignment 1 
 * Due date: 28 August
 * =======================================
 * Student Name - Student ID
 * 	Bex Ellery - 20013588
 *  Seungwoon Yang - 21008279
 */


package assignment1;
import java.awt.Color;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.lang.*;
import java.util.Arrays;
import java.util.Random;
import com.itextpdf.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

import java.io.*;

public class GUI implements ActionListener{
	JFrame window;
	JTextArea textArea;
	JScrollPane scrollPane;
	JMenuBar menuBar;
	JMenu menuFile, menuEdit, menuHelp, menuView;
	JMenuItem
	add, open, save, exit, iSearch, iTD ,iSaveAs,iPrint, //items for File menu
	iSelect, iCopy, iPaste, iCut,	// items for Edit menu
	iAbout;	// items for help menu 
	JTextField searchBar;//search
	JButton searchButton;
	GUI()
	{
		window = new JFrame();  
        
        createWindow();
		createTextArea();
		createMenuBar();
		createFileItem();
		createSCPC();
		createAbout();

		window.setTitle("159251 - Assignment 1 - Bex, Seungwoon");

		window.setVisible(true);
	}

	public static void main(String[] args) {
		new GUI();
	}

	//create window
	public void createWindow() {
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDate = myDateObj.format(myFormatObj);

		window.setSize(800,600);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	//create text area, enable scroll bar
	public void createTextArea() {
		textArea = new JTextArea();
		scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		window.add(scrollPane);
	}
	
	// create menu bar that has File, Edit, Help, View item
	public void createMenuBar() {
		menuBar = new JMenuBar();
		window.setJMenuBar(menuBar);
		
		menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		menuEdit = new JMenu("Edit");
		menuBar.add(menuEdit);
		
		menuHelp = new JMenu("Help");
		menuBar.add(menuHelp);
		
		menuView = new JMenu("View");
		menuBar.add(menuView);
	}
	
	// File menu has New, Open, Save, Exit, Save as PDF, Print items
	public void createFileItem() {
		add = new JMenuItem("New");
		add.addActionListener(this);
		menuFile.add(add);
		
		open=new JMenuItem("Open");
		open.addActionListener(this);
		menuFile.add(open);
		
		save=new JMenuItem("Save");
		save.addActionListener(this);
		menuFile.add(save);
		
		exit=new JMenuItem("Exit");
		exit.addActionListener(this);
		menuFile.add(exit);
		
		iSaveAs = new JMenuItem("save as PDF");
		iSaveAs.addActionListener(this);
		menuFile.add(iSaveAs);
		
		iPrint = new JMenuItem("print");
		iPrint.addActionListener(this);
		menuFile.add(iPrint);
	}
	
	//Edit menu has select all, copy, paste, cut, search, time/date items
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

		iSearch = new JMenuItem("Search");
		iSearch.addActionListener(this);
		menuEdit.add(iSearch);
		
		iTD =  new JMenuItem("Time/Date");
		iTD.addActionListener(this);
		menuEdit.add(iTD);
	}
	
	//Help menu has About item 
	public void createAbout() {
		iAbout = new JMenuItem("About");
		iAbout.addActionListener(this);
		menuHelp.add(iAbout);
	}

	String getSaveFilePath(String title, FileNameExtensionFilter filter) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle(title);
		if ( filter != null ) {
			fileChooser.setFileFilter(filter);
		}

		int userSelection = fileChooser.showSaveDialog(this.window);

		if ( userSelection == JFileChooser.APPROVE_OPTION ) {
			File fileToSave = fileChooser.getSelectedFile();
			// System.out.println("Save as file: " + fileToSave.getAbsolutePath());
			return fileToSave.getAbsolutePath();
		}
		return null;
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		// Time and Date format
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDate = myDateObj.format(myFormatObj);
		
		//About function displays the popup box with message
		JMenuItem item = (JMenuItem) e.getSource();
		String messageBox = "Student Info (Name - ID): \n" + "1. Bex Ellery - 20013588\n" + "2. Seungwoon Yang - 21008279";
		
		//Save as PDF function
		if ( item == iSaveAs ) {
			String filePath = getSaveFilePath("Save as PDF",
					new FileNameExtensionFilter("PDF(Portable Document Format)", "pdf"));
			if ( filePath != null ) {
				String text = textArea.getText();
				Document doc = new Document();
				// File file = new File("C:\\Users\\File_Path_Here\\File_Name_Here.Pdf"); //file path here

				int lastIndexOf = filePath.lastIndexOf(".");
				if ( lastIndexOf == -1 ) {
					// the filename doesn't have file extension, so add '.pdf' as default.
					filePath += ".pdf";
				}
				File file = new File(filePath);
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
			else {
				System.out.println("'Save as PDF' was canceled or failed.");
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
		else if (item == iAbout) {
			JOptionPane.showMessageDialog(null, messageBox); //PopUp Box
		}
		else if (item == add) {
			new GUI();
		}
		else if  (item == open) {
			OpenDocument();
		}
		else if (item == save) {
			SaveDocument();
		}
		// Time and Date
		else if(item == iTD) {
			textArea.setText("");
			textArea.setText(formattedDate);
		}
		else if ( item == iPrint ) {
			try {
				textArea.print();
			} catch (PrinterException ex) {
				throw new RuntimeException(ex);
			}
		}
		else if ( item == iSearch ) {
			findWord();
		}
		window.setJMenuBar(menuBar);
	}

	// search Function
	public void findWord() {
		String word = (String)JOptionPane.showInputDialog(
				this.window,
				"Enter the word you want to find:",
				"Find Dialog",
				JOptionPane.PLAIN_MESSAGE,
				null,
				null,
				null);

		Highlighter.HighlightPainter painter =
				new DefaultHighlighter.DefaultHighlightPainter( Color.cyan );

		String text = textArea.getText();
		int offset = text.indexOf(word);
		int length = word.length();

		while ( offset != -1)
		{
			try
			{
				textArea.getHighlighter().addHighlight(offset, offset + length, painter);
				offset = text.indexOf(word, offset + 1);
			}
			catch(BadLocationException ble) {
				ble.printStackTrace();
			}
		}
	}

	boolean saved = true;
	boolean newFileFlag = true;
	String fileName=new String("Untitled");
	File fileRef=new File(fileName);
	JFileChooser chooser=new JFileChooser();

	public void OpenDocument(){ //open file from computer
		int i=chooser.showOpenDialog(this.window);
		if(i==JFileChooser.APPROVE_OPTION){
			File f=chooser.getSelectedFile();
			String filepath=f.getPath();
			fileName=f.getName();
			try{
				BufferedReader br=new BufferedReader(new FileReader(filepath));
				String s1="",s2="";
				while((s1=br.readLine())!=null){
					s2+=s1+"\n";
				}
				textArea.setText(s2);
				br.close();
			}catch (Exception ex) {ex.printStackTrace();  }
		}
		newFileFlag=false;
		window.setTitle(fileName + " - " + "159251_assignment 1_TextEditor");
	}

	public void SaveDocument() {//save current file
		if(!newFileFlag)
		{
			saveFile(fileRef);
			return;
		}

		saveAsFile();
		fileName=chooser.getName(chooser.getSelectedFile());
		window.setTitle(fileName + " - " + "159251_assignment 1_TextEditor");
	}
	void saveFile(File temp)//if file already opened
	{
		FileWriter fout=null;
		try
		{
			fout=new FileWriter(temp);
			fout.write(textArea.getText());
		}
		catch(IOException ioe){return;}
		finally
		{try{fout.close();}catch(IOException excp){}}
		newFileFlag=false;
	}
	void saveAsFile() {//if new file
		File temp;
		chooser.setDialogTitle("Save As...");
		chooser.setApproveButtonText("Save");
		chooser.setApproveButtonMnemonic(KeyEvent.VK_S);
		chooser.setApproveButtonToolTipText("Click to save");
		
		do {
			if (chooser.showSaveDialog(this.window) != JFileChooser.APPROVE_OPTION)
				return;
			temp = chooser.getSelectedFile();
			if (!temp.exists()) break;
			if (JOptionPane.showConfirmDialog(
					this.window, "<html>" + temp.getPath() + " already exists.<br>Do you want to replace it?<html>",
					"Save As", JOptionPane.YES_NO_OPTION
			) == JOptionPane.YES_OPTION)
				break;
		} while (true);
		
		saveFile(temp);
	}
}
