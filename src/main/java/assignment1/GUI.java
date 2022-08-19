package assignment1;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class GUI{
	JFrame window;
	JTextArea textArea;
	JScrollPane scrollPane;

	public static void main(String[] args) {
		new GUI();
	}

	public GUI() {
		createWindow();
		createTextArea();
		createFileMenu();
		window.setVisible(true);
	}

	public void createWindow() {
		window = new JFrame("159251_assignment 1_TextEditor");
		window.setSize(800,600);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void createTextArea() {
		textArea = new JTextArea();
		scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		window.add(scrollPane);
		//window.add(textArea);
	}

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
}

