package ui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class Chooser extends Component {

    public Chooser() {

    }

    public String getPath(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT", "txt");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(this);

        if (result != JFileChooser.CANCEL_OPTION) {

            File file = fileChooser.getSelectedFile();

            if ((file == null) || (file.getName().equals(""))) {
                System.out.println("Invalid file");
            } else {
                return (file.getAbsolutePath());
            }
        }
        return null;
    }


}