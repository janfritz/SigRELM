package gui;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

@SuppressWarnings("serial")
public class GUI extends JFrame {
	private JPanel header;
	private JPanel footer;
	private JPanel inner;
	
	private JButton add;
	private JButton verify;
	private JButton identify;

	private JTextPane message;
	private SimpleAttributeSet attrib;
	
	private AddPanel addPanel;
	private VerifyPanel verifyPanel;
	private IdentifyPanel identifyPanel;	
	private SimulationDialog simulationDialog;

	public GUI(){
		initFrame();
		initComponents();
		initInner();
		addListeners();
		updateUserList();
	}
	
	public void initFrame(){
        setLayout(null);
        setSize(500,500);
		setTitle("SigRELM");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage("images/icon.png"));
	}
	
	public void initComponents(){
		header = new JPanel();
		header.setLayout(null);
		header.setBounds(0,0,494,70);
		add(header);
		
		JLabel icon = new JLabel(new ImageIcon("images/icon.png"));
		icon.setBounds(15,10,50,50);
		header.add(icon);
		
		JLabel title = new JLabel("SigRELM");
		title.setBounds(75,9,100,25);
		title.setFont(new Font("Segoe UI", 1, 20));
		header.add(title);
		
		JLabel desc = new JLabel("Offline Signature Recognition");
		desc.setBounds(75,33,250,25);
		desc.setFont(new Font("Segoe UI", 0, 18));
		header.add(desc);
		
		JSeparator sep1 = new JSeparator();
		sep1.setBounds(15,70,464,1);
		add(sep1);
		
		JSeparator sep2 = new JSeparator();
		sep2.setBounds(15,441,464,1);
		add(sep2);

		footer = new JPanel();
		footer.setLayout(null);
		footer.setBounds(0,441,494,30);
		add(footer);		
		
		JLabel name = new JLabel("Daniell Jan Fritz C. Saborrido");
		name.setBounds(15,5,200,20);
		name.setFont(new Font("Segoe UI", 2, 12));
		footer.add(name);
		
		JLabel course = new JLabel("BSCS-4 A.Y. 2013-2014, UPVTC");
		course.setBounds(316,5,165,20);
		course.setFont(new Font("Segoe UI", 2, 12));
		footer.add(course);
		
		simulationDialog = new SimulationDialog();
		simulationDialog.setLocationRelativeTo(null);
	}
	
	private void initInner(){
		inner = new JPanel();
		inner.setLayout(null);
		inner.setBounds(0,70,494,371);
		add(inner);
		
        attrib = new SimpleAttributeSet();
        StyleConstants.setAlignment(attrib, StyleConstants.ALIGN_JUSTIFIED);
        StyleConstants.setFontFamily(attrib, "Segoe UI");  
        StyleConstants.setFontSize(attrib, 18); 
        
		message = new JTextPane();
		message.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse in libero ac massa elementum pellentesque. Vestibulum auctor eu augue non placerat. Phasellus tincidunt blandit quam, et tempor ante condimentum et. Integer sed vehicula dolor. Nulla sed gravida nisi. Cras lobortis metus nisi, et interdum dui varius in.");
		message.setBackground(null);
		message.setEditable(false);
		message.setBounds(10,10,479,150);
		message.getStyledDocument().setParagraphAttributes(0, message.getText().length(), attrib, false);
		inner.add(message);
		
		add = new JButton(new ImageIcon("images/add.png"));
		add.setFocusable(false);
		add.setBounds(30,185,130,130);
		inner.add(add);
		
		verify = new JButton(new ImageIcon("images/verify.png"));
		verify.setFocusable(false);
		verify.setBounds(182,185,130,130);
		inner.add(verify);
		
		identify = new JButton(new ImageIcon("images/identify.png"));
		identify.setFocusable(false);
		identify.setBounds(334,185,130,130);
		inner.add(identify);
		
		JLabel addLabel = new JLabel("ADD");
		addLabel.setBounds(30,325,130,30);
		addLabel.setFont(new Font("Segoe UI", 1, 18));
		addLabel.setHorizontalAlignment(JLabel.CENTER);
		inner.add(addLabel);

		JLabel verifyLabel = new JLabel("VERIFY");
		verifyLabel.setBounds(182,325,130,30);
		verifyLabel.setFont(new Font("Segoe UI", 1, 18));
		verifyLabel.setHorizontalAlignment(JLabel.CENTER);
		inner.add(verifyLabel);

		JLabel identifyLabel = new JLabel("IDENTIFY");
		identifyLabel.setBounds(334,325,130,30);
		identifyLabel.setFont(new Font("Segoe UI", 1, 18));
		identifyLabel.setHorizontalAlignment(JLabel.CENTER);
		inner.add(identifyLabel);
		
		addPanel = new AddPanel(simulationDialog);
		addPanel.setVisible(false);
		addPanel.setBounds(0,70,494,371);
		add(addPanel);
		
		verifyPanel = new VerifyPanel();
		verifyPanel.setVisible(false);
		verifyPanel.setBounds(0,70,494,371);
		add(verifyPanel);
		
		identifyPanel = new IdentifyPanel();
		identifyPanel.setVisible(false);
		identifyPanel.setBounds(0,70,494,371);
		add(identifyPanel);
	}
	
	private void addListeners(){
		add.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				inner.setVisible(false);
				addPanel.setVisible(true);
			}
		});
		verify.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				inner.setVisible(false);
				verifyPanel.setVisible(true);
			}
		});
		identify.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				inner.setVisible(false);
				identifyPanel.setVisible(true);
			}
		});
	}
	
	public static void updateUserList(){
		File folder = new File("train/");
		folder.mkdir();
		File[] listOfFiles = folder.listFiles();
		VerifyPanel.owner_combo.removeAll();
	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()) {
//	    	  String str = listOfFiles[i].getName().split("_train.txt")[0];
	    	  VerifyPanel.owner_combo.addItem(new String(listOfFiles[i].getName().split("_train.txt")[0]));
	      }
	    }
	}
}
