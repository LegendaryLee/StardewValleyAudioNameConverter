package legendarylee.stardewvalley.audioconverter;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class App extends JFrame {

    private JPanel panel1;
    private JButton browseFolder;
    private JButton convertToIDButton;
    private JButton convertToNameButton;
    private JLabel audioLoc;
    private JTextArea textArea1;
    private JButton Browse;
    private JFileChooser chooser;
    private String chooserTitle = "Select a Folder";
    private File location = null;
    private static ArrayList<String> NAMES = new ArrayList<String>();
    private static ArrayList<String> IDS = new ArrayList<String>();
    private static boolean idsFound = true;
    private static boolean namesFound = true;

    public App(String title) {
        super(title);

        if (idsFound && namesFound) {
            textArea1.append("Click \"Browse\" to select a folder" + "\n");
        } else {
            if (!idsFound && !namesFound) {
                textArea1.append("ERROR: Files \"names.txt\" and \"ids.txt\" could not be found" + "\n");
            } else if (!idsFound) {
                textArea1.append("ERROR: File \"ids.txt\" could not be found" + "\n");
            } else {
                textArea1.append("ERROR: File \"names.txt\" could not be found" + "\n");
            }
        }

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(panel1);
        this.pack();
        browseFolder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Select a folder to use
                chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("."));
                chooser.setDialogTitle(chooserTitle);
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    //System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
                    //System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
                    location = chooser.getSelectedFile();
                    String temp = location.toString();
                    audioLoc.setText("..." + temp.substring(temp.lastIndexOf("\\")));
                } else {
                    textArea1.append("No Selection" + "\n");
                }
            }
        });
        convertToNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rename(IDS, NAMES);
            }
        });
        convertToIDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rename(NAMES, IDS);
            }
        });
    }

    public static void main(String[] args) throws FileNotFoundException {
        try {
            getKey("names.txt", NAMES);
            getKey("ids.txt", IDS);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        JFrame frame = new App("SV Audio Name Converter");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));
        frame.setVisible(true);
    }

    private void rename(ArrayList<String> nameFrom, ArrayList<String> nameTo) {
        boolean madeEdit = false;
        if (location != null) {
            for (int i = 0; i < Objects.requireNonNull(location.listFiles()).length; i++) {
                for (int s = 0; s < nameFrom.size(); s++) {
                    File t = new File(location.getAbsolutePath() + "/" + nameFrom.get(s) + ".wav");
                    File t2 = new File(location.getAbsolutePath() + "/" + nameTo.get(s) + ".wav");
                    if (t.renameTo(t2)) {
                        madeEdit = true;
                        String from = t.toString();
                        String to = t2.toString();
                        textArea1.append("Renaming " + from.substring(from.lastIndexOf("\\") + 1) + " to " + to.substring(from.lastIndexOf("\\") + 1) + "\n");
                    }
                }
            }
            if (!madeEdit) {
                textArea1.append("No audio files found" + "\n");
            }
        } else {
            textArea1.append("Please select a folder location" + "\n");
        }
    }

    private static void getKey(String file, ArrayList<String> LIST) throws IOException {
        BufferedReader br = null;
        try {
            FileInputStream in = new FileInputStream(file);
            br = new BufferedReader(new InputStreamReader(in));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            if (LIST == IDS) {
                idsFound = false;
            } else if (LIST == NAMES) {
                namesFound = false;
            }
        }
        if (br != null) {
            String line = br.readLine();
            while (line != null) {
                LIST.add(line);
                line = br.readLine();
            }
        } else {
            System.out.println("BufferedReader returned null");
        }
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(8, 3, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10), null));
        convertToIDButton = new JButton();
        convertToIDButton.setText("Convert to ID");
        panel1.add(convertToIDButton, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        browseFolder = new JButton();
        browseFolder.setText("Browse");
        panel1.add(browseFolder, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        audioLoc = new JLabel();
        audioLoc.setText("No Folder Selected");
        panel1.add(audioLoc, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, new Dimension(150, -1), new Dimension(150, -1), new Dimension(150, -1), 0, false));
        convertToNameButton = new JButton();
        convertToNameButton.setText("Convert to Name");
        panel1.add(convertToNameButton, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JSeparator separator1 = new JSeparator();
        panel1.add(separator1, new GridConstraints(2, 2, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Audio File Location:");
        panel1.add(label1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JScrollPane scrollPane1 = new JScrollPane();
        panel1.add(scrollPane1, new GridConstraints(2, 0, 4, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, new Dimension(250, 300), null, null, 0, false));
        textArea1 = new JTextArea();
        textArea1.setEditable(false);
        scrollPane1.setViewportView(textArea1);
        final JSeparator separator2 = new JSeparator();
        panel1.add(separator2, new GridConstraints(6, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Developed by LegendaryLee");
        panel1.add(label2, new GridConstraints(7, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }

}
