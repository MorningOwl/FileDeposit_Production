package main;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JSpinner;
import javax.swing.JSpinner.DateEditor;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JCheckBox;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.SpinnerDateModel;

import util.Schedule;

import java.awt.SystemColor;
import java.util.Date;
import java.util.Calendar;

public class ScheduleFormPanel extends JPanel {
	private JTextField scheduleNameField;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_2;
	private DateEditor de_firstTimeSpinner;
	private DateEditor frequencyEditor;
	private JSpinner firstTimeSpinner;
	private JSpinner frequencySpinner;
	private Schedule schedule;
	
	//Globalize the edit fields
	
	
	/**
	 * Create the panel.
	 */
	public ScheduleFormPanel() {
		setBackground(SystemColor.activeCaption);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblEditSchedule = new JLabel("Editing Schedule");
		lblEditSchedule.setFont(new Font("Arial", Font.BOLD, 18));
		GridBagConstraints gbc_lblEditSchedule = new GridBagConstraints();
		gbc_lblEditSchedule.gridwidth = 2;
		gbc_lblEditSchedule.anchor = GridBagConstraints.EAST;
		gbc_lblEditSchedule.insets = new Insets(0, 0, 5, 5);
		gbc_lblEditSchedule.gridx = 1;
		gbc_lblEditSchedule.gridy = 1;
		add(lblEditSchedule, gbc_lblEditSchedule);
		
		JLabel lblScheduleName = new JLabel("Schedule Name:");
		lblScheduleName.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblScheduleName = new GridBagConstraints();
		gbc_lblScheduleName.anchor = GridBagConstraints.EAST;
		gbc_lblScheduleName.insets = new Insets(0, 0, 5, 5);
		gbc_lblScheduleName.gridx = 1;
		gbc_lblScheduleName.gridy = 2;
		add(lblScheduleName, gbc_lblScheduleName);
		
		scheduleNameField = new JTextField();
		GridBagConstraints gbc_scheduleNameField = new GridBagConstraints();
		gbc_scheduleNameField.insets = new Insets(0, 0, 5, 5);
		gbc_scheduleNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_scheduleNameField.gridx = 2;
		gbc_scheduleNameField.gridy = 2;
		add(scheduleNameField, gbc_scheduleNameField);
		scheduleNameField.setColumns(10);
		
		JLabel lblFilesToBack = new JLabel("Files to Back Up");
		lblFilesToBack.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblFilesToBack = new GridBagConstraints();
		gbc_lblFilesToBack.gridwidth = 2;
		gbc_lblFilesToBack.insets = new Insets(0, 0, 5, 5);
		gbc_lblFilesToBack.gridx = 1;
		gbc_lblFilesToBack.gridy = 3;
		add(lblFilesToBack, gbc_lblFilesToBack);
		
		JList list = new JList();
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 2;
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 1;
		gbc_list.gridy = 4;
		add(list, gbc_list);
		
		JButton btnAddMoreFiles = new JButton("add more files...");
		btnAddMoreFiles.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnAddMoreFiles = new GridBagConstraints();
		gbc_btnAddMoreFiles.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddMoreFiles.gridx = 2;
		gbc_btnAddMoreFiles.gridy = 5;
		add(btnAddMoreFiles, gbc_btnAddMoreFiles);
		
		JLabel lblStartTime = new JLabel("Start Time:");
		lblStartTime.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblStartTime = new GridBagConstraints();
		gbc_lblStartTime.anchor = GridBagConstraints.EAST;
		gbc_lblStartTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblStartTime.gridx = 1;
		gbc_lblStartTime.gridy = 6;
		add(lblStartTime, gbc_lblStartTime);
		
		firstTimeSpinner = new JSpinner( new SpinnerDateModel(new Date(1444706722397L), null, null, Calendar.AM_PM) );
		firstTimeSpinner.setFont(new Font("Arial", Font.PLAIN, 11));
		de_firstTimeSpinner = new DateEditor(firstTimeSpinner, "HH:mm:ss");
		firstTimeSpinner.setEditor(de_firstTimeSpinner);
		firstTimeSpinner.setValue(new Date()); 
		GridBagConstraints gbc_firstTimeSpinner = new GridBagConstraints();
		gbc_firstTimeSpinner.insets = new Insets(0, 0, 5, 5);
		gbc_firstTimeSpinner.fill = GridBagConstraints.HORIZONTAL;
		gbc_firstTimeSpinner.gridx = 2;
		gbc_firstTimeSpinner.gridy = 6;
		add(firstTimeSpinner, gbc_firstTimeSpinner);
					
		JLabel lblFrequency = new JLabel("Frequency:");
		lblFrequency.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblFrequency = new GridBagConstraints();
		gbc_lblFrequency.anchor = GridBagConstraints.EAST;
		gbc_lblFrequency.insets = new Insets(0, 0, 5, 5);
		gbc_lblFrequency.gridx = 1;
		gbc_lblFrequency.gridy = 7;
		add(lblFrequency, gbc_lblFrequency);
		
		frequencySpinner = new JSpinner( new SpinnerDateModel(new Date(1444706685651L), null, null, Calendar.MINUTE) );
		frequencySpinner.setFont(new Font("Arial", Font.PLAIN, 11));
		frequencyEditor =  new DateEditor(frequencySpinner, "HH:mm:ss");;
		frequencySpinner.setEditor(frequencyEditor);
		GridBagConstraints gbc_frequencyEditor = new GridBagConstraints();
		gbc_frequencyEditor.insets = new Insets(0, 0, 5, 5);
		gbc_frequencyEditor.fill = GridBagConstraints.HORIZONTAL;
		gbc_frequencyEditor.gridx = 2;
		gbc_frequencyEditor.gridy = 7;
		add(frequencySpinner, gbc_frequencyEditor);
		
		JLabel lblOutputFolder = new JLabel("Output Folder: ");
		lblOutputFolder.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblOutputFolder = new GridBagConstraints();
		gbc_lblOutputFolder.anchor = GridBagConstraints.EAST;
		gbc_lblOutputFolder.insets = new Insets(0, 0, 5, 5);
		gbc_lblOutputFolder.gridx = 1;
		gbc_lblOutputFolder.gridy = 8;
		add(lblOutputFolder, gbc_lblOutputFolder);
		
		textField_4 = new JTextField();
		textField_4.setEditable(false);
		textField_4.setColumns(10);
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(0, 0, 5, 5);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 2;
		gbc_textField_4.gridy = 8;
		add(textField_4, gbc_textField_4);
		
		JCheckBox chckbxReplace = new JCheckBox("Replace");
		chckbxReplace.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_chckbxReplace = new GridBagConstraints();
		gbc_chckbxReplace.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxReplace.gridx = 1;
		gbc_chckbxReplace.gridy = 9;
		add(chckbxReplace, gbc_chckbxReplace);
		
		JCheckBox chckbxVersionControl = new JCheckBox("Version Control");
		chckbxVersionControl.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_chckbxVersionControl = new GridBagConstraints();
		gbc_chckbxVersionControl.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxVersionControl.gridx = 2;
		gbc_chckbxVersionControl.gridy = 9;
		add(chckbxVersionControl, gbc_chckbxVersionControl);
		
		JCheckBox chckbxEnable = new JCheckBox("Enable this Schedule?");
		chckbxEnable.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_chckbxEnable = new GridBagConstraints();
		gbc_chckbxEnable.gridwidth = 2;
		gbc_chckbxEnable.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxEnable.gridx = 1;
		gbc_chckbxEnable.gridy = 10;
		add(chckbxEnable, gbc_chckbxEnable);
		
		JButton btnSave = new JButton("Save");
		btnSave.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.gridwidth = 2;
		gbc_btnSave.insets = new Insets(0, 0, 0, 5);
		gbc_btnSave.gridx = 1;
		gbc_btnSave.gridy = 11;
		add(btnSave, gbc_btnSave);
	}
	
	public void setSchedule(Schedule s){
		scheduleNameField.setText(s.getName());
		firstTimeSpinner.setValue(s.getFirstTime());
		frequencySpinner.setValue(new Date(s.getPeriod()));
	}

}
