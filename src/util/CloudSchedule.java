package util;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class CloudSchedule extends Schedule implements Serializable{

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = 3441018864112509968L;
	private String name;
	private List<File> fileList;
	private Date firstTime;
	private Long period;
	private boolean enabled;
	private UUID uniqueId;
	private int type;
	private String userId;

	public CloudSchedule(String name, List<File> file, Date firstTime,
			Long period, boolean enabled, UUID uniqueId, int type, String userId) {
		this.name = name;
		this.fileList = file;
		this.firstTime = firstTime;
		this.period = period;
		this.enabled = enabled;
		this.uniqueId = uniqueId;
		this.type = type;
		this.userId = userId;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public List<File> getFiles() {
		return this.fileList;
	}
	
	@Override
	public void setFiles(List<File> files){
		fileList.clear();
		fileList.addAll(files);
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
		return this.firstTime;
	}

	@Override
	public void setPeriod(long period) {
		this.period = period;
	}

	@Override
	public Long getPeriod() {
		return this.period;
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
	public int getType() {
		return type;
	}

	@Override
	public void setType(int type) {
		this.type = type;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	@Override
	public UUID getScheduleId() {
		return uniqueId;
	}
	
	@Override
	public void run() {
		//boolean done;
		for (File f : fileList) {
			if (type == REPLACE) {
				performBackUpReplace(f);
			} else if (type == VERSION_CONTROL) {
				performBackUpVersionControl(f);
			} else {
				//nuffin
			}
		}
	}

	/*
	 * Returns true or fals if there is enough usable space.
	 * @see util.Schedule#enoughUsableSpace()
	 */
	@Override
	public boolean enoughUsableSpace() {
		int inFileSize = 0;
		for(File f: fileList){
			inFileSize += f.length();
		}
		try {
			return inFileSize < (1e+9 - CloudConnector.getCloudSize());
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean performBackUpReplace(File inFile) {
		try {			
			CloudConnector.sendFileToCloud(inFile, userId + "/");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean performBackUpVersionControl(File inFile) {
		String inFileName = inFile.getName();
		String inFileNameWithoutExtension = inFileName.substring(0,
				inFileName.indexOf('.'));
		int inFileVersionInteger = 0;
		String inFileVersionString = "_v" + inFileVersionInteger;
		String inFileExtension = inFile.getName().substring(
				inFileName.indexOf('.'), inFileName.length());
		String outFilePath = userId + "/" + inFileNameWithoutExtension + inFileVersionString + inFileExtension;
		
		try {
		while(CloudConnector.fileExistsInCloud(outFilePath)){
			inFileNameWithoutExtension = inFileName.substring(0,
					inFileName.indexOf('.'));
			inFileVersionInteger = 0;
			inFileVersionString = "_v" + inFileVersionInteger;
			inFileExtension = inFile.getName().substring(
					inFileName.indexOf('.'), inFileName.length());
			outFilePath = userId + "/" + inFileNameWithoutExtension + inFileVersionString + inFileExtension;
		}		
			CloudConnector.sendFileToCloud(inFile, outFilePath);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * 
	 */
	public String toString(){
		return "<Cloud> " + getName();
	}

}
