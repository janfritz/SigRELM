package main;

import javax.swing.UIManager;

import gui.GUI;

public class Main {
	
	public static void main(String args[]){
	    try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    }	    
	    catch(Exception ex){}		    

		GUI gui = new GUI();
		gui.setVisible(true);
	}

}
