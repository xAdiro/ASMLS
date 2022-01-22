package com.adiro.nasm_ide.gui.content;

import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;

public class RegistersView extends VBox{
	
	private Register ax;
	private Register bx;
	private Register cx;
	private Register dx;

	public RegistersView() {
		super();
		
		ax = new Register(" A");
		bx = new Register(" B");
		cx = new Register(" C");
		dx = new Register(" D");
		
		
		getChildren().add(ax);
		getChildren().add(new Separator());
		getChildren().add(bx);
		getChildren().add(new Separator());
		getChildren().add(cx);
		getChildren().add(new Separator());
		getChildren().add(dx);
	}
	
	public void setAx(int newValue) {
		ax.setValue(newValue);
	}
	
	public void setBx(int newValue) {
		bx.setValue(newValue);
	}
	
	public void setCx(int newValue) {
		cx.setValue(newValue);
	}
	
	public void setDx(int newValue) {
		dx.setValue(newValue);
	}
	
	private class Register extends HBox{
		int value = 0;
		private Text rxText;
		private Text rlText;
		private Text rhText;
		private String registerLetter;
		
		public Register(String registerLetter) {
			super();
			this.registerLetter = registerLetter;
			
			rxText = new Text(registerLetter + "X: " + Integer.toString(value));
			rxText.setFont(Font.font("Arial", FontPosture.REGULAR, 20));
			var rx = new HBox(rxText);
			rx.setMinWidth(150);
			
			rlText = new Text(registerLetter + "L: " + Integer.toString(value & 0x00FF));
			rlText.setFont(Font.font("Arial", FontPosture.REGULAR, 12));
			var rl = new HBox(rlText);
			rl.setMinWidth(100);
			
			rhText = new Text(registerLetter + "H: " + Integer.toString(value >> 8));
			rhText.setFont(Font.font("Arial", FontPosture.REGULAR, 12));
			var rh = new HBox(rhText);
			rh.setMinWidth(100);
			
			var halves = new VBox(rh, rl);
			
			getChildren().addAll(rx, halves);
		}
		
		public void setValue(int newValue) {
			rxText.setText(registerLetter + "X: " + Integer.toString(newValue));
			rlText.setText(registerLetter + "L: " + Integer.toString(newValue & 0x00FF));
			rhText.setText(registerLetter + "H: " + Integer.toString(newValue >> 8));
			
		}
	}
}
