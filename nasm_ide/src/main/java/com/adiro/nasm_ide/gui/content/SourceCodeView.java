package com.adiro.nasm_ide.gui.content;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class SourceCodeView extends ScrollPane {
	
	List<Text> code;
	private int currentLine = 0;
	private String sourceCodePath;
	
	public SourceCodeView() {
		super();
    	setMaxSize(1000, 700);
    	setMinSize(1000, 700);
    	setFocusTraversable(false);
    	sourceCodePath = getPrevLocation();
    	loadSourceCode();
	}
	
	public boolean goToNextLine() {
		
		do {
			if(currentLine < code.size() - 1) {
				currentLine++;
			}
			else {
				return false;
			}
		}
		while(code.get(currentLine).getText().isBlank());
		
		
		colorLine(currentLine);
		return true;
	}
	
	public boolean goToLine(int index) {
		
		currentLine = -1;
		int i = -1;
		
		do {
			if(currentLine >= code.size()-1)
				return false;
			
			goToNextLine();
			i++;
		}
		while(i < index);
		
		setVvalue( (1.0 / (code.size()-29))* (currentLine - 0.2));
		
		
		
		return true;
	}
	
	public void setSourceCodePath(String newPath) {
		sourceCodePath = newPath;
		loadSourceCode();
	}
	
	public void haltNextLine() {
		clearColors();
		try {
			currentLine++;
			code.get(currentLine).setFill(Color.RED);
		}
		catch(Exception e) {
			System.out.println("[ERROR] No line to color");
		}
		
	}
	public int getCurrentLine() {
		return currentLine;
	}
	
	private boolean loadSourceCode() {
		File file = new File(sourceCodePath);
		
		code = new ArrayList<>();
    	
    	Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("[ERROR] Couldn't find the file " + file.getAbsolutePath());
			
			return false;
		}
    	
    	while(scanner.hasNextLine()) {
    		code.add(new Text(scanner.nextLine() + "\n"));
    	}
    	scanner.close();
    	
    	var text = new TextFlow();
    	
    	for(var line : code ) {
    		line.setFont(Font.font("Arial", FontPosture.REGULAR, 20));
    		text.getChildren().add(line);
    	}
    	
    	this.setContent(text);
    	
    	return true;
	}
	
	private void clearColors() {
		for(var line : code) {
			line.setFill(Color.BLACK);
		}
	}
	
	private void colorLine(int n) {
		
		clearColors();
		code.get(n).setFill(Color.GREEN);
	}
	
	public String getPrevLocation() {
			
			Path path = Paths.get("resources/locations/source");
			String prevLocation = "";
			
			try {
				prevLocation = Files.readAllLines(path).get(0);
			} catch (IOException e) {
				System.out.println("[WARNING] Missing resources/locations/source, the program might be corrupted");
			}
			
			if(prevLocation.isBlank()) prevLocation = "./";
			return prevLocation;
		}
	
	
}
