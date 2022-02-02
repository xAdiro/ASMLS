package com.adiro.nasm_ide.gui.menu;

import com.adiro.nasm_ide.gui.button.ChangeSourceFileButton;
import com.adiro.nasm_ide.gui.button.MoveBackButton;
import com.adiro.nasm_ide.gui.button.MoveNextButton;
import com.adiro.nasm_ide.gui.button.MoveToEndButton;
import com.adiro.nasm_ide.gui.button.MoveToStartButton;
import com.adiro.nasm_ide.gui.content.ContentView;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class IconMenuBar extends VBox{
	
	public IconMenuBar(ContentView contentView, Stage stage) {
		super();
		
		var b1 = new ChangeSourceFileButton(contentView, stage);
		var b2 = new MoveToStartButton(contentView);
		var b3 = new MoveBackButton(contentView);
		var b4 = new MoveNextButton(contentView);
		var b5 = new MoveToEndButton(contentView);
		
		var buttons = new ButtonSet(b1, b2, b3, b4, b5);
		
		setPadding(new Insets(5));
		setSpacing(5);
		
		
		getChildren().addAll(buttons, new Separator());
	}
	
	private class ButtonSet extends HBox{
		public ButtonSet(Button... buttons){
			super(buttons);
			
		}
	}

}
