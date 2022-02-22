package com.adiro.asmls.logic;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.adiro.asmls.App;
import com.adiro.asmls.gui.content.RegistersView;
import com.adiro.asmls.gui.content.codeview.CodeView;

public class LogReader {

    RegistersView registers;
    CodeView code;
    int currentStep = 0;
    private String filePrefix;

    public LogReader(RegistersView registers, CodeView sourceArea) {
        this.registers = registers;
        this.code = sourceArea;

        try {
            var resource = ".source";
            BufferedReader br = new BufferedReader(new FileReader(resource));
            setLogDirectory(new File(br.readLine()).getParent()+"/debug/");
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void goToFirstLine(){
        byte[] step = null;
        try {
            Path path = Paths.get(getFileName(0));
            step = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        currentStep = 0;

        code.goToLine(readRegister(step, 0));
        setRegisters(step);
        registers.setFlags(readRegister(step, 9));
    }

    public void goToLastLine(){
        byte[] step = null;
        Path path = null;
        while(true){
            Path checkedPath = Paths.get(getFileName());
            if(!Files.exists(checkedPath)){
                break;
            }
            path = checkedPath;
            currentStep++;
        }
        if(path == null) return; // already at last line

        try {
            step = Files.readAllBytes(path);
        } catch (IOException e) {
            code.haltLine(code.getCurrentLine()+1);
            return;
        }

        code.goToLine(readRegister(step, 0));
        setRegisters(step);
        registers.setFlags(readRegister(step, 9));
    }

    public boolean nextLine(){

        byte[] step = null;
        try {
            Path path = Paths.get(getFileName());
            step = Files.readAllBytes(path);
        } catch (IOException e) {
            code.haltLine(code.getCurrentLine()+1);
            return false;
        }
        currentStep++;


        code.goToLine(readRegister(step, 0));
        setRegisters(step);
        registers.setFlags(readRegister(step, 9));




        return true;
    }

    public boolean prevLine() {

        if(currentStep>1) {
            currentStep -=2;
            nextLine();
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

    private String getFileName(int fileId){
        var fileName = filePrefix + String.format("%05d", fileId) + "DEB.LOG";
        System.out.println("Debug file: " + fileName);
        return fileName;
    }

    public void setLogDirectory(String location) {
        filePrefix = location;
    }

}

