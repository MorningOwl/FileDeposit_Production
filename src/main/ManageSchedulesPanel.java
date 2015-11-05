package main;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;

import util.Schedule;
import util.ScheduleManager;

public class ManageSchedulesPanel extends JPanel {

	private DefaultListModel<Schedule> listModel;
	private JList<Schedule> scheduleManageJList;	
	private JScrollPane scheduleManagerScrollPane;
	private JButton removeButton, editButton;
	private JPanel buttonPanel;
	private EditScheduleFormPanel scheduleEditPanel;
	private ScheduleManager scheduleManager;
	/**
	 * Create the panel.
	 */
	public ManageSchedulesPanel(int width, int height, ScheduleManager manager) {
		setSize(width, height);
		setBackground(SystemColor.activeCaption);
		this.scheduleManager = manager;
		//Set Layout
		setLayout(new BorderLayout());
		
		/**
		 * ZA LEFT SIDE OV ZA LAYOUT
		 */		
		//Create the list display.
		listModel = new DefaultListModel<Schedule>();
		scheduleManageJList = new JList<Schedule>(listModel);
		scheduleManageJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scheduleManageJList.setFixedCellWidth(235);
		displayScheduleList();
		//Create Scroll pane and add to panel
		scheduleManagerScrollPane = new JScrollPane(scheduleManageJList);
		add(scheduleManagerScrollPane, BorderLayout.LINE_START);
		/**
		 * ZA RIGHT SIDE OV ZA LAYOUT
		 */	
		//showScheduleFormPanel();
		
		/**
		 * ZA BOTTOM SIDE OV ZA LAYOUT
		 */	
		//Create le buttons
		buttonPanel = new JPanel();
		removeButton = new JButton("Remove Schedule");
		removeButton.addActionListener(new ActionListener() {			  
	            public void actionPerformed(ActionEvent e)
	            {
	            	Schedule selectedSchedule = scheduleManageJList.getSelectedValue();
	            	int response = JOptionPane.showConfirmDialog(null, "You are about delete this schedule. Are you sure?", "Confirm",
	    			    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	    			    if (response == JOptionPane.YES_OPTION){
	    			    	scheduleManager.removeSchedule(selectedSchedule);
	    			    	displayScheduleList();
	    			    	scheduleManager.saveToFile();
	    			    }          		    			    
	            }
	        });   
		  
		editButton = new JButton("Edit Schedule");
		editButton.addActionListener(new ActionListener() {			  
            public void actionPerformed(ActionEvent e)
            {
            	Schedule selectedSchedule = scheduleManageJList.getSelectedValue();
            	if (selectedSchedule != null){
            		showScheduleFormPanel();
                	scheduleEditPanel.displaySchedule(selectedSchedule);
                	validate();
            	}	            	
            }
        });  
		new JButton("Save");
		buttonPanel.add(removeButton);
		buttonPanel.add(editButton);		
		add(buttonPanel, BorderLayout.PAGE_END);
		
	}
	
	
	public void showScheduleFormPanel(){
		scheduleEditPanel = new EditScheduleFormPanel(scheduleManager);
		add(scheduleEditPanel, BorderLayout.LINE_END);
	}
	
	
	/**
	 * Displays the elements in JList.
	 */
	public void displayScheduleList(){
		listModel.clear();
		for(Schedule s: scheduleManager.getScheduleList()){
			listModel.addElement(s);
		}
		scheduleManageJList.setModel(listModel); 
		validate();
	}

}
