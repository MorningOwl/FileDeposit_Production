package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.FlowLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.jets3t.service.multi.DownloadPackage;
import org.jets3t.service.multi.SimpleThreadedStorageService;

import util.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTabbedPane tabPane;
	private static ManageSchedulesPanel manageSchedulesPanel;
	private static AddScheduleFormPanel addScheduleFormPanel;
	private static CloudPanel cloudPanel;
	private static ScheduleManager manager;
	private static String userid;

	/**
	 * @wbp.nonvisual location=-38,339
	 */

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				LoginPanel loginPanel = new LoginPanel();
				int result;

				while (userid == null) {
					result = JOptionPane.showConfirmDialog(null, loginPanel,
							"Login", JOptionPane.OK_CANCEL_OPTION);
					if (result == JOptionPane.CANCEL_OPTION || result == -1) {
						System.exit(0);
					} else {
						userid = loginPanel.authenticateUser();
					}
				}
				
				if(userid == null){
					System.exit(0);
				}
				try{
				File managerFile = new File(userid + "_" + "manager.ser");
				if(!managerFile.exists()){
					managerFile.createNewFile();
					manager = new ScheduleManager(userid);
					manager.saveToFile();
				} else {
					FileInputStream fin = new FileInputStream(userid + "_" + "manager.ser");
					ObjectInputStream ois = new ObjectInputStream(fin);
					manager = (ScheduleManager) ois.readObject();
					manager.rescheduleAllSchedules();
					ois.close();
				} 

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
		setSize(550, 560);
		setResizable(false);
		buildTabPane();
		getContentPane().add(tabPane);

		JMenuBar menuBar = new JMenuBar();
		getContentPane().add(menuBar, BorderLayout.NORTH);

		JMenu mnUser = new JMenu("User");
		menuBar.add(mnUser);

		JMenuItem mntmLogOut = new JMenuItem("Log out...");
		mntmLogOut.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.exit(0);
			}
		});
		mnUser.add(mntmLogOut);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
	}

	private void buildTabPane() {
		tabPane = new JTabbedPane();
		tabPane.setSize(getWidth(), getHeight());
		manageSchedulesPanel = new ManageSchedulesPanel(getWidth(),
				getHeight(), manager);
		tabPane.add("Manage Schedules", manageSchedulesPanel);
		addScheduleFormPanel = new AddScheduleFormPanel(manager);
		tabPane.add("Add a Schedule", addScheduleFormPanel);
		cloudPanel = new CloudPanel(userid);
		tabPane.add("View Google Cloud", cloudPanel);
		tabPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// update the list display on manage schedule
				manageSchedulesPanel.displayScheduleList();
			}
		});
	}

}
