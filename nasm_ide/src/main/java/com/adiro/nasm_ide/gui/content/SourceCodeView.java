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

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class SourceCodeView extends ScrollPane {
	
	List<TextLine> sourceCode;
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
			if(currentLine < sourceCode.size() - 1) {
				currentLine++;
			}
			else {
				return false;
			}
		}
		while(sourceCode.get(currentLine).getText().isBlank());
		
		
		colorLine(currentLine, GuiColors.LINEPASSED);
		return true;
	}
	
	public boolean goToLine(int index) {
		
		currentLine = -1;
		int i = -1;
		
		do {
			if(currentLine >= sourceCode.size()-1)
				return false;
			
			goToNextLine();
			i++;
		}
		while(i < index);
		
		setVvalue( (1.0 / (sourceCode.size()-29))* (currentLine - 0.2));
		
		
		
		return true;
	}
	
	public void setSourceCodePath(String newPath) {
		sourceCodePath = newPath;
		loadSourceCode();
	}
	
	public void haltLine(int index) {
		clearColors();
		try {
			colorLine(index, GuiColors.LINEERROR);
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
		
		sourceCode = new ArrayList<>();
    	
    	Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("[ERROR] Couldn't find the file " + file.getAbsolutePath());
			
			return false;
		}
		
		var contentText = new VBox();
//		contentText.setPadding(new Insets(0, 0, 0, 0));
//		VBox.setMargin(contentText, new Insets(0, 0, 0, 0));
//		contentText.setPrefHeight(0);
		
    	
    	while(scanner.hasNextLine()) {
    		var line = new Text(scanner.nextLine());
    		line.setFont(Font.font("Arial", FontPosture.REGULAR, 20));
    		
    		var textLine = new TextLine(line);
    		
    		sourceCode.add(textLine);
    		contentText.getChildren().add(textLine);
    		//contentText.getChildren().add(new Text("\n"));
    	}
    	scanner.close();
    	
    	
    	
    	
    	
    	this.setContent(contentText);
    	
    	return true;
	}
	
	private void clearColors() {
		for(var line : sourceCode) {
			line.setBackground(
					new Background(
							new BackgroundFill(
									Color.WHITESMOKE, CornerRadii.EMPTY, Insets.EMPTY)));
		}
	}
	
	private void colorLine(int n, Color color) {
		
		clearColors();
		
		sourceCode.get(n).setBackground(
				new Background(
						new BackgroundFill(
								color, CornerRadii.EMPTY, Insets.EMPTY)));
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
	
	private class TextLine extends TextFlow{
		
		private Text text;
		
		public TextLine(Text text) {
			super(text);
			this.text = text;
			setPadding(new Insets(0));
			
		}
		
		public void setBackgroundColor(Color color) {
			setBackground(
					new Background(
							new BackgroundFill(
									color, CornerRadii.EMPTY, Insets.EMPTY)));
		}
		
		public String getText() {
			return text.getText();
		}
	}
	
	
}
