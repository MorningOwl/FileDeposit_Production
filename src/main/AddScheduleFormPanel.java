package main;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DateEditor;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JCheckBox;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerDateModel;

import util.CloudConnector;
import util.CloudSchedule;
import util.LocalSchedule;
import util.Schedule;
import util.ScheduleManager;

import java.awt.SystemColor;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.swing.JRadioButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddScheduleFormPanel extends JPanel {
	private JLabel lblAddSchedule;
	private JTextField scheduleNameField;
	private JLabel lblOutputFolder;
	private JTextField outputFolderField;
	private DateEditor de_firstTimeSpinner;
	private JSpinner firstTimeSpinner;
	private List<File> currentFileList;
	private DefaultListModel<File> listModel;
	private JList<File> list;
	private JScrollPane listScrollPane;
	private JRadioButton rdbtnReplace;
	private JRadioButton rdbtnVersionControl ;
	private JCheckBox enableCheckBox;
	private JTextField frequencyHourField;
	private JTextField frequencyMinuteField;
	private JButton btnRemoveFile;
	private Schedule currentSchedule;
	private ScheduleManager scheduleManager;
	private JRadioButton localScheduleRadio;
	private JRadioButton cloudScheduleRadio;
	private JButton btnReset;
	private ButtonGroup scheduleGroup;
	private ButtonGroup typeGroup;
	
	
	//Globalize the edit fields
	
	
	/**
	 * Create the panel.
	 */
	public AddScheduleFormPanel(ScheduleManager scheduleManager) {
		this.scheduleManager = scheduleManager;
		currentFileList = new ArrayList<File>();
			
		setBackground(SystemColor.activeCaption);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lblAddSchedule = new JLabel("Add a Schedule");
		lblAddSchedule.setFont(new Font("Arial", Font.BOLD, 18));
		GridBagConstraints gbc_lblAddSchedule = new GridBagConstraints();
		gbc_lblAddSchedule.gridwidth = 2;
		gbc_lblAddSchedule.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddSchedule.gridx = 1;
		gbc_lblAddSchedule.gridy = 1;
		add(lblAddSchedule, gbc_lblAddSchedule);
		
		localScheduleRadio = new JRadioButton("Local Schedule");
		localScheduleRadio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(localScheduleRadio.isSelected()){
					lblOutputFolder.setVisible(true);
					outputFolderField.setVisible(true);
					validate();
				}
			}
		});		
		localScheduleRadio.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_localScheduleRadio = new GridBagConstraints();
		gbc_localScheduleRadio.insets = new Insets(0, 0, 5, 5);
		gbc_localScheduleRadio.gridx = 1;
		gbc_localScheduleRadio.gridy = 2;
		add(localScheduleRadio, gbc_localScheduleRadio);
		
		cloudScheduleRadio = new JRadioButton("Cloud Schedule");
		cloudScheduleRadio.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(cloudScheduleRadio.isSelected()){
					lblOutputFolder.setVisible(false);
					outputFolderField.setVisible(false);
					validate();
				}
			}
		});
		
		
		cloudScheduleRadio.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_cloudScheduleRadio = new GridBagConstraints();
		gbc_cloudScheduleRadio.insets = new Insets(0, 0, 5, 5);
		gbc_cloudScheduleRadio.gridx = 2;
		gbc_cloudScheduleRadio.gridy = 2;		
		add(cloudScheduleRadio, gbc_cloudScheduleRadio);
		
		scheduleGroup = new ButtonGroup();
		scheduleGroup.add(localScheduleRadio);
		scheduleGroup.add(cloudScheduleRadio);
		
		JLabel lblScheduleName = new JLabel("Schedule Name:");
		lblScheduleName.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblScheduleName = new GridBagConstraints();
		gbc_lblScheduleName.anchor = GridBagConstraints.EAST;
		gbc_lblScheduleName.insets = new Insets(0, 0, 5, 5);
		gbc_lblScheduleName.gridx = 1;
		gbc_lblScheduleName.gridy = 3;
		add(lblScheduleName, gbc_lblScheduleName);
		
		scheduleNameField = new JTextField();
		GridBagConstraints gbc_scheduleNameField = new GridBagConstraints();
		gbc_scheduleNameField.insets = new Insets(0, 0, 5, 5);
		gbc_scheduleNameField.fill = GridBagConstraints.HORIZONTAL;
		gbc_scheduleNameField.gridx = 2;
		gbc_scheduleNameField.gridy = 3;
		add(scheduleNameField, gbc_scheduleNameField);
		scheduleNameField.setColumns(10);
		
		JLabel lblFilesToBack = new JLabel("Files to Back Up");
		lblFilesToBack.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblFilesToBack = new GridBagConstraints();
		gbc_lblFilesToBack.gridwidth = 2;
		gbc_lblFilesToBack.insets = new Insets(0, 0, 5, 5);
		gbc_lblFilesToBack.gridx = 1;
		gbc_lblFilesToBack.gridy = 4;
		add(lblFilesToBack, gbc_lblFilesToBack);
		
		listModel = new DefaultListModel<File>();
		list = new JList<File>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setFixedCellWidth(235);
		listScrollPane = new JScrollPane(list);
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.gridwidth = 2;
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.gridx = 1;
		gbc_list.gridy = 5;
		add(listScrollPane, gbc_list);
		
		JButton btnAddMoreFiles = new JButton("add more files...");
		btnAddMoreFiles.addMouseListener(new MouseAdapter() {			
			JFileChooser fileChooser = new JFileChooser();
			@Override
			public void mouseClicked(MouseEvent arg0) {
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int result = fileChooser.showOpenDialog(getParent());
				if (result == JFileChooser.APPROVE_OPTION) {
					currentFileList.add(fileChooser.getSelectedFile());
					displayFileList();
				}
			}
		});
		btnAddMoreFiles.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_btnAddMoreFiles = new GridBagConstraints();
		gbc_btnAddMoreFiles.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAddMoreFiles.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddMoreFiles.gridx = 1;
		gbc_btnAddMoreFiles.gridy = 6;
		add(btnAddMoreFiles, gbc_btnAddMoreFiles);
		
		btnRemoveFile = new JButton("remove file");
		btnRemoveFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				File selectedFile = list.getSelectedValue();
            	if (selectedFile != null){
            		currentFileList.remove(selectedFile);
                	displayFileList();
            		
            	}	   
			}
		});
		btnRemoveFile.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_btnRemoveFile = new GridBagConstraints();
		gbc_btnRemoveFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRemoveFile.insets = new Insets(0, 0, 5, 5);
		gbc_btnRemoveFile.gridx = 2;
		gbc_btnRemoveFile.gridy = 6;
		add(btnRemoveFile, gbc_btnRemoveFile);
		
		JLabel lblStartTime = new JLabel("Start Time:");
		lblStartTime.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblStartTime = new GridBagConstraints();
		gbc_lblStartTime.anchor = GridBagConstraints.EAST;
		gbc_lblStartTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblStartTime.gridx = 1;
		gbc_lblStartTime.gridy = 7;
		add(lblStartTime, gbc_lblStartTime);
		
		firstTimeSpinner = new JSpinner( new SpinnerDateModel(new Date(), null, null, Calendar.AM_PM) );
		firstTimeSpinner.setFont(new Font("Arial", Font.PLAIN, 12));
		de_firstTimeSpinner = new DateEditor(firstTimeSpinner, "HH:mm");
		firstTimeSpinner.setEditor(de_firstTimeSpinner);
		firstTimeSpinner.setValue(new Date()); 
		GridBagConstraints gbc_firstTimeSpinner = new GridBagConstraints();
		gbc_firstTimeSpinner.anchor = GridBagConstraints.WEST;
		gbc_firstTimeSpinner.insets = new Insets(0, 0, 5, 5);
		gbc_firstTimeSpinner.gridx = 2;
		gbc_firstTimeSpinner.gridy = 7;
		add(firstTimeSpinner, gbc_firstTimeSpinner);
					
		JLabel lblFrequency = new JLabel("Frequency:");
		lblFrequency.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblFrequency = new GridBagConstraints();
		gbc_lblFrequency.anchor = GridBagConstraints.EAST;
		gbc_lblFrequency.insets = new Insets(0, 0, 5, 5);
		gbc_lblFrequency.gridx = 1;
		gbc_lblFrequency.gridy = 8;
		add(lblFrequency, gbc_lblFrequency);
		
		
		JPanel timePane = new JPanel();
		frequencyHourField = new JTextField(2);
		frequencyHourField.setHorizontalAlignment(JTextField.RIGHT);
		JLabel frequencyHourText = new JLabel("Hours");
		frequencyMinuteField = new JTextField(2);
		frequencyMinuteField.setHorizontalAlignment(JTextField.RIGHT);
		JLabel frequencyMinuteText = new JLabel("Minutes");
		timePane.add(frequencyHourField);
		timePane.add(frequencyHourText);
		timePane.add(frequencyMinuteField);
		timePane.add(frequencyMinuteText);
		timePane.setBackground(SystemColor.activeCaption);
		GridBagConstraints gbc_frequencyEditor = new GridBagConstraints();
		gbc_frequencyEditor.anchor = GridBagConstraints.WEST;
		gbc_frequencyEditor.insets = new Insets(0, 0, 5, 5);
		gbc_frequencyEditor.gridx = 2;
		gbc_frequencyEditor.gridy = 8;
		//add(frequencySpinner, gbc_frequencyEditor);
		add(timePane, gbc_frequencyEditor);
		
		lblOutputFolder = new JLabel("Output Folder: ");
		lblOutputFolder.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_lblOutputFolder = new GridBagConstraints();
		gbc_lblOutputFolder.anchor = GridBagConstraints.EAST;
		gbc_lblOutputFolder.insets = new Insets(0, 0, 5, 5);
		gbc_lblOutputFolder.gridx = 1;
		gbc_lblOutputFolder.gridy = 9;
		add(lblOutputFolder, gbc_lblOutputFolder);
		
		outputFolderField = new JTextField();
		outputFolderField.addMouseListener(new MouseAdapter() {
			JFileChooser fileChooser2 = new JFileChooser();
			@Override
			public void mouseClicked(MouseEvent arg0) {
				fileChooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int result = fileChooser2.showOpenDialog(getParent());
				if (result == JFileChooser.APPROVE_OPTION) {
					outputFolderField.setText(fileChooser2.getSelectedFile().toString());
					validate();
				}
			}
		});
		outputFolderField.setEditable(false);
		outputFolderField.setColumns(10);
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(0, 0, 5, 5);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 2;
		gbc_textField_4.gridy = 9;
		add(outputFolderField, gbc_textField_4);
		
		rdbtnReplace = new JRadioButton("Synchronize");
		rdbtnReplace.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_rdbtnReplace = new GridBagConstraints();
		gbc_rdbtnReplace.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnReplace.gridx = 1;
		gbc_rdbtnReplace.gridy = 10;
		add(rdbtnReplace, gbc_rdbtnReplace);
		
		rdbtnVersionControl = new JRadioButton("Version Control");
		rdbtnVersionControl.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_rdbtnVersionControl = new GridBagConstraints();
		gbc_rdbtnVersionControl.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnVersionControl.gridx = 2;
		gbc_rdbtnVersionControl.gridy = 10;
		add(rdbtnVersionControl, gbc_rdbtnVersionControl);
		
		typeGroup = new ButtonGroup();
		typeGroup.add(rdbtnReplace);
		typeGroup.add(rdbtnVersionControl);
		
		enableCheckBox = new JCheckBox("Enable this Schedule?");
		enableCheckBox.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_chckbxEnable = new GridBagConstraints();
		gbc_chckbxEnable.gridwidth = 2;
		gbc_chckbxEnable.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxEnable.gridx = 1;
		gbc_chckbxEnable.gridy = 11;
		add(enableCheckBox, gbc_chckbxEnable);
		
		JButton btnSave = new JButton("Save");
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			    int response = JOptionPane.showConfirmDialog(null, "You are about to save changes to this schedule. Are you sure?", "Confirm",
			        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			    if (response == JOptionPane.YES_OPTION){
			    	saveSchedule();
			    	reset();
			    }			
			}
		});
		btnSave.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnSave = new GridBagConstraints();
		gbc_btnSave.anchor = GridBagConstraints.EAST;
		gbc_btnSave.insets = new Insets(0, 0, 5, 5);
		gbc_btnSave.gridx = 1;
		gbc_btnSave.gridy = 12;
		add(btnSave, gbc_btnSave);
		
		btnReset = new JButton("Reset");
		btnReset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				reset();
			}
		});
		btnReset.setFont(new Font("Arial", Font.PLAIN, 14));
		GridBagConstraints gbc_btnReset = new GridBagConstraints();
		gbc_btnReset.anchor = GridBagConstraints.WEST;
		gbc_btnReset.insets = new Insets(0, 0, 5, 5);
		gbc_btnReset.gridx = 2;
		gbc_btnReset.gridy = 12;
		add(btnReset, gbc_btnReset);
		
		reset();
	}
	
	
	private void saveSchedule(){
		
		if(localScheduleRadio.isSelected()){
			//temporarily save all the data
			Schedule newSchedule;
			String newScheduleName;
			List<File> newFiles = new ArrayList<File>();
			Date newFirstTime;
			long newPeriod;
			File newOutputFolder;
			int newType;
			boolean newEnable;
			UUID newUUID;
			
			//get the new data
			newScheduleName = scheduleNameField.getText();
			newFiles.addAll(currentFileList);
			newOutputFolder = new File(outputFolderField.getText());
			newFirstTime = (Date)firstTimeSpinner.getValue();
			newPeriod = getMilliFromFrequencyFields();
			newEnable = enableCheckBox.isSelected();
			newUUID = UUID.randomUUID();
			if(rdbtnReplace.isSelected()){
				newType = Schedule.REPLACE;
			} else {
				newType = Schedule.VERSION_CONTROL;
			}
			newSchedule = new LocalSchedule(newScheduleName, currentFileList, newOutputFolder, newFirstTime,
					newPeriod, newEnable, newUUID, newType);
			scheduleManager.addSchedule(newSchedule);
			scheduleManager.saveToFile();
		} else if (cloudScheduleRadio.isSelected()){
			//temporarily save all the data
			Schedule newSchedule;
			String newScheduleName;
			List<File> newFiles  = new ArrayList<File>();;
			Date newFirstTime;
			long newPeriod;
			File newOutputFolder;
			int newType;
			boolean newEnable;
			UUID newUUID;
			
			//get the new data
			newScheduleName = scheduleNameField.getText();
			newFiles.addAll(currentFileList);
			newOutputFolder = new File(outputFolderField.getText());
			newFirstTime = (Date)firstTimeSpinner.getValue();
			newPeriod = getMilliFromFrequencyFields();
			newEnable = enableCheckBox.isSelected();
			newUUID = UUID.randomUUID();
			if(rdbtnReplace.isSelected()){
				newType = Schedule.REPLACE;
			} else {
				newType = Schedule.VERSION_CONTROL;
			}						
			newSchedule = new CloudSchedule(newScheduleName ,currentFileList, newFirstTime,
						newPeriod, newEnable, newUUID, newType, scheduleManager.getUserId());
			scheduleManager.addSchedule(newSchedule);
			scheduleManager.saveToFile();
		} else {
			//Do nothing. Class was not selected.
		}
		
		validate();
	}
	
	/**
	 * resets the fields
	 */
	private void reset(){
		scheduleGroup.clearSelection();
		scheduleNameField.setText("");
		outputFolderField.setText("");
		firstTimeSpinner.setValue(new Date());
		frequencyHourField.setText(0 + "");
		frequencyMinuteField.setText(0 + "");
		typeGroup.clearSelection();
		enableCheckBox.setSelected(false);	
		//currentFileList.clear();
		displayFileList();
	}
	
	/**
	 * Displays data on the frequency fields
	 * @param ms
	 */
	private void displayFrequencyFields(long ms){
		int minutes = (int) ((ms / (1000*60)) % 60);
		int hours = (int) (ms / (1000*60*60));
		frequencyHourField.setText(hours + "");
		frequencyMinuteField.setText(minutes + "");
	}
	
	private long getMilliFromFrequencyFields(){
		int minutes = Integer.parseInt(frequencyMinuteField.getText());
		int hours = Integer.parseInt(frequencyHourField.getText());
		return (long)(hours * (1000*60*60)) + (minutes * (1000*60));
	}
	
	/**
	 * Displays the elements in JList.
	 */
	public void displayFileList(){
		listModel.clear();
		for(File f: currentFileList){
			listModel.addElement(f);
		}
		list.setModel(listModel);
		validate();
	}

}
