package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JTabbedPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import util.*;
public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTabbedPane tabPane;
	private static ScheduleManager manager = new ScheduleManager();
	/**
	 * @wbp.nonvisual location=-38,339
	 */

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					manager = new ScheduleManager();
					Schedule sch1 = new LocalSchedule("Schedule 1", new File("C:\\Users\\Edward\\Documents\\"), new Date(), 10000l, true, Schedule.REPLACE);
					sch1.addFile(new File("C:\\Users\\Edward\\Desktop\\test\\test.txt"));
					manager.addSchedule(sch1);
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
					frame.validate();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(505,560);
		setResizable(false);
		buildTabPane();
		getContentPane().add(tabPane);
		
		JMenuBar menuBar = new JMenuBar();
		getContentPane().add(menuBar, BorderLayout.NORTH);
		
		JMenu mnUser = new JMenu("User");
		menuBar.add(mnUser);
		
		JMenuItem mntmLogOut = new JMenuItem("Log out...");
		mnUser.add(mntmLogOut);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
	}
	
	
	private void buildTabPane(){
		tabPane = new JTabbedPane();
		tabPane.setSize(getWidth(), getHeight());
		tabPane.add("Manage Panels", new ManageSchedulesPanel(getWidth(), getHeight(), manager));
		tabPane.add("Add a Schedule", new AddSchedulePanel(getWidth(), getHeight()));
		
	}

}
