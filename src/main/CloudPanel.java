package main;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTree;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import java.awt.BorderLayout;
import java.awt.SystemColor;
import java.io.File;
import java.util.List;

import javax.swing.JScrollPane;

import util.CloudConnector;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CloudPanel extends JPanel {

	private List<File> currentFileList;
	private DefaultListModel<File> listModel;
	private JList<File> cloudFileJList;
	private String userid;
	private JTextField textField;
	/**
	 * Create the panel.
	 */
	public CloudPanel(final String userid) {
		this.userid = userid;
		setLayout(new BorderLayout(0, 0));
	
		try {
			listModel = new DefaultListModel<File>();
			cloudFileJList = new JList<File>(listModel);
			cloudFileJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			cloudFileJList.setFixedCellWidth(235);
			displayCloudFiles();
			JScrollPane scrollPane = new JScrollPane(cloudFileJList);
			add(scrollPane, BorderLayout.NORTH);
			
			final JPanel panel = new JPanel();
			add(panel, BorderLayout.CENTER);
			panel.setBackground(SystemColor.activeCaption);
			JButton btnDownloadAll = new JButton("Dump");
			
			btnDownloadAll.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					try {
						CloudConnector.downloadFileList(userid, textField.getText()+ "\\");
					} catch (Exception e) {
						JOptionPane.showMessageDialog(panel, "Eggs are not supposed to be green.");
					}
				}
			});
			btnDownloadAll.setFont(new Font("Arial", Font.PLAIN, 12));
			panel.add(btnDownloadAll);
			
			textField = new JTextField();
			textField.setEditable(false);
			textField.addMouseListener(new MouseAdapter() {
				JFileChooser fileChooser2 = new JFileChooser();
				@Override
				public void mouseClicked(MouseEvent arg0) {
					fileChooser2.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int result = fileChooser2.showOpenDialog(getParent());
					if (result == JFileChooser.APPROVE_OPTION) {
						textField.setText(fileChooser2.getSelectedFile().toString());
						validate();
					}
				}
			});
			textField.setFont(new Font("Arial", Font.PLAIN, 12));
			panel.add(textField);
			textField.setColumns(20);			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	/**
	 * Displays the elements in JList.
	 */
	private void displayCloudFiles(){
		listModel.clear();
		try {
			for(File f: CloudConnector.getCloudFileNamesByUserName(userid)){
				listModel.addElement(f);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		cloudFileJList.setModel(listModel);
		validate();
	}
	
	

}
