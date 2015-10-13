package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class LocalSchedule extends Schedule {
	private String name;
	private List<File> fileList;
	private Date firstTime;
	private Long period;
	private boolean enabled;
	private UUID uniqueId;
	private File outputFolder;
	private int type;

	/**
	 * 
	 * @param name
	 * @param outputFolder
	 * @param firstTime
	 * @param period
	 * @param enabled
	 * @param type
	 */
	public LocalSchedule(String name, File outputFolder, Date firstTime,
			Long period, boolean enabled, int type) {
		this.name = name;
		setOutputFolder(outputFolder);
		this.fileList = new LinkedList<File>();
		this.firstTime = firstTime;
		this.period = period;
		this.enabled = enabled;
		this.uniqueId = UUID.randomUUID();
		this.type = type;
	}

	public File getOutputFolder() {
		return outputFolder;
	}

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
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<File> getFiles() {
		return fileList;
	}

	@Override
	public File getFile(int index) {
		return fileList.get(index);
	}

	@Override
	public void addFile(File file) {
		fileList.add(file);
	}

	@Override
	public void removeFile(File file) {
		fileList.remove(file);
	}

	@Override
	public void setFirstTime(Date firstTime) {
		this.firstTime = firstTime;
	}

	@Override
	public Date getFirstTime() {
		return firstTime;
	}

	@Override
	public void setPeriod(long period) {
		this.period = period;
	}

	@Override
	public Long getPeriod() {
		return period;
	}

	@Override
	public void enable() {
		this.enabled = true;
	}

	@Override
	public void disable() {
		this.enabled = false;
	}

	@Override
	public boolean isEnabled() {
		return (enabled == true);
	}

	@Override
	public UUID getScheduleId() {
		return uniqueId;
	}

	@Override
	public int getType() {
		return type;
	}

	@Override
	public void setType(int type) {
		this.type = type;

	}

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
				// create out file if it doesn't exist, return false if it
				// errors
				if (!outFile.exists()) {
					if (!outFile.createNewFile()) {
						return false;
					}
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
			String inFileVersionString = "_v" + inFileVersionInteger;
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
	
	public boolean enoughUsableSpace(){
		int inFileSize = 0;
		for(File f: fileList){
			inFileSize += f.length();
		}
		
		return inFileSize < outputFolder.getUsableSpace();
	}
	
	/**
	 * 
	 */
	public String toString(){
		return getName();
	}
	 

}
