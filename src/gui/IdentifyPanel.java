package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class IdentifyPanel extends JPanel {

	private JButton browse;
	private JButton identify;
	
	private JCheckBox show_simulation;
	
	private JLabel preview;
	private JLabel progress;
	private JLabel panelTitle;
	private JLabel file_chosen;
	private JLabel sig_caption;
	
	private JScrollPane scroll;
	
	private JTextArea progress_area;
	
	public IdentifyPanel(){
		initPanel();
		initComponents();
		addListeners();
	}
	
	private void initPanel(){
		setLayout(null);
	}
	
	private void initComponents(){	
		panelTitle = new JLabel("Identify Signature Image");
		panelTitle.setBounds(15,5,250,30);
		panelTitle.setFont(new Font("Segoe UI", 1, 18));
		add(panelTitle);
		
		sig_caption = new JLabel("Signature Image:");
		sig_caption.setBounds(15,40,150,20);
		sig_caption.setFont(new Font("Segoe UI", 0, 14));
		add(sig_caption);
		
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
		
		identify = new JButton("IDENTIFY");
		identify.setBounds(15,149,105,27);
		identify.setFont(new Font("Segoe UI", 1, 14));
		add(identify);
		
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
	}
	
	private void addListeners(){	
		browse.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		identify.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(file_chosen.getText().equals("No file chosen"))
					JOptionPane.showMessageDialog(null, "All fields are required.", "Error!", JOptionPane.ERROR_MESSAGE);
				else{
					
				}
			}
		});
	}
}
