package gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import main.Simulator;


@SuppressWarnings("serial")
public class SimulationDialog extends JDialog {
	public static SimulationDialog instance;

	private JPanel imagePanel;
	private JProgressBar progressBar;
	
	public SimulationPanel simulationPanel;	

	public SimulationDialog(){
		initDialog();
		initComponents();
	}
	
	public void setTitle(String title){
		setTitle(title);
	}
	
	private void initDialog(){
		setLayout(null);
		setVisible(false);
		setSize(496,519);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	}
	
	private void initComponents(){		
		imagePanel = new JPanel();
		imagePanel.setLayout(null);
		imagePanel.setBounds(5,5,470,430);
		imagePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1), "Signature Image", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Segoe UI", 1, 14), Color.BLACK));
		add(imagePanel);
		
		simulationPanel = new SimulationPanel();
		simulationPanel.setBounds(10,20,450,400);
		imagePanel.add(simulationPanel);
		
		progressBar = new JProgressBar();
		progressBar.setValue(0);
		progressBar.setBounds(5,5,470,30);
		add(progressBar);
		
		Simulator.setSimulationPanel(simulationPanel);
	}
}
