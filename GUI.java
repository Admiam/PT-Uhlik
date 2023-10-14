import java.awt.event.ActionEvent;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class GUI  extends JFrame implements ActionListener{
    private Container c;
    private JLabel title;
    private JLabel input;
    private JTextField tInput;
    private JLabel output;
    private JTextField tOutput;
    private JButton submitBtn;
    private Input file;

    public GUI(Input in) {
    	
    	this.file = in;
        setTitle("Sto roků v šachtě žil");
        setBounds(300, 90, 600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("PT Uhlík");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(250, 30);
        c.add(title);

        input = new JLabel("Input");
        input.setFont(new Font("Arial", Font.PLAIN, 20));
        input.setSize(100, 20);
        input.setLocation(150, 100);
        c.add(input);

        tInput = new JTextField();
        tInput.setFont(new Font("Arial", Font.PLAIN, 15));
        tInput.setSize(190, 20);
        tInput.setLocation(250, 100);
        c.add(tInput);

        output = new JLabel("Output");
        output.setFont(new Font("Arial", Font.PLAIN, 20));
        output.setSize(100, 20);
        output.setLocation(150, 200);
        c.add(output);

        tOutput = new JTextField();
        tOutput.setFont(new Font("Arial", Font.PLAIN, 15));
        tOutput.setSize(190, 20);
        tOutput.setLocation(250, 200);
        c.add(tOutput);

        submitBtn = new JButton("Submit");
        submitBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        submitBtn.setSize(100, 20);
        submitBtn.setLocation(250, 300);
        submitBtn.addActionListener(this);
        c.add(submitBtn);

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitBtn){
        	
        	if(tInput.getText() == null) {
        		file.setInput(tInput.getText());
        	}
        	if(tOutput.getText() == null) {
        		file.setOutput(tOutput.getText());
        	}
            file.read();

            System.out.println("input: " + tInput.getText() + " , output: " + tOutput.getText());
        }
    }
}
