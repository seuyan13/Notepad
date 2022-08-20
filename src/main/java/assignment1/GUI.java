package assignment1;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.lang.*;
import java.util.Random;
import com.itextpdf.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.io.*;

public class GUI implements ActionListener{
	JFrame window;
	JTextArea textArea;
	JScrollPane scrollPane;
	JMenuBar menuBar;
	JMenu menuFile, menuEdit, menuHelp, menuPrint,file, search;
	JMenuItem
	add, open, save, exit, iSearch, iSaveAs,iPrint, //items for File menu
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
    		//createFileMenu();

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
			File file = new File("C:\\Users\\File_Path_Here\\File_Name_Here.Pdf"); //file path here
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
		else if (item == add) {
			new GUI();
		}
		else if  (item == open) {
			OpenDocument();
		}
		else if (item == save) {
			SaveDocument();
		}
		window.setJMenuBar(mb);
	}
<<<<<<< HEAD
	
=======

	/*
	public void createFileMenu() {
		JMenu file, search;
		JMenuItem add, open, save, exit;
		JMenuBar mb=new JMenuBar();

		search=new JMenu("Search");
		file=new JMenu("File");

		add=new JMenuItem("New");
		open=new JMenuItem("Open");
		save=new JMenuItem("Save");
		exit=new JMenuItem("Exit");

		file.add(add); file.add(open); file.add(save); file.add(exit);
		mb.add(file); mb.add(search);

		add.addActionListener(new ActionListener() { //if New is selected
			public void actionPerformed(ActionEvent e) {
				new GUI(); //open new window
			}
		});
		open.addActionListener(new ActionListener() { //if Open is selected
			public void actionPerformed(ActionEvent e) {
				OpenDocument();
			}
		});
		save.addActionListener(new ActionListener() { //if Save is selected
			public void actionPerformed(ActionEvent e) {
				SaveDocument();
			}
		});
		window.setJMenuBar(mb);
	}
	*/

>>>>>>> fd88e1b5a7c559508618202d8840e2b70aae58bd
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
			return;}

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
<<<<<<< HEAD
}
=======
}

>>>>>>> fd88e1b5a7c559508618202d8840e2b70aae58bd
