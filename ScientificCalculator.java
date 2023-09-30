import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScientificCalculator extends JFrame {

    private JTextField display;
    private String currentInput = "";
    private double memory = 0.0;

    public ScientificCalculator() {
        setTitle("Scientific Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 5));

        String[] buttonLabels = {
            "7", "8", "9", "/", "sqrt",
            "4", "5", "6", "*", "pow",
            "1", "2", "3", "-", "log",
            "0", ".", "+/-", "+", "=",
            "sin", "cos", "tan", "C", "M"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            switch (command) {
                case "=":
                    evaluateExpression();
                    break;
                case "C":
                    clearDisplay();
                    break;
                case "M":
                    memory = Double.parseDouble(display.getText());
                    break;
                case "sqrt":
                    currentInput = String.valueOf(Math.sqrt(Double.parseDouble(currentInput)));
                    display.setText(currentInput);
                    break;
                case "pow":
                    currentInput += "^";
                    break;
                case "+/-":
                    if (!currentInput.isEmpty()) {
                        double value = Double.parseDouble(currentInput);
                        value = -value;
                        currentInput = String.valueOf(value);
                        display.setText(currentInput);
                    }
                    break;
                default:
                    currentInput += command;
                    display.setText(currentInput);
                    break;
            }
        }
    }

    private void evaluateExpression() {
        try {
            String expression = currentInput.replace("sin", "Math.sin")
                                           .replace("cos", "Math.cos")
                                           .replace("tan", "Math.tan")
                                           .replace("log", "Math.log");
            double result = evaluate(expression);
            currentInput = String.valueOf(result);
            display.setText(currentInput);
        } catch (Exception e) {
            currentInput = "Error";
            display.setText(currentInput);
        }
    }

    private double evaluate(String expression) {
        return (double) new Object() {
            double eval() {
                try {
                    return new Object() {
                        int pos = -1, ch;

                        void nextChar() {
                            ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
                        }

                        boolean eat(int charToEat) {
                            while (ch == ' ') nextChar();
                            if (ch == charToEat) {
                                nextChar();
                                return true;
                            }
                            return false;
                        }

                        double parse() {
                            nextChar();
                            double x = parseExpression();
                            if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                            return x;
                        }

                        
                        double parseExpression() {
                            double x = parseTerm();
                            for (; ; ) {
                                if (eat('+')) x += parseTerm();
                                else if (eat('-')) x -= parseTerm();
                                else return x;
                            }
                        }

                        double parseTerm() {
                            double x = parseFactor();
                            for (; ; ) {
                                if (eat('*')) x *= parseFactor(); 
                                else if (eat('/')) x /= parseFactor();
                                else return x;
                            }
                        }

                        double parseFactor() {
                            if (eat('+')) return parseFactor(); 
                            if (eat('-')) return -parseFactor(); 
                            double x;
                            int startPos = this.pos;
                            if (eat('(')) { 
                                x = parseExpression();
                                eat(')');
                            } else if ((ch >= '0' && ch <= '9') || ch == '.') { 
                                while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                                x = Double.parseDouble(expression.substring(startPos, this.pos));
                            } else {
                                throw new RuntimeException("Unexpected: " + (char) ch);
                            }

                            if (eat('^')) x = Math.pow(x, parseFactor()); 
                            else if (expression.startsWith("sqrt", startPos)) {
                                x = Math.sqrt(x);
                                pos += 3;
                            } else if (expression.startsWith("sin", startPos)) {
                                x = Math.sin(x);
                                pos += 2;
                            } else if (expression.startsWith("cos", startPos)) {
                                x = Math.cos(x);
                                pos += 2;
                            } else if (expression.startsWith("tan", startPos)) {
                                x = Math.tan(x);
                                pos += 2;
                            } else if (expression.startsWith("log", startPos)) {
                                x = Math.log(x);
                                pos += 2;
                            }
                            return x;
                        }
                    }.parse();
                } catch (Exception e) {
                    e.printStackTrace();
                    return Double.NaN;
                }
            }
        }.eval();
    }

    private void clearDisplay() {
        currentInput = "";
        display.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ScientificCalculator();
        });
    }
}
