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

public class CreateUserPanel extends JPanel {
	private JTextField usernameTextField;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	private JLabel lblConfirmPassword;
	/**
	 * Create the panel.
	 */
	public CreateUserPanel() {
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
		
		lblConfirmPassword = new JLabel("Confirm Password:");
		GridBagConstraints gbc_lblConfirmPassword = new GridBagConstraints();
		gbc_lblConfirmPassword.anchor = GridBagConstraints.EAST;
		gbc_lblConfirmPassword.insets = new Insets(0, 0, 5, 5);
		gbc_lblConfirmPassword.gridx = 4;
		gbc_lblConfirmPassword.gridy = 5;
		add(lblConfirmPassword, gbc_lblConfirmPassword);
		
		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setColumns(16);
		GridBagConstraints gbc_confirmPasswordField = new GridBagConstraints();
		gbc_confirmPasswordField.insets = new Insets(0, 0, 5, 0);
		gbc_confirmPasswordField.anchor = GridBagConstraints.WEST;
		gbc_confirmPasswordField.gridx = 5;
		gbc_confirmPasswordField.gridy = 5;
		add(confirmPasswordField, gbc_confirmPasswordField);
		
	}
	
	public boolean createNewUser(){
		if(UserAuthentication.nameExists(usernameTextField.getText())){
			JOptionPane.showMessageDialog(null, "Username Taken", "Error", JOptionPane.ERROR_MESSAGE);		
			return false;
		}			
		else if(!comparePassword(passwordField.getPassword(), confirmPasswordField.getPassword())){
			JOptionPane.showMessageDialog(null, "Passwords don't match.", "Error", JOptionPane.ERROR_MESSAGE);	
			return false;
		}			
		else{
			UserAuthentication.storeNewUser(usernameTextField.getText(), getPasswordString(passwordField.getPassword()));
			JOptionPane.showMessageDialog(null, "New account has been created.", "Account Created", JOptionPane.ERROR_MESSAGE);		
			return true;
		}
			
	}
	
	private boolean comparePassword(char[] pass1, char[] pass2){
		if(pass1.length == 0 || pass2.length == 0)
			return false;
		if(pass1.length != pass2.length)
			return false;
		if(pass1 == null || pass2 == null)
			return false;
		for(int i = 0; i < pass1.length; i++){
			if (pass1[i] != pass2[i])
				return false;
		}
		return true;
	}
	
	private String getPasswordString(char[] pass){
		String convertedPass = "";
		for(int i = 0; i < pass.length; i++){
			convertedPass += pass[i];
		}
		return convertedPass;
	}
}