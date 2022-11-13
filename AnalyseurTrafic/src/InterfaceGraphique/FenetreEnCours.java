package InterfaceGraphique;


import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import Protocole.FacadeTrame;
import TraitementFichier.FileReader;

import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class FenetreEnCours {
    FenetreInit window;

    public FenetreEnCours() {
        window = new FenetreInit();
        window.btnFind.addActionListener(this::pathButton);
        window.btnStart.addActionListener(this::startButton);
        window.btnSave.addActionListener(this::saveButton);
    }

    private void pathButton(ActionEvent e) {
        //JOptionPane.showMessageDialog(null, "Ton message");
        JFileChooser fileChooser = new JFileChooser();
        //file name filter
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file","txt");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(window);
        if(result == JFileChooser.APPROVE_OPTION){
            window.setPath(fileChooser.getSelectedFile().getAbsolutePath());
            startButton(e);
        }
    }
    
    private void startButton(ActionEvent e){
        String path = window.getPath();
        int i=1;
        try{
            if(!path.isEmpty()){
                window.clearOutput(); 
                FileReader file = new FileReader(path);
                for(ArrayList<String> octet :file.getOctet()){
                    FacadeTrame ft = new FacadeTrame(i,octet);
                    window.appendOutput(ft.toString());
                    i++;
                }
            }
            else{
                window.appendOutput("File path - Error");
            }
        }
        catch(Exception exc){
            window.createError(exc.getMessage());
        }
    }
    
    private void saveButton(ActionEvent e){
        JFileChooser fileChooser = new JFileChooser();
        //file name filter
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file","txt");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showSaveDialog(window);
        if(result == JFileChooser.APPROVE_OPTION){
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            path = getPathWithExtensionName(path, "txt");
            try{
                BufferedWriter out = new BufferedWriter(new FileWriter(path));
                out.write(window.getResult());
                out.close();
                JOptionPane.showMessageDialog(window, "Success");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(window, "Can't write in the file");
            }
        }
    }

    private String getPathWithExtensionName(String path, String extension){
        String[] pathSplit = path.split("\\.");
        if(pathSplit[pathSplit.length-1].trim().equals(extension)){
            return path;
        }
        return path+"."+extension;
    }


    public void start() {
        window.start(); 
    }
}