package util;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.jets3t.service.Constants;
import org.jets3t.service.ServiceException;
import org.jets3t.service.acl.Permission;
import org.jets3t.service.acl.gs.AllUsersGrantee;
import org.jets3t.service.acl.gs.GSAccessControlList;
import org.jets3t.service.acl.gs.GroupByDomainGrantee;
import org.jets3t.service.acl.gs.UserByEmailAddressGrantee;
import org.jets3t.service.acl.gs.UserByIdGrantee;
import org.jets3t.service.impl.rest.httpclient.GoogleStorageService;
import org.jets3t.service.model.GSBucket;
import org.jets3t.service.model.GSObject;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.multi.DownloadPackage;
import org.jets3t.service.multi.SimpleThreadedStorageService;
import org.jets3t.service.security.GSCredentials;
import org.jets3t.service.utils.ServiceUtils;

/**
 * Connects to Google Cloud.
 * @author Edward
 *
 */
public final class CloudConnector{	
	/**
	 * 
	 */
	//private static final long serialVersionUID = -3290991630733464172L;
	private static final String ACCESS_KEY = "GOOGD72WUOK3WMRTDZW2";
	private static final String SECRET_KEY = "rzy903m2rJfy5r4dDGannvH7QB5Vddo9Y0F/lga+";
	private static final String BUCKET = "filedepositcloud-1093.appspot.com";
	private static GSCredentials gsCredentials;
	private static GoogleStorageService gsService;
	private static List<File> cloudList =  new ArrayList<File>();;
	
	
	private static void connect() throws Exception{
		gsCredentials = new GSCredentials(ACCESS_KEY, SECRET_KEY);
    	gsService = new GoogleStorageService(gsCredentials);
	}
	
	public static void sendFileToCloud(File inFile, String outPath) throws Exception{
		connect();
		GSObject fileObject = new GSObject(inFile);
		fileObject.setName(outPath + inFile.getName());
		fileObject.setName(fileObject.getName().replace("\\", "/"));
		gsService.putObject(BUCKET, fileObject);
	}
	
	public static boolean fileExistsInCloud (String inFilePath) throws Exception{
		//updateCloudList();
		connect();
		return gsService.getBucket(BUCKET).containsMetadata(inFilePath);
		//return cloudList.contains(inFile);
	}
	
	public static int getCloudSize() throws Exception{
		connect();
		int cloudSize = 0;
		GSObject[] objects = gsService.listObjects(BUCKET);
		for(int i = 0; i < objects.length; i++){
			cloudSize += objects[i].getContentLength();
		}
		return cloudSize;
	}
	
	private static void updateCloudList() throws Exception{
		connect();
		GSObject[] objects = gsService.listObjects(BUCKET);
		cloudList.clear();
		for(int i = 0; i < objects.length; i++){
			cloudList.add(new File(objects[i].getKey()));
		}
	}
	
	public static List<File> getCloudFileNamesByUserName(String userid) throws Exception{
		connect();
		List<File> fileList = new LinkedList<File>();
		updateCloudList();
		int firsSlashDelimiter = 0;
		String pathName = "";
		for(File f: cloudList){
			fileList.add(f);
		}
		return fileList;
	}
	
	public static void downloadFileList(String userid, String output) throws Exception{
		connect(); 
		SimpleThreadedStorageService simpleMulti = new SimpleThreadedStorageService(gsService);		
    	GSObject[] objects = gsService.listObjects(BUCKET);
    	DownloadPackage[] downloadPackages = new DownloadPackage[objects.length];    	
    	for(int i = 0; i < objects.length; i++){
    		downloadPackages[i] = new DownloadPackage(objects[i], new File(output + objects[i].getKey()));
    	}
    	simpleMulti.downloadObjects(BUCKET, downloadPackages);
    	
    	
	}
}
