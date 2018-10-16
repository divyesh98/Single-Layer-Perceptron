package javaapplication81;

import java.awt.Color;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class JavaApplication81{
    
    private static int maxTimes = 1000;
    private static double rate = 0.1;
    private static double threshold = 0;
    private static double minRange = -0.5;
    private static double maxRange = 0.5;
    
    //adding all left side labels
    static JLabel learning_rate = new JLabel("Learning Rate");
    static JLabel init_thr = new JLabel("Initial Threshold");
    static JLabel maxConverg = new JLabel("Maximum Convergence");
    static JLabel init_Weight = new JLabel("Initial Weights");
    static JLabel semi_colon = new JLabel("~");
    static JLabel converg_time = new JLabel("Convergence Times");
    static JLabel final_threshold = new JLabel("Final Threshold");
    static JLabel synp_weights = new JLabel("Synapic Weights");
    static JLabel trans_rate = new JLabel("Training Recognition Rate");
    static JLabel test_rate = new JLabel("Testing Recognition Rate");
    static JLabel test_data = new JLabel("Testing Data");
    static JLabel train_data = new JLabel("Training Data");
    //end of adding left side labels
    
    static JPanel layoutPanel = new JPanel();
    static JMenuItem loadMenuItem;
    static JMenuItem generateMenuItem;
    static JFrame frame = new JFrame();
    static JButton loadButton = new JButton("Load");
    static JButton generateButton = new JButton("Generate");
    static JLabel loadValue = new JLabel("File not loaded");
    static JTextField learningTextField = new JTextField();
    static JTextField thresholdTextField = new JTextField();
    static JLabel trainingValue = new JLabel("?");
    static JLabel testingValue = new JLabel("?");
    static JLabel weightsValue = new JLabel("?");
    //static JSlider zoomerSlider;
    static JLabel timesValue = new JLabel("?");
    static JLabel fThresholdValue = new JLabel("?");
    static JTextField maxTimesValue = new JTextField();
    static JTextField wRangeMinValue = new JTextField();
    static JTextField wRangeMaxValue = new JTextField();
    static JTable trainTable = new JTable();
    static JTable testTable = new JTable();
    static DefaultTableModel trainTableModel = new DefaultTableModel();
    static DefaultTableModel testTableModel = new DefaultTableModel();
    static DecimalFormat df = new DecimalFormat("####0.00");
    
    private Color[] colorArray = {Color.GREEN, Color.BLUE, Color.RED, Color.YELLOW, Color.CYAN, Color.PINK, Color.BLACK,
                                    Color.MAGENTA, Color.GRAY};
    
    private ArrayList<Double[]> input = new ArrayList<>();
    private ArrayList<Double[]> trainData = new ArrayList<>();
    private ArrayList<Double[]> testData = new ArrayList<>();
    private ArrayList<Double[]> weights = new ArrayList<>();
    private ArrayList<Double> outputKinds = new ArrayList<>();
    
    //private Point mouse;
    
    JavaApplication81(){
        
        String an = Double.toString(rate);
        learningTextField.setText(an);
        
        an = Double.toString(threshold);
        thresholdTextField.setText(an);
        
        an = Double.toString(minRange);
        wRangeMinValue.setText(an);
        
        an = Double.toString(maxRange);
        wRangeMaxValue.setText(an);
        
        an = Integer.toString(maxTimes);
        maxTimesValue.setText(an);

        loadButton.addActionListener(new ActionListener(){
            
            public void actionPerformed(ActionEvent e){
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files(*.txt)", "txt", "text");
                fileChooser.setFileFilter(filter);
                if (fileChooser.showOpenDialog(layoutPanel) == JFileChooser.APPROVE_OPTION) {
                    loadFile(fileChooser);
                }
            }
        });
        
/*        loadMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files(*.txt)", "txt", "text");
                fileChooser.setFileFilter(filter);
                if (fileChooser.showOpenDialog(layoutPanel) == JFileChooser.APPROVE_OPTION) {
                    loadFile(fileChooser);
                }
            }
        });
        
  */      
        generateButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                startTrain();
             }
        });
        
  /*      generateMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                startTrain();
            }
        });
    */    
        learningTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                changeRate();
            }

            public void removeUpdate(DocumentEvent e) {
                changeRate();
            }

            public void insertUpdate(DocumentEvent e) {
                changeRate();
            }

            void changeRate() {
                try {
                    alertBackground(learningTextField, false);
                    rate = Double.valueOf(learningTextField.getText());
                    startTrain();
                } catch (NumberFormatException e) {
                    alertBackground(learningTextField, true);
                    rate = 0.5f;
                }
            }
        });
        
        thresholdTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                changeThreshold();
            }

            public void removeUpdate(DocumentEvent e) {
                changeThreshold();
            }

            public void insertUpdate(DocumentEvent e) {
                changeThreshold();
            }

            void changeThreshold() {
                try {
                    alertBackground(thresholdTextField, false);
                    threshold = Double.valueOf(thresholdTextField.getText());
                    startTrain();
                } catch (NumberFormatException e) {
                    alertBackground(thresholdTextField, true);
                    threshold = 0;
                }
            }
        });
        
        maxTimesValue.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                changeMaxTimes();
            }

            public void removeUpdate(DocumentEvent e) {
                changeMaxTimes();
            }

            public void insertUpdate(DocumentEvent e) {
                changeMaxTimes();
            }

            void changeMaxTimes() {
                try {
                    alertBackground(maxTimesValue, false);
                    maxTimes = Integer.valueOf(maxTimesValue.getText());
                    startTrain();
                } catch (NumberFormatException e) {
                    alertBackground(maxTimesValue, true);
                    maxTimes = 1000;
                }
            }
        });
        
        wRangeMinValue.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                changeMinRange();
            }

            public void removeUpdate(DocumentEvent e) {
                changeMinRange();
            }

            public void insertUpdate(DocumentEvent e) {
                changeMinRange();
            }

            void changeMinRange() {
                try {
                    if (Double.valueOf(wRangeMinValue.getText()) > maxRange)
                        alertBackground(wRangeMinValue, true);
                    else {
                        alertBackground(wRangeMinValue, false);
                        minRange = Double.valueOf(wRangeMinValue.getText());
                        startTrain();
                    }
                } catch (NumberFormatException e) {
                    alertBackground(wRangeMinValue, true);
                    minRange = -0.5f;
                }
            }
        });
        
        wRangeMaxValue.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                changeMaxRange();
            }

            public void removeUpdate(DocumentEvent e) {
                changeMaxRange();
            }

            public void insertUpdate(DocumentEvent e) {
                changeMaxRange();
            }

            void changeMaxRange() {
                try {
                    if (Double.valueOf(wRangeMaxValue.getText()) < minRange)
                        alertBackground(wRangeMaxValue, true);
                    else {
                        alertBackground(wRangeMaxValue, false);
                        maxRange = Double.valueOf(wRangeMaxValue.getText());
                        startTrain();
                    }
                } catch (NumberFormatException e) {
                    alertBackground(wRangeMaxValue, true);
                    maxRange = 0.5f;
                }
            }
        });
    }
    
    private void resetData() {
        input.clear();
        trainData.clear();
        testData.clear();
        outputKinds.clear();
        trainTableModel.setColumnCount(0);
        trainTableModel.setRowCount(0);
        testTableModel.setColumnCount(0);
        testTableModel.setRowCount(0);
    }
    
    private static void resetFrame() {
        SwingUtilities.updateComponentTreeUI(frame);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }
    
    private void trainPerceptron(Double dy) {
        if (trainData.size() == 0) return;
        Double[] w = new Double[trainData.get(0).length - 1];
        w[0] = threshold;
        for (int i = 1; i < trainData.get(0).length - 1; i++)
            w[i] = getRandomNumber();
        int wi = outputKinds.indexOf(dy);
        if (wi == 0) weights.clear();
        weights.add(w);
        int times = 0, correct = 0;
        while (times < maxTimes) {
            correct = 0;
            for (Double[] x : trainData) {
                Double sum = 0.0;
                for (int i = 0; i < weights.get(wi).length; i++) {
                    sum += weights.get(wi)[i] * x[i];
                }
                Double fx = Math.signum(sum);
                Double y = (x[x.length - 1].equals(dy)) ? 1.0 : -1.0;
                Double e = y - fx;
                if (e == 0) ++correct;
                for (int i = 0; i < weights.get(wi).length; i++) {
                    weights.get(wi)[i] = weights.get(wi)[i] + rate * e * x[i];
                }
            }
            if (correct == trainData.size()) break;
            ++times;
        }
        StringBuilder weightOutput = new StringBuilder("(");
        weightOutput.append(df.format(weights.get(wi)[1]));
        for (int i = 2; i < weights.get(wi).length; i++) {
            weightOutput.append(", ").append(df.format(weights.get(wi)[i]));
        }
        weightOutput.append(")");
        timesValue.setText(String.valueOf(times));
        weightsValue.setText(weightOutput.toString());
        fThresholdValue.setText(weights.get(wi)[0].toString());
        trainingValue.setText((double) correct / trainData.size() * 100 + "%");
        testPerceptron(dy);
    }
    
    private void testPerceptron(Double dy) {
        
        if (testData.size() == 0) return;
        
        int wi = outputKinds.indexOf(dy);
        
        int correct = 0;
        
        for (Double[] x : testData) {
            Double sum = 0.0;
            for (int i = 0; i < weights.get(wi).length; i++) {
                sum += weights.get(wi)[i] * x[i];
            }
            Double fx = Math.signum(sum);
            Double y = (x[x.length - 1].equals(dy)) ? 1.0 : -1.0;
            Double e = y - fx;
            if (e == 0) ++correct;
        }
        testingValue.setText((double) correct / testData.size() * 100 + "%");
        
    }
    
    private void startTrain() {
        if (outputKinds.size() > 2)
            outputKinds.forEach(this::trainPerceptron);
        else
            trainPerceptron(outputKinds.get(0));
        
    }
    
    private void alertBackground(JTextField textField, boolean alert) {
        if (alert)
            textField.setBackground(Color.PINK);
        else
            textField.setBackground(Color.WHITE);
    }
    
    private void loadFile(JFileChooser fileChooser) {
        File loadedFile = fileChooser.getSelectedFile();
        loadValue.setText(loadedFile.getPath());
        resetFrame();
        resetData();
        try (BufferedReader br = new BufferedReader(new FileReader(loadedFile))) {
            String line = br.readLine();
            while (line != null) {
                // Split by space or tab
                String[] lineSplit = line.split("\\s+");
                // Remove empty elements
                lineSplit = Arrays.stream(lineSplit).
                        filter(s -> (s != null && s.length() > 0)).
                        toArray(String[]::new);
                Double[] numbers = new Double[lineSplit.length + 1];
                numbers[0] = -1.0;
                for (int i = 1; i <= lineSplit.length; i++) {
                    numbers[i] = Double.parseDouble(lineSplit[i - 1]);
                }
                input.add(numbers);
                line = br.readLine();
            }
            for (Double[] x : input) {
                Double output = x[x.length - 1];
                if (!outputKinds.contains(output))
                    outputKinds.add(output);
            }
            int[] trainKindTimes = new int[outputKinds.size()];
            int[] testKindTimes = new int[outputKinds.size()];
            for (Double[] x : input) {
                Double output = x[x.length - 1];
                int i;
                for (i = 0; i < outputKinds.size(); i++)
                    if (output.equals(outputKinds.get(i)))
                        break;
                if (trainKindTimes[i] == 0 || testKindTimes[i] > trainKindTimes[i] / 2) {
                    ++trainKindTimes[i];
                    trainData.add(x);
                } else {
                    ++testKindTimes[i];
                    testData.add(x);
                }
            }
            ArrayList<String> header = new ArrayList<>();
            header.add("w");
            for (int i = 1; i < trainData.get(0).length - 1; i++)
                header.add("x" + i);
            header.add("yd");
            trainTableModel.setColumnIdentifiers(header.toArray());
            testTableModel.setColumnIdentifiers(header.toArray());
            for (Double[] x : trainData)
                trainTableModel.addRow(x);
            for (Double[] x : testData)
                testTableModel.addRow(x);
            trainTable.setModel(trainTableModel);
            testTable.setModel(testTableModel);
            // TODO - show y result at data table
            generateButton.setEnabled(true);
            startTrain();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
    
    private Double getRandomNumber() {
        
        Random r = new Random();
        
        return minRange + (maxRange - minRange) * r.nextDouble();
    
    }
    
    public static void main(String args[]){
        
        JavaApplication81 get = new JavaApplication81();
        
        JMenuBar menuBar = new JMenuBar();
        JMenu filesMenu = new JMenu("Files");
        
        loadMenuItem = new JMenuItem("Load", KeyEvent.VK_L);
        generateMenuItem = new JMenuItem("Generate", KeyEvent.VK_G);
        filesMenu.setMnemonic(KeyEvent.VK_F);
        filesMenu.add(loadMenuItem);
        filesMenu.add(generateMenuItem);
        menuBar.add(filesMenu);
        
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(70, 10, 1250, 700);
        
        Container c = frame.getContentPane();
        c.setLayout(null);
        
        
        loadButton.setBounds(100, 50, 350, 20);
        
        generateButton.setBounds(100, 550, 350, 20);
        
        loadValue.setBounds(100, 100, 650, 20);
        
        learningTextField.setBounds(250, 160, 200, 20);
        thresholdTextField.setBounds(250, 190, 200, 20);
        learning_rate.setBounds(100, 160, 150, 20);
        init_thr.setBounds(100, 190, 150, 20);
        
        maxConverg.setBounds(100,220,150,20);
        init_Weight.setBounds(100,250,150,20);
        semi_colon.setBounds(333,250,50,20);
        converg_time.setBounds(100,280,150,20);
        final_threshold.setBounds(100,310,150,20);
        synp_weights.setBounds(100,340,150,20);
        trans_rate.setBounds(100,370,150,20);
        test_rate.setBounds(100,400,150,20);
        
        fThresholdValue.setBounds(250,310,200,20);
        timesValue.setBounds(250, 280, 200, 20);
        trainingValue.setBounds(250, 370, 200, 20);
        testingValue.setBounds(250, 400, 200 ,20);
        weightsValue.setBounds(250, 340, 200, 20);
        
        maxTimesValue.setBounds(250, 220, 200, 20);
        wRangeMinValue.setBounds(250, 250, 75, 20);
        wRangeMaxValue.setBounds(350, 250, 75, 20);
        
        test_data.setBounds(850, 50, 200, 20);
        train_data.setBounds(850, 350, 200, 20);
        
        trainTable.setBounds(700, 120, 400, 200);
        testTable.setBounds(700, 420, 400, 200);
        
        c.add(loadButton);  c.add(generateButton);  c.add(loadValue);   c.add(learningTextField);
        c.add(thresholdTextField);  c.add(learning_rate);   c.add(init_thr);    c.add(maxConverg);
        c.add(init_Weight); c.add(semi_colon);      c.add(converg_time);        c.add(final_threshold);
        c.add(synp_weights);    c.add(trans_rate);  c.add(test_rate);       c.add(fThresholdValue);
        c.add(timesValue);  c.add(trainingValue);   c.add(testingValue);    c.add(weightsValue);
        c.add(maxTimesValue);   c.add(wRangeMinValue);  c.add(wRangeMaxValue);  c.add(test_data);
        c.add(train_data);      c.add(trainTable);      c.add(testTable);
    }

}