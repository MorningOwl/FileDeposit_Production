package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.security.DigestInputStream;
import java.security.MessageDigest;

public class LocalSchedule extends Schedule implements Serializable {
	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -3734287983789128612L;
	private String name;
	private List<File> fileList;
	private Date firstTime;
	private Long period;
	private boolean enabled;
	private UUID uniqueId;
	private File outputFolder;
	private int type;

	/**
	 * Instantiates the local schedule object.
	 * 
	 * @param name
	 * @param outputFolder
	 * @param firstTime
	 * @param period
	 * @param enabled
	 * @param type
	 */
	public LocalSchedule(String name, List<File> file, File outputFolder,
			Date firstTime, Long period, boolean enabled, UUID uniqueId,
			int type) {
		this.name = name;
		setOutputFolder(outputFolder);
		this.fileList = file;
		this.firstTime = firstTime;
		this.period = period;
		this.enabled = enabled;
		this.uniqueId = uniqueId;
		this.type = type;
	}

	/**
	 * Gets the output folder File.
	 * 
	 * @return
	 */
	public File getOutputFolder() {
		return outputFolder;
	}

	/**
	 * Sets a new output folder path.
	 * 
	 * @param outputFolder
	 */
	public void setOutputFolder(File outputFolder) {
		if (outputFolder.exists() && outputFolder.isDirectory()) {
			this.outputFolder = outputFolder;
		} else if (outputFolder.exists() && !outputFolder.isDirectory()) {
			outputFolder.mkdirs();
		} else if (!outputFolder.exists()) {
			outputFolder.mkdirs();
		}
	}

	@Override
	/**
	 * Sets a new name for the schedule.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the name of this schedule.
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Gets the list of files this schedules performs back up on.
	 */
	@Override
	public List<File> getFiles() {
		return fileList;
	}

	/**
	 * Sets a new list of files to perform back up on.
	 */
	@Override
	public void setFiles(List<File> files) {
		fileList.clear();
		fileList.addAll(files);
	}

	/**
	 * Adds a file to the list.
	 */
	@Override
	public void addFile(File file) {
		fileList.add(file);
	}

	/**
	 * Removes a file from the list.
	 */
	@Override
	public void removeFile(File file) {
		fileList.remove(file);
	}

	/**
	 * Sets the time that the schedule will run at first
	 */
	@Override
	public void setFirstTime(Date firstTime) {
		this.firstTime = firstTime;
	}

	/**
	 * Gets the time that the schedule will run at first
	 */
	@Override
	public Date getFirstTime() {
		return firstTime;
	}

	/**
	 * Sets a period on how long this schedule will run
	 */
	@Override
	public void setPeriod(long period) {
		this.period = period;
	}

	/**
	 * Gets a period on how long the period will run
	 */
	@Override
	public Long getPeriod() {
		return period;
	}

	/**
	 * Enables the schedule
	 */
	@Override
	public void enable() {
		this.enabled = true;
	}

	/**
	 * Disables the schedule
	 */
	@Override
	public void disable() {
		this.enabled = false;
	}

	/**
	 * Checks if the schedule is enabled
	 */
	@Override
	public boolean isEnabled() {
		return (enabled == true);
	}

	/**
	 * Gets the unique id of this schedule
	 */
	@Override
	public UUID getScheduleId() {
		return uniqueId;
	}

	/**
	 * Gets schedule type, either REPLACE or VERSION_CONTROL
	 */
	@Override
	public int getType() {
		return type;
	}

	/**
	 * Sets schedule type, either REPLACE or VERSION_CONTROL
	 */
	@Override
	public void setType(int type) {
		this.type = type;

	}

	/**
	 * the main function that runs when this schedule is active
	 */
	@Override
	public void run() {
		boolean done;
		for (File f : fileList) {
			if (type == REPLACE) {
				done = performBackUpReplace(f);
			} else if (type == VERSION_CONTROL) {
				done = performBackUpVersionControl(f);
			} else {
				done = false;
			}
		}
	}

	/**
	 * This performs a back up copy in locally in a given file. If the output
	 * file exists, it replaces the file.
	 * 
	 * @param inFile
	 * @return A message if back up was performed.
	 */
	public boolean performBackUpReplace(File inFile) {
		if (inFile.isFile()) {
			FileChannel input = null;
			FileChannel output = null;
			// get the full output name
			String outPath = "/" + inFile.getName();
			File outFile = new File(outputFolder.getAbsolutePath() + outPath);

			try {
				if (!outFile.getParentFile().exists()) {
					if (!outFile.getParentFile().mkdirs()) {
						return false;
					}
				}
				// create out file if it doesn't exist
				if (!outFile.exists()) {
					if (!outFile.createNewFile()) {
						//error creating new file, return false
						return false;
					}
				} else if (compareChecksums(inFile,outFile)) {
					//if both files are the same and not updated
					//do not back up
						return false;
				}
				
				//Begin back up
				input = new FileInputStream(inFile).getChannel();
				output = new FileOutputStream(outFile).getChannel();
				output.transferFrom(input, 0, input.size());
				input.close();
				output.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			
			return true;
		} else
			return false;
	}

	/**
	 * This performs a back up copy in locally in a given file. It creates a
	 * file with a timestap after.
	 * 
	 * @param inFile
	 * @return
	 */
	public boolean performBackUpVersionControl(File inFile) {
		if (inFile.isFile()) {
			FileChannel input = null;
			FileChannel output = null;
			// get inFile name excluding Extenstion
			String inFileName = inFile.getName();
			String inFileNameWithoutExtenstion = inFileName.substring(0,
					inFileName.indexOf('.'));
			int inFileVersionInteger = 0;
			String inFileVersionString = "[" + inFileVersionInteger + "]";
			String inFileExtension = inFile.getName().substring(
					inFileName.indexOf('.'), inFileName.length());

			File outFile = new File(outputFolder.getAbsolutePath() + "/"
					+ inFileNameWithoutExtenstion + inFileVersionString
					+ inFileExtension);
			try {
				if (!outFile.getParentFile().exists()) {
					if (!outFile.getParentFile().mkdirs()) {
						return false;
					}
				}
				// if the file with the current version exists,
				// it creates a new file with version + 1
				while (outFile.exists()) {
					inFileVersionInteger++;
					inFileVersionString = "_v" + inFileVersionInteger;
					outFile = new File(outputFolder.getAbsolutePath() + "/"
							+ inFileNameWithoutExtenstion + inFileVersionString
							+ inFileExtension);
				}
				if (!outFile.exists() && !outFile.createNewFile()) {
					return false;
				}
				input = new FileInputStream(inFile).getChannel();
				output = new FileOutputStream(outFile).getChannel();
				output.transferFrom(input, 0, input.size());
				input.close();
				output.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		} else
			return false;
	}

	/**
	 * Checks if there is enough space in the outputFolder to perform back up on
	 */
	public boolean enoughUsableSpace() {
		int inFileSize = 0;
		for (File f : fileList) {
			inFileSize += f.length();
		}

		return inFileSize < outputFolder.getUsableSpace();
	}

	/**
	 * <Local> ScheduleName
	 */
	public String toString() {
		return "<Local> " + getName();
	}

	private boolean compareChecksums(File file1, File file2) {
		boolean same = false;
		try {
			FileInputStream fis = new FileInputStream(file1);
			String md51 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
			fis.close();
			
			fis = new FileInputStream(file2);
			String md52 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
			fis.close();
			
			if(md51.equals(md52)){
				same = true;
			} else {
				same = false;
			}
			/* Read decorated stream (dis) to EOF as normal... */
		} catch (Exception e) {
			
		}

		return same;
	}

}
