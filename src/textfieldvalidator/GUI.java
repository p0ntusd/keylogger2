/**
 * The GUI for the text validator program.
 * Contains three auto-validated text fields
 * and a button to display errors.
 *
 * @author Pontus Dahlkvist
 * @date 04/01 -25
 */

package textfieldvalidator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * ------------------------- GUI --------------------------
 */
public class GUI {
    private ArrayList<ValidatedTextField> validatedTextFields = new ArrayList<>();
    private JTextArea errorText;
    private ArrayList<Character> keyPressList = new ArrayList<>();
    JTextArea savedKeys;


    /**
     * Constructor.
     */
    void buildGUI() {
        JFrame frame = new JFrame("Validator");
        frame.setSize(500, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        ValidatedTextField name = new ValidatedTextField(new BasicValidator());
        ValidatedTextField email = new ValidatedTextField(new EmailValidator(new BasicValidator()));
        ValidatedTextField password = new ValidatedTextField(new PasswordValidator(new BasicValidator()));

        JLabel nameText = new JLabel("Username: ");
        JLabel emailText = new JLabel("Email adress: ");
        JLabel passwordText = new JLabel("Password: ");

        validatedTextFields.add(name);
        validatedTextFields.add(email);
        validatedTextFields.add(password);

        addKeyListenerToField(name);
        addKeyListenerToField(email);
        addKeyListenerToField(password);

        errorText = new JTextArea(3, 30);
        errorText.setEditable(false);
        errorText.setLineWrap(true);
        errorText.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(errorText);

        panel.add(nameText);
        panel.add(name);
        panel.add(emailText);
        panel.add(email);
        panel.add(passwordText);
        panel.add(password);

        JButton validateButton = new JButton("Log in");
        validateButton.addActionListener(e -> getErrors());
        panel.add(validateButton);

        panel.add(scrollPane);

        JButton showSavedKeys = new JButton("Show logged keys");
        showSavedKeys.addActionListener(e -> printSavedKeys());
        panel.add(showSavedKeys);

        savedKeys = new JTextArea(10, 50);
        savedKeys.setEditable(false);
        savedKeys.setLineWrap(true);
        JScrollPane savedKeysPane = new JScrollPane(savedKeys);
        savedKeysPane.setPreferredSize(new Dimension(10, 50));
        panel.add(savedKeysPane);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void addKeyListenerToField(ValidatedTextField field) {
        field.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                keyPressList.add(e.getKeyChar());
            }
        });
    }

    /**
     * Will display all current errors from the
     * three text fields in a separate error text area.
     * It does so when the user pushes the button to do it.
     */
    private void getErrors() {
        ArrayList<String> errors = new ArrayList<>();

        for (ValidatedTextField validatedTextField : validatedTextFields) {
            if (!validatedTextField.isValid()) {
                errors.add(validatedTextField.getError());
            }
        }
        if(errors.isEmpty()) {
            errorText.setText("Alla fält godkända!");
        } else {
            errorText.setText(String.join("\n", errors));
        }
    }

    private void printSavedKeys() {
        System.out.println(savedKeys.getText());
        for(Character key : keyPressList) {
            savedKeys.append(key.toString());
        }
    }
}
