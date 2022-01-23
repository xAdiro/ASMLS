package com.adiro.nasm_ide.gui.content;

import java.io.File;

import com.adiro.nasm_ide.logic.LogReader;

import javafx.scene.layout.HBox;

public class ContentView extends HBox{
	
		private RegistersView registersView;
		private SourceCodeView scView;
		private LogReader logReader;
	
		public ContentView() {
			
			super();
			
			this.registersView = new RegistersView();
			this.scView = new SourceCodeView();
			this.logReader = new LogReader(registersView, scView);
			
			getChildren().addAll(scView, registersView);
			
		}
		
		public boolean nextLine() {
			return logReader.NextLine();
		}
		
		public boolean prevLine() {
			return logReader.prevLine();
		}
		
		//public void haltLine() {
		//	scView.haltLine();
		//}
		
		public void setSourceCodePath(String path) {
			scView.setSourceCodePath(path);
			
			logReader.setLogDirectory(getFileDirectory(path) + "/debug/");
		}
		
		private String getFileDirectory(String filePath) {
			File sourceFile = new File(filePath);
			return sourceFile.getParent();
		}
}
