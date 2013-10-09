package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import main.Simulator;

@SuppressWarnings("serial")
public class VerifyPanel extends JPanel {
	private File file;
	
	private ChooserFilter filter;
	private String[] file_types = {"png", "jpg", "jpeg", "bmp", "gif", "tiff"};

	private JButton browse;
	private JButton verify;
	
	private JCheckBox show_simulation;
	
	private JFileChooser chooser;
	
	public static JComboBox<String> owner_combo;
	
	private JLabel preview;
	private JLabel progress;
	private JLabel panelTitle;
	private JLabel file_chosen;
	private JLabel sig_caption;
	private JLabel owner_caption;
	
	private JScrollPane scroll;
	
	private JTextArea progress_area;

	public VerifyPanel(){
		initPanel();
		initComponents();
		addListeners();
	}
	
	private void initPanel(){
		setLayout(null);
	}
	
	private void initComponents(){	
		panelTitle = new JLabel("Verify Signature Image");
		panelTitle.setBounds(15,5,250,30);
		panelTitle.setFont(new Font("Segoe UI", 1, 18));
		add(panelTitle);
		
		sig_caption = new JLabel("Signature Image:");
		sig_caption.setBounds(15,40,150,20);
		sig_caption.setFont(new Font("Segoe UI", 0, 14));
		add(sig_caption);
		
		owner_caption = new JLabel("Claimed Owner:");
		owner_caption.setBounds(295,40,185,20);
		owner_caption.setFont(new Font("Segoe UI", 0, 14));
		add(owner_caption);
		
		owner_combo = new JComboBox<String>();
		owner_combo.setBounds(295,65,185,25);
		owner_combo.setFont(new Font("Segoe UI", 0, 14));
		add(owner_combo);
		
		preview = new JLabel();
		preview.setBounds(15,65,60,60);
		preview.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));
		add(preview);
		
		file_chosen = new JLabel(" No file chosen");
		file_chosen.setBounds(85,65,200,25);
		file_chosen.setFont(new Font("Segoe UI", 2, 14));
		file_chosen.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		add(file_chosen);
		
		browse = new JButton("Browse");
		browse.setBounds(85,99,105,27);
		browse.setFont(new Font("Segoe UI", 0, 14));
		add(browse);
		
		verify = new JButton("VERIFY");
		verify.setBounds(15,149,105,27);
		verify.setFont(new Font("Segoe UI", 1, 14));
		add(verify);
		
		show_simulation = new JCheckBox("Show Simulation");
		show_simulation.setSelected(true);
		show_simulation.setBounds(130,150,150,25);
		show_simulation.setFont(new Font("Segoe UI", 0, 12));
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
		browse.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = chooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION){
					file = chooser.getSelectedFile();
					file_chosen.setText(file.getName());
		        }				
			}
		});
		verify.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(file_chosen.getText().equals("No file chosen"))
					JOptionPane.showMessageDialog(null, "All fields are required.", "Error!", JOptionPane.ERROR_MESSAGE);
				else{
					System.out.println("Processing request...");
					progress_area.append("Processing request..." + System.getProperty("line.separator"));	
					
//					simulationDialog.setVisible(true);
//					simulationDialog.simulationPanel.loadImage(file.getPath());
					Simulator.addSignature(file.getPath(), file.getName(), 1, 1);
					Simulator.extractFeatures(file.getPath(), file.getName(), 1, 1);
					Simulator.test((String)owner_combo.getSelectedItem());
				}
			}
		});
	}
}
