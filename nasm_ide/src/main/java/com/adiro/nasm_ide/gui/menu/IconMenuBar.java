package com.adiro.nasm_ide.gui.menu;

import com.adiro.nasm_ide.gui.button.ChangeSourceFileButton;
import com.adiro.nasm_ide.gui.button.MoveBackButton;
import com.adiro.nasm_ide.gui.button.MoveNextButton;
import com.adiro.nasm_ide.gui.button.MoveToEndButton;
import com.adiro.nasm_ide.gui.content.ContentView;

import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class IconMenuBar extends HBox{
	
	public IconMenuBar(ContentView contentView, Stage stage) {
		super();
		
		var b1 = new ChangeSourceFileButton(contentView, stage);
		var b2 = new MoveBackButton(contentView);
		var b3 = new MoveNextButton(contentView);
		var b4 = new MoveToEndButton(contentView);
		
		
		getChildren().addAll(b1, b2, b3, b4);
	}

}
