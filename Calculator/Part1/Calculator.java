import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
final public class Calculator{
    private JTextField frontText,operText,backText,ansText,equalText;
    private JButton addButton,minusButton,multipleButton,divideButton,calculateButton;
    private int buttonWidth = 100;
    private int borderWidth = 10;
    private Calculator()
    {
        JFrame frame = new JFrame();
        frame.setSize(5*buttonWidth+6*borderWidth,2*(buttonWidth+borderWidth));
        //close the programme when click the default close button
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        //the font of all
        Font font = new Font("SansSerif",Font.BOLD,20);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new FlowLayout());

//TextField set for Textfield above. Using absolute position and set font and horizontal alignment.
        //the first text field that can input.
        frontText = new JTextField();
        frontText.setBounds(0,0,buttonWidth,buttonWidth);
        frontText.setHorizontalAlignment(JTextField.CENTER);
        frontText.setFont(font);
        //the operator text, which can show operator when clicking the button below
        operText = new JTextField();
        operText.setEditable(false);
        operText.setBounds(buttonWidth+borderWidth,0,buttonWidth,buttonWidth);
        operText.setHorizontalAlignment(JTextField.CENTER);
        operText.setFont(font);
        //the second text field that can input
        backText = new JTextField();
        backText.setBounds((buttonWidth+borderWidth)*2,0,buttonWidth,buttonWidth);
        backText.setHorizontalAlignment(JTextField.CENTER);
        backText.setFont(font);
        //show the equal symbol, no other meaning
        equalText = new JTextField("=");
        equalText.setEditable(false);
        equalText.setBounds((buttonWidth+borderWidth)*3,0,buttonWidth,buttonWidth);
        equalText.setHorizontalAlignment(JTextField.CENTER);
        equalText.setFont(font);
        //the ans text which shows the answer
        ansText = new JTextField();
        ansText.setEditable(false);
        ansText.setBounds((buttonWidth+borderWidth)*4,0,buttonWidth,buttonWidth);
        ansText.setHorizontalAlignment(JTextField.CENTER);
        ansText.setFont(font);

//next is the button on the second row
        //add button
        addButton = new JButton("+");
        addButton.setBounds(0,buttonWidth+borderWidth,buttonWidth,buttonWidth);
        addButton.setFont(font);
        //minius button
        minusButton = new JButton("-");
        minusButton.setBounds(buttonWidth+borderWidth,buttonWidth+borderWidth,buttonWidth,buttonWidth);
        minusButton.setFont(font);
        //multiple button
        multipleButton = new JButton("*");
        multipleButton.setBounds((buttonWidth+borderWidth)*2,buttonWidth+borderWidth,buttonWidth,buttonWidth);
        multipleButton.setFont(font);
        //divide button
        divideButton = new JButton("/");
        divideButton.setBounds((buttonWidth+borderWidth)*3,buttonWidth+borderWidth,buttonWidth,buttonWidth);
        divideButton.setFont(font);
        //equal button
        calculateButton = new JButton("=");
        calculateButton.setBounds((buttonWidth+borderWidth)*4,buttonWidth+borderWidth,buttonWidth,buttonWidth);
        calculateButton.setFont(font);
//Action that bind to all buttons and solve the calculate
        ActionListener calculateEvent = new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
            	//legal check
                if(e.getSource()==calculateButton)
                {
                    if(!legalCheck(frontText.getText(),backText.getText()))
                    {
                        return;
                    }
                    int a=Integer.parseInt(frontText.getText());
                    int b=Integer.parseInt(backText.getText());
                    ansText.setText(String.valueOf(getAns(a,b)));
                }
                else
                {
                    setButtonText(e);
                }
            }
        };
        addButton.addActionListener(calculateEvent);
        minusButton.addActionListener(calculateEvent);
        multipleButton.addActionListener(calculateEvent);
        divideButton.addActionListener(calculateEvent);
        calculateButton.addActionListener(calculateEvent);

        frame.add(frontText);
        frame.add(operText);
        frame.add(backText);
        frame.add(equalText);
        frame.add(ansText);
        frame.add(addButton);
        frame.add(minusButton);
        frame.add(multipleButton);
        frame.add(divideButton);
        frame.add(calculateButton);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        new Calculator();
    }

    private int getAns(int a,int b)
    {
        if(operText.getText().equals("+"))
        {
            return a+b;
        }
        else if(operText.getText().equals("-"))
        {
            return a-b;
        }
        else if(operText.getText().equals("*"))
        {
            return a*b;
        }
        else if(operText.getText().equals("/"))
        {
            return a/b;
        }
        return -1;
    }
    private void setButtonText(ActionEvent e)
    {
        if(e.getSource()==addButton)
        {
            operText.setText("+");
        }
        else if(e.getSource()==minusButton)
        {
            operText.setText("-");
        }
        else if(e.getSource()==multipleButton)
        {
            operText.setText("*");
        }
        else if(e.getSource()==divideButton)
        {
            operText.setText("/");
        }
    }
    private boolean legalCheck(String s1,String s2)
    {
        if(s1.equals("")||s2.equals(""))
        {
            return false;
        }
        String operator = operText.getText();
        if(s2.equals("0")&&operator.equals("/"))
        {
            ansText.setText("wrong!");
            return false;
        }
        return true;
    }
}