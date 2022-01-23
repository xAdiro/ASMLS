package com.adiro.nasm_ide.logic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.adiro.nasm_ide.gui.content.RegistersView;
import com.adiro.nasm_ide.gui.content.SourceCodeView;

public class LogReader {
	
	RegistersView registers;
	SourceCodeView sourceArea;
	int currentStep = 0;
	private String filePrefix = "/home/adrian/Dokumenty/Dos/ide/debug/";
	
	public LogReader(RegistersView registers, SourceCodeView sourceArea) {
		this.registers = registers;
		this.sourceArea = sourceArea;
	}
	
	public boolean NextLine(){
		
		Path path = Paths.get(getFileName());
		byte[] step = null;
		
		try {
			step = Files.readAllBytes(path);
		} catch (IOException e) {
			sourceArea.haltLine(sourceArea.getCurrentLine()+1);
			return false;
		}
		currentStep++;
		
		
		sourceArea.goToLine(readRegister(step, 0));
		setRegisters(step);
		
		
		
		
		return true;
	}
	
	public boolean prevLine() {
		
		if(currentStep>1) {
			currentStep -=2;
			NextLine();
			return true;
		}
		
		
		return false;
	}
	
	private void setRegisters(byte[] bytes) {
		
		registers.setAx(readRegister(bytes, 1));
		registers.setBx(readRegister(bytes, 2));
		registers.setCx(readRegister(bytes, 3));
		registers.setDx(readRegister(bytes, 4));
	}
	
	private int readRegister(byte[] bytes, int n) {

		
		
		
		int value = (int) (ConvertToUnsignedValue(bytes[2*n]) << 8);
		value += ConvertToUnsignedValue(bytes[2*n+1]);
		return value;
		
	}
	
	@SuppressWarnings("unused")
	private void printRegisters(byte[] bytes) {
		for(var register : bytes) {
			if(register < 0) {
				System.out.println(register+256);
			}
			else {
				System.out.println(register);
			}
		}
	}
	
	private int ConvertToUnsignedValue(byte signedByte) {
		
		final int bias = 256;
		
		if(signedByte < 0) {
			return (int)signedByte + bias;
		}
		return (int)signedByte;

	}
	
	private String getFileName() {
		var fileName = filePrefix + String.format("%05d", currentStep) + "DEB.LOG";
		System.out.println("Debug file: " + fileName);
		return fileName;
	}
	
	public void setLogDirectory(String location) {
		filePrefix = location;
	}
	
}
