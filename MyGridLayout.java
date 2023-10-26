import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import javax.swing.text.*;

public class MyGridLayout extends Generator{
    JFrame f;
    Generator g = new Generator();

    public char[][] textFieldText = new char[6][5];
    MyGridLayout() {
        System.out.println(g.word);
        f = new JFrame();
        JTextField[][] textFields = new JTextField[6][5];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                textFields[i][j] = new JTextField(1);
                textFields[i][j].setEnabled(false);
            }
        }
        textFields[0][0].setEnabled(true);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                //textFields[i][j] = new JTextField(1);
                f.add(textFields[i][j]);
                setSingleCharLimit(textFields[i][j]);
                addKeyListenerToMoveFocus(textFields, i, j);
            }
        }

        // setting grid layout of 6 rows and 5 columns
        f.setLayout(new GridLayout(6, 5));
        f.setSize(300, 300);
        f.setVisible(true);
    }

    private void setSingleCharLimit(JTextField textField) {
        Document doc = textField.getDocument();
        if (doc instanceof PlainDocument) {
            PlainDocument plainDoc = (PlainDocument) doc;
            plainDoc.setDocumentFilter(new DocumentFilter() {
                @Override
                public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                        throws BadLocationException {
                    int newLength = fb.getDocument().getLength() - length + text.length();
                    if (newLength == 1) {
                        super.replace(fb, offset, length, text, attrs);
                    } else if (newLength == 0) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                }
            });
        }
    }

    private void addKeyListenerToMoveFocus(JTextField[][] textFields, int row, int col) {
        JTextField textField = textFields[row][col];
        textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Empty implementation
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && row < 5) {
                    String str = new String(textFieldText[row]);
                    int [] v = new int[5];
                    if (is_valid(g.file_name, str))
                        v = color(g.word, str);
                    for (int j = 0; j < 5; j++) {
                        if (v[j] == 1)
                            textFields[row][j].setBackground(Color.YELLOW);
                        if (v[j] == 2)
                            textFields[row][j].setBackground(Color.GREEN);
                    }


                    textFields[row + 1][0].setEnabled(true);
                    if (col == textFields[row].length - 1) { // Compare with textFields[row].length directly
                        if (row < textFields.length - 1) {
                            textFields[row + 1][0].requestFocus();
                        }
                    }
                    //System.out.println(textFieldText[row]);
                }
                else if (col < 4){
                    textFields[row][col + 1].setEnabled(true);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String text = textField.getText();

                if (textField.getText().length() == 1) {
                    textFieldText[row][col] = text.charAt(0);
                    if (col < textFields[row].length - 1) {
                        textFields[row][col + 1].requestFocus();
                    }
                }
            }
        });
    }
}