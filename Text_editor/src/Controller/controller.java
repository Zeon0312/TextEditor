package Controller;

import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

import View.view;

public class controller {
        view View;
        String filename;
        String fileaddress;
        public controller(view View) {
        	this.View=View;
        }

public void newFile() {
	View.textArea.setText("");
	View.window.setTitle("New");
	filename=null;
	fileaddress=null;
}
public void open() {
FileDialog fd =new FileDialog(View.window, "Open",FileDialog.LOAD); 
fd.setVisible(true);
if(fd.getFile()!=null) {
	filename =fd.getFile();
	fileaddress =fd.getDirectory();
	View.window.setTitle(filename);
	 
}
System.out.println("File address and file name "+fileaddress+filename);
try {
	BufferedReader br = new BufferedReader(new FileReader(fileaddress+filename));
	View.textArea.setText("");
	String line=null;
	while((line =br.readLine())!=null) {
		View.textArea.append(line+ "\n");
	}br.close();
}catch(Exception e) {
	System.out.println("FILE NOT OPPENED!");
}
}
public void save() {
if (filename==null) {
	saveAs();
}else {
	try {
		FileWriter fw = new FileWriter(fileaddress+filename);
		fw.write(View.textArea.getText());
		View.window.setTitle(filename);
		fw.close();
	}catch (Exception e) {
		System.out.println("SOMETHING WRONG!");
	}
}
}
public void saveAs() {
FileDialog fd = new FileDialog(View.window, "Save",FileDialog.SAVE);
fd.setVisible(true);
if(fd.getFile()!=null) {
	filename=fd.getFile()
;
	fileaddress=fd.getDirectory();
	View.window.setTitle(filename);
	} 
		try {
		FileWriter fw = new FileWriter(fileaddress+filename);
		fw.write(View.textArea.getText());
		fw.close();
		}catch(Exception e) {}
		System.out.println("SOMETHING WRONG! ");

}
public void exit() {
System.exit(0);
}
}