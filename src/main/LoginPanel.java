package main;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import auth.UserAuthentication;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginPanel extends JPanel {
	private JTextField usernameTextField;
	private JPasswordField passwordField;
	/**
	 * Create the panel.
	 */
	public LoginPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblUsername = new JLabel("Username:");
		GridBagConstraints gbc_lblUsername = new GridBagConstraints();
		gbc_lblUsername.anchor = GridBagConstraints.EAST;
		gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
		gbc_lblUsername.gridx = 4;
		gbc_lblUsername.gridy = 3;
		add(lblUsername, gbc_lblUsername);
		
		usernameTextField = new JTextField();
		GridBagConstraints gbc_usernameTextField = new GridBagConstraints();
		gbc_usernameTextField.anchor = GridBagConstraints.WEST;
		gbc_usernameTextField.insets = new Insets(0, 0, 5, 0);
		gbc_usernameTextField.gridx = 5;
		gbc_usernameTextField.gridy = 3;
		add(usernameTextField, gbc_usernameTextField);
		usernameTextField.setColumns(16);
		
		JLabel lblPassword = new JLabel("Password:");
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblPassword.gridx = 4;
		gbc_lblPassword.gridy = 4;
		add(lblPassword, gbc_lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setColumns(16);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.anchor = GridBagConstraints.WEST;
		gbc_passwordField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField.gridx = 5;
		gbc_passwordField.gridy = 4;
		add(passwordField, gbc_passwordField);
		

		
		JButton newUserButton = new JButton("Create New Account");
		newUserButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				CreateUserPanel createUserPanel = new CreateUserPanel();
				int result;
				result = JOptionPane.showConfirmDialog(null, createUserPanel,
						"Create Account", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION){
					createUserPanel.createNewUser();
				}
			}
		});
		GridBagConstraints gbc_newUserButton = new GridBagConstraints();
		gbc_newUserButton.anchor = GridBagConstraints.WEST;
		gbc_newUserButton.gridx = 5;
		gbc_newUserButton.gridy = 6;
		add(newUserButton, gbc_newUserButton);
	}
	
	public String authenticateUser(){
		char[] password = passwordField.getPassword();
		String covertedPass = "";
		for(int i = 0; i < password.length; i++){
			covertedPass += password[i];
		}
		
		if(UserAuthentication.localAuthenticate(usernameTextField.getText(), covertedPass)){
			return usernameTextField.getText();
		} else {
			JOptionPane.showMessageDialog(null, "Wrong password!", "You gave the wrong username/password.", JOptionPane.ERROR_MESSAGE);		
			return null;
		}		
	}

}
