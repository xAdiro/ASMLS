package com.adiro.nasm_ide.gui.button;

import com.adiro.nasm_ide.gui.content.ContentView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class MoveToStartButton extends Button{
	public MoveToStartButton(ContentView contentView) {
		super("<<<");
		this.setOnAction(new EventHandler<ActionEvent> () {
			@Override public void handle(ActionEvent e) {
				moveToEnd(contentView);
			}
		});
	
	}
	

	private void moveToEnd(ContentView contentView) {
			
		while(contentView.prevLine()) {}
			
	}

}
