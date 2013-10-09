package main;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.NotConvergedException;

import elm.ELM;

import preprocess.BackgroundElimination;
import preprocess.Grayscale;
import preprocess.Thinning;
import sift.FeatureExtraction;
import sift.SIFT2;

import gui.AddPanel;
import gui.SimulationPanel;

public class Simulator {
    public static LinkedList<List> features = new LinkedList<List>();
		
	public static SimulationPanel simulationPanel;
	
	private static BufferedImage image;
    
    private static int scaledWidth;
    private static int scaledHeight;
    
    private static ELM elm = new ELM(1, 24, "sig");
		
	public static void setSimulationPanel(SimulationPanel panel){
		simulationPanel = panel;
	}
	
	private static BufferedImage resizeImage(BufferedImage image) {
		scaleImage(image.getWidth(), image.getHeight());
        final BufferedImage bufferedImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);
        graphics2D.drawImage(image, 0, 0, scaledWidth, scaledHeight, null);
        graphics2D.dispose();
 
        return bufferedImage;
    }
	
	private static void scaleImage(int width, int height) {
	    int bound_width = 450;
	    int bound_height = 400;
	    scaledWidth = width;
	    scaledHeight = height;
	    
	    if (width > bound_width) {
	        scaledWidth = bound_width;
	        scaledHeight = (scaledWidth * height) / width;
	    }

	    if (scaledHeight > bound_height) {
	        scaledHeight = bound_height;
	        scaledWidth = (scaledHeight * width) / height;
	    }
	}
		
	public static void addSignature(String imageURL, String imageName, int count, int size){
		try {
			image = ImageIO.read(new File(imageURL));
//			image = resizeImage(image);

	        AddPanel.progress_area.append("Grayscaling " + imageName + ". (Image " + count + " of " + size + ")" + System.getProperty("line.separator"));
			image = Grayscale.toGray(image);			
//			simulationPanel.drawImage(image);

	        AddPanel.progress_area.append("Eliminating background of " + imageName + ". (Image " + count + " of " + size + ")" + System.getProperty("line.separator"));
			image = BackgroundElimination.eliminateBackground(image);
//			simulationPanel.drawImage(image);
			
	        AddPanel.progress_area.append("Thinning edges of " + imageName + ". (Image " + count + " of " + size + ")" + System.getProperty("line.separator"));
			image = Thinning.thinEdges(image);
//			simulationPanel.drawImage(image);
		} catch (IOException e) {}		
		
//		System.out.println("Please wait, Processing SIFT Algorithm...");
//		AddPanel.progress_area.append("Finished preprocessing." + System.getProperty("line.separator"));		
//		AddPanel.progress_area.append(System.getProperty("line.separator") + "Please wait, Processing SIFT Algorithm..." + System.getProperty("line.separator"));
		
//		FeatureExtraction.processSIFT(image);
//		AddPanel.progress_area.append(FeatureExtraction.lines.size() + " features identified and processed." + System.getProperty("line.separator"));		
	}
	
	public static void extractFeatures(String imageURL, String imageName, int count, int size){
//		System.out.println("Extracting SIFT Features of " + imageName + ". (Image " + count + " of " + size + ")" + System.getProperty("line.separator"));
		AddPanel.progress_area.append("Extracting SIFT Features of " + imageName + ". (Image " + count + " of " + size + ")" + System.getProperty("line.separator"));
		
		SIFT2.extractSIFTFeatures(image);
		features.add(SIFT2.result);
//		System.out.println(SIFT2.result.size() + " features extracted from " + imageName);
		AddPanel.progress_area.append(SIFT2.result.size() + " features extracted from " + imageName + System.getProperty("line.separator"));
	}
	
	public static void train(String name, int rows){
		int min = features.get(0).size();
		for(int ctr = 0; ctr < features.size(); ctr++){
			min = Math.min(min, features.get(ctr).size());
		}
		
		createTrainingData(name, rows, min*4, min);
		try {
			elm.train("train/" + name + "_train.txt");
			saveWeights(name, elm.getInputWeight(), "in");
			saveWeights(name, elm.getOutputWeight(), "out");
//			displayInputWeights(elm.getInputWeight());
//			displayOutputWeights(elm.getOutputWeight());
			System.out.println("TrainingTime: "+ elm.getTrainingTime());
			System.out.println("TrainingAcc: "+ elm.getTrainingAccuracy());
		} catch (NotConvergedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void saveWeights(String name, DenseMatrix denseMatrix, String type){
		try{
			File dir = new File("elm/");
			dir.mkdir();
			File file = new File("elm/" + name + "_" + type + ".txt");
			if (!file.exists())
				file.createNewFile();
			else{
				file.delete();
				file.createNewFile();
			}

			BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));		
			System.out.println("Matrix: " + denseMatrix.numColumns() + "," + denseMatrix.numRows());
			bw.write(denseMatrix.numRows() + " " + denseMatrix.numColumns() + System.getProperty("line.separator"));
			
			for(int row = 0; row < denseMatrix.numRows(); row++){
				for(int col = 0; col < denseMatrix.numColumns(); col++){
					if(col == 0)
						bw.write(new String("" + denseMatrix.get(row,col)));
					else
						bw.write(new String(" " + denseMatrix.get(row,col)));
				}
				bw.write(System.getProperty("line.separator"));
			}
			bw.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
		
//	public static void displayInputWeights(DenseMatrix denseMatrix){
//		System.out.println("Input Weights: " + "(" + denseMatrix.numColumns() + "," + denseMatrix.numRows() +")");
//		for(int row = 0; row < denseMatrix.numRows(); row++){
//			for(int col = 0; col < denseMatrix.numColumns(); col++)
//				System.out.print("[" + denseMatrix.get(row,col) + "]");
//			System.out.println();
//		}
//	}
//	
//	public static void displayOutputWeights(DenseMatrix denseMatrix){
//		System.out.println("Output Weights: " + "(" + denseMatrix.numColumns() + "," + denseMatrix.numRows() +")");
//		for(int row = 0; row < denseMatrix.numRows(); row++){
//			for(int col = 0; col < denseMatrix.numColumns(); col++)
//				System.out.print("[" + denseMatrix.get(row,col) + "]");
//			System.out.println();
//		}
//	}
	
	public static void test(String name){
		int min = features.get(0).size();
		createTestingData(name, 1, min*4, min);
		try {
			elm.setInputWeight(createDenseMatrix("elm/" + name + "_in.txt"));
			elm.setOutputWeight(createDenseMatrix("elm/" + name + "_out.txt"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		elm.test("test/" + name + "_test.txt");
		System.out.println("TestingTime: "+ elm.getTestingTime());
		System.out.println("TestingAcc: "+ elm.getTestingAccuracy());
	}
	
	private static DenseMatrix createDenseMatrix(String filename) throws IOException{
		System.out.println(filename);
		File file = new File(filename);
		
		BufferedReader bf = new BufferedReader(new FileReader(file));
		String firstline = bf.readLine();
		String strings[] = firstline.split(" ");
		int r = Integer.parseInt(strings[0]);
		int c = Integer.parseInt(strings[1]);
		DenseMatrix matrix = new DenseMatrix(r, c);
		firstline = bf.readLine();
		for(int row = 0; row < r; row++){
			String []data = firstline.split(" ");
			for(int col = 0; col < c; col++){
				matrix.add(row, col, Double.parseDouble(data[col]));
//				matrix.set(row, col, Double.parseDouble(data[col]));
				System.out.println("(" + row + "," + col + ") - " + Double.parseDouble(data[col]));
			}
			firstline = bf.readLine();
		}
		bf.close();
		return matrix;
	}
	
	private static void createTrainingData(String name, int row, int col, int min){
		try{
			String line = "";
			Random rand = new Random();

			File dir = new File("train/");
			dir.mkdir();
			File file = new File("train/" + name + "_train.txt");
			if (!file.exists()) {
				file.createNewFile();
			} 
			else{
				file.delete();
			}
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);			
			bw.write(new String(row + " " + (col+1) + " " + 2) + System.getProperty("line.separator"));
			
			for(int ctr = 0; ctr < features.size(); ctr++){
				if(ctr < AddPanel.genuineSignatures.length)
					line = "1";
				else
					line = "0";

				for(int count = 0; count < min; count ++){
					int num = rand.nextInt(features.get(ctr).size() - 0);
					List<double[]> list = features.get(ctr);
					System.out.println("[0]" + list.get(num)[0]);
					line = line + " " + list.get(num)[0] + " " + list.get(num)[1] +  " " + list.get(num)[2] +  " " + list.get(num)[3];
				}
				bw.write(line + System.getProperty("line.separator"));
				line = "";
			}
			bw.close();
 
			System.out.println("Created Training File!");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private static void createTestingData(String name, int row, int col, int min){
		try{
			String line = "";
			Random rand = new Random();

			File dir = new File("test/");
			dir.mkdir();
			File file = new File("test/" + name + "_test.txt");
			if (!file.exists()) {
				file.createNewFile();
			} 
			else{
				file.delete();
			}
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);			
			bw.write(new String(row + " " + (col+1) + " " + 2) + System.getProperty("line.separator"));
			
//			for(int ctr = 0; ctr < features.size(); ctr++){
//				if(ctr < AddPanel.genuineSignatures.length)
//					flag = 1;
//				else
//					flag = 0;
				line = "1";
				
				for(int count = 0; count < min; count ++){
					int num = rand.nextInt(features.get(0).size() - 0);
					List<double[]> list = features.get(0);
					line = line + " " + list.get(num)[0] + " " + list.get(num)[1] +  " " + list.get(num)[2] +  " " + list.get(num)[3];
				}
				bw.write(line + System.getProperty("line.separator"));
				line = "";
//			}
			bw.close();
 
			System.out.println("Created Testing File!");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
}
