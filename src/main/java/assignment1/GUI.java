package assignment1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Locale;
import javax.swing.text.*;
public class GUI{
	JFrame window;
	JTextArea textArea;
	JScrollPane scrollPane;

	JTextField searchBar;

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

	public void search() throws BadLocationException {

		String text = textArea.getText().toUpperCase();
		String word = searchBar.getText().toUpperCase();
		int Tsize = text.length();
		int Wsize = word.length();
		boolean match;
		textArea.getHighlighter().removeAllHighlights();
		if(Wsize==0){return;}
		for(int i=0;i<Tsize;i++){
			match=false;

			if(text.charAt(i) == word.charAt(0)){
				match=true;
				for(int n=0;n<Wsize;n++){
					if (text.charAt(i + n) != word.charAt(n)) {
						match = false;
						break;
					}
				}
				if(match){
					textArea.getHighlighter().addHighlight(i,(i+Wsize), new DefaultHighlighter.DefaultHighlightPainter(Color.RED));
				}
			}
		}
	}

	public void createFileMenu() {
		JMenu file;
		JMenuItem add, open, save, exit;
		JMenuBar mb=new JMenuBar();

		file=new JMenu("File");
		searchBar=new JTextField();
		add=new JMenuItem("New");
		open=new JMenuItem("Open");
		save=new JMenuItem("Save");
		exit=new JMenuItem("Exit");

		JButton searchButton=new JButton("Search");

		file.add(add); file.add(open); file.add(save); file.add(exit);
		mb.add(file); mb.add(searchButton); mb.add(searchBar);

		searchButton.addActionListener(new ActionListener() { //if searchBar used selected
			public void actionPerformed(ActionEvent e) {
				try {
					search(); //search word
				} catch (BadLocationException ex) {
					ex.printStackTrace();
				}
			}
		});
		exit.addActionListener(new ActionListener() { //if New is selected
			public void actionPerformed(ActionEvent e) {
				 System.exit(0);//exit program
			}
		});

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

