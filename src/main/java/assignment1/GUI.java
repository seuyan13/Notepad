package assignment1;
import java.awt.event.*;
import javax.swing.*;

public class GUI implements ActionListener{
	JFrame window;
	JTextArea textArea;
	JScrollPane scrollPane;
	JMenuBar menuBar;
	JMenu menuFile, menuEdit, menuHelp;
	JMenuItem iSelect, iCopy, iPaste, iCut, iHelp;
	GUI()
	{
		window = new JFrame();  
        iCut = new JMenuItem("cutItem");  
        iCopy = new JMenuItem("copyItem");  
        iPaste = new JMenuItem("pasteItem");  
        iSelect = new JMenuItem("selectAllItem");  
        iCopy.addActionListener(this);  
        iCut.addActionListener(this);  
        iSelect.addActionListener(this);  
        iPaste.addActionListener(this);  
        
        menuBar = new JMenuBar();  
        menuBar.setBounds(5, 5, 400, 40);  
        menuFile = new JMenu("File");  
        menuEdit = new JMenu("Edit");  
        menuHelp = new JMenu("Help");  
        menuEdit.add(iCut);  
        menuEdit.add(iCopy);  
        menuEdit.add(iPaste);  
        menuEdit.add(iSelect);
        menuBar.add(menuFile);  
        menuBar.add(menuEdit);  
        menuBar.add(menuHelp);  
        createWindow();
		createTextArea();
		createMenuBar();
		createSCPC();
		createAbout();
		window.setVisible(true);
	}
	
	public static void main(String[] args) {
		new GUI();
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
	
	public void createSCPC() {
		iSelect = new JMenuItem("select all");
		menuEdit.add(iSelect);
		
		iCopy = new JMenuItem("copy");
		menuEdit.add(iCopy);
		
		iPaste = new JMenuItem("paste");
		menuEdit.add(iPaste);
		
		iCut = new JMenuItem("cut");
		menuEdit.add(iCut);
	}
	
	public void createAbout() {
		iHelp = new JMenuItem("About");
		menuHelp.add(iHelp);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
