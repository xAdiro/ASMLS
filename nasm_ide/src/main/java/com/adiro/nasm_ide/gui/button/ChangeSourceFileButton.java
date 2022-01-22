package com.adiro.nasm_ide.gui.button;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.adiro.nasm_ide.gui.content.ContentView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ChangeSourceFileButton extends Button{
	public ChangeSourceFileButton(ContentView contentView, Stage stage) {
		super();
		var icon = new ImageView("file:resources/icons/file.png");
		icon.setPreserveRatio(true);
		icon.setFitHeight(17);
		setGraphic(icon);
		this.setOnAction(new EventHandler<ActionEvent> () {
			@Override public void handle(ActionEvent e) {
				changeFile(contentView, stage);
			}
		});
	
	}
	
	private void changeFile(ContentView contentView, Stage stage) {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		
		File defaultDir = new File(getPrevDirectory());
		fileChooser.setInitialDirectory(defaultDir);
		System.out.println(defaultDir);
		
		
		File selectedFile = fileChooser.showOpenDialog(stage);
		
		String filePath;
		if(selectedFile != null) {
			
			filePath = selectedFile.getAbsolutePath();
			contentView.setSourceCodePath(filePath);
			setPrevLocation(filePath);
			System.out.println("Selceted: " + filePath);
		}
		else {
			System.out.println("No file selected, nothing to do");
		}
		
		
		
		
	}
	
	private String getPrevDirectory() {
		
		Path path = Paths.get("resources/locations/source");
		String prevLocation = "";
		
		try {
			prevLocation = Files.readAllLines(path).get(0);
			prevLocation = getFileDirectory(prevLocation);
			
			
		} catch (IOException e) {
			System.out.println("[WARNING] Missing resources/locations/source, the program might be corrupted");
		}
		
		if(prevLocation.isBlank()) prevLocation = "./";
		return prevLocation;
	}
	
	private boolean setPrevLocation(String location) {
		try {
			FileWriter writer = new FileWriter("resources/locations/source");
			writer.write(location);
			writer.flush();
			writer.close();
			
			
			
		} catch (IOException e) {
			System.out.print("[WARNING] Missing resources/locations/source, the program might be corrupted");
		}
		return false;
		
	}



	private String getFileDirectory(String filePath) {
		File sourceFile = new File(filePath);
		return sourceFile.getParent();
	}
}
