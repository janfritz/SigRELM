package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.Simulator;


@SuppressWarnings("serial")
public class AddPanel extends JPanel {
	public static File[] forgedSignatures;
	public static File[] genuineSignatures;
	private LinkedList<File> files = new LinkedList<File>();
	
	private ChooserFilter filter;
	private String[] file_types = {"png", "jpg", "jpeg", "bmp", "gif", "tiff"};

	private JButton loadGenuine;
	private JButton loadForged;
	private JButton add;
	
	private JCheckBox show_simulation;
	
	private JFileChooser chooser;
	
	private JLabel progress;
	private JLabel panelTitle;
	private JLabel name_caption;
	private JLabel thresh_caption;
	
	private JScrollPane scroll;
	
	public static JTextArea progress_area;
	
	private JTextField name_field;
	private JTextField thresh_field;
	
	private SimulationDialog simulationDialog;
	
	public AddPanel(SimulationDialog simulationDialog){
		initPanel();
		initComponents();
		addListeners();
		this.simulationDialog = simulationDialog;
	}
	
	private void initPanel(){
		setLayout(null);
	}
	
	private void initComponents(){		
		panelTitle = new JLabel("Add Signature to Database");
		panelTitle.setBounds(15,5,250,30);
		panelTitle.setFont(new Font("Segoe UI", 1, 18));
		add(panelTitle);
		
		name_caption = new JLabel("Name:");
		name_caption.setBounds(15,40,100,20);
		name_caption.setFont(new Font("Segoe UI", 0, 14));
		add(name_caption);
		
		thresh_caption = new JLabel("Threshold (in %):");
		thresh_caption.setBounds(375,40,105,20);
		thresh_caption.setFont(new Font("Segoe UI", 0, 14));
		add(thresh_caption);
		
		name_field = new JTextField();
		name_field.setBounds(15,65,350,25);
		name_field.setFont(new Font("Segoe UI", 0, 14));
		add(name_field);
		
		thresh_field = new JTextField();
		thresh_field.setBounds(375,65,105,25);
		thresh_field.setFont(new Font("Segoe UI", 0, 14));
		add(thresh_field);
		
		loadGenuine = new JButton("Load Genuine Signatures");
		loadGenuine.setBounds(40,99,200,27);
		loadGenuine.setFont(new Font("Segoe UI", 0, 14));
		add(loadGenuine);
		
		loadForged = new JButton("Load Forged Signatures");
		loadForged.setBounds(250,99,200,27);
		loadForged.setFont(new Font("Segoe UI", 0, 14));
		add(loadForged);
		
		add = new JButton("ADD");
		add.setBounds(15,149,105,27);
		add.setFont(new Font("Segoe UI", 1, 14));
		add(add);
		
		show_simulation = new JCheckBox("(Simulation is not available in the Add function)");
		show_simulation.setEnabled(false);
		show_simulation.setBounds(130,150,300,25);
		show_simulation.setFont(new Font("Segoe UI", 2, 12));
		add(show_simulation);
		
		progress = new JLabel("Progress:");
		progress.setBounds(15,190,100,25);
		progress.setFont(new Font("Segoe UI", 0, 14));
		add(progress);
		
		progress_area = new JTextArea();
		progress_area.setEditable(false);
		
		scroll = new JScrollPane(progress_area);
		scroll.setBounds(15,220,464,140);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll);
		
		chooser = new JFileChooser();
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setMultiSelectionEnabled(true);
		filter = new ChooserFilter(file_types, "Image Files");  
		chooser.addChoosableFileFilter(filter);  
	}
	
	private void addListeners(){	
		loadGenuine.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = chooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					genuineSignatures = chooser.getSelectedFiles();
					loadGenuine.setText(genuineSignatures.length + " genuine signature/s");
		        }
			}
		});
		loadForged.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = chooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					forgedSignatures = chooser.getSelectedFiles();
					loadForged.setText(forgedSignatures.length + " forged signature/s");
		        }
			}
		});
		add.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(name_field.getText().equals("") || thresh_field.getText().equals("") || (genuineSignatures.length == 0 && forgedSignatures.length == 0))
					JOptionPane.showMessageDialog(null, "All fields are required.", "Error!", JOptionPane.ERROR_MESSAGE);
				else{
					if(!isExistingUser(name_field.getText())){
						for(int ctr = 0; genuineSignatures != null && ctr < genuineSignatures.length; ctr++)
							files.add(genuineSignatures[ctr]);
						for(int ctr = 0; forgedSignatures != null && ctr < forgedSignatures.length; ctr++)
							files.add(forgedSignatures[ctr]);
						
						System.out.println("Processing request...");
						progress_area.append("Processing request..." + System.getProperty("line.separator"));	
						
						for(int ctr = 0; ctr < files.size(); ctr++){
	//						simulationDialog.setVisible(true);
	//						simulationDialog.simulationPanel.loadImage(files[ctr].getPath());
							Simulator.addSignature(files.get(ctr).getPath(), files.get(ctr).getName(), ctr + 1, files.size());
							Simulator.extractFeatures(files.get(ctr).getPath(), files.get(ctr).getName(), ctr + 1, files.size());
						}
						Simulator.train(name_field.getText(), files.size());
						GUI.updateUserList();
					}
					else
						JOptionPane.showMessageDialog(null, "User already exists!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	private boolean isExistingUser(String name){
		boolean exist = false;
		File folder = new File("train/");
		File[] listOfFiles = folder.listFiles();
		VerifyPanel.owner_combo.removeAll();
	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()){
	    	  if(name.trim().equals(new String(listOfFiles[i].getName().split("_train.txt")[0]))){
	    		  exist = true;
	    		  break;
	    	  }
	      }
	    }
	    return exist;
	}
}
