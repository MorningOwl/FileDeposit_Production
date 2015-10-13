package util;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
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
public class CloudConnector{

	private final String ACCESS_KEY = "GOOGD72WUOK3WMRTDZW2";
	private final String SECRET_KEY = "rzy903m2rJfy5r4dDGannvH7QB5Vddo9Y0F/lga+";
	private final String BUCKET = "filedepositcloud-1093.appspot.com";
	private GSCredentials gsCredentials;
	private GoogleStorageService gsService;
	private List<File> cloudList;
	
	public CloudConnector() throws Exception{
		cloudList = new ArrayList<File>();
		connect();
	}
	
	public void connect() throws Exception{
		gsCredentials = new GSCredentials(ACCESS_KEY, SECRET_KEY);
    	gsService = new GoogleStorageService(gsCredentials);
	}
	
	public void sendFileToCloud(File inFile, String outPath) throws Exception{
		GSObject fileObject = new GSObject(inFile);
		fileObject.setName(outPath);
		fileObject.setName(fileObject.getName().replace("\\", "/"));
		gsService.putObject(BUCKET, fileObject);
	}
	
	public boolean fileExistsInCloud (String inFilePath) throws Exception{
		//updateCloudList();
		return gsService.getBucket(BUCKET).containsMetadata(inFilePath);
		//return cloudList.contains(inFile);
	}
	
	public int getCloudSize() throws Exception{
		int cloudSize = 0;
		GSObject[] objects = gsService.listObjects(BUCKET);
		for(int i = 0; i < objects.length; i++){
			cloudSize += objects[i].getContentLength();
		}
		return cloudSize;
	}
	
	private void updateCloudList() throws Exception{
		GSObject[] objects = gsService.listObjects(BUCKET);
		cloudList.clear();
		for(int i = 0; i < objects.length; i++){
			cloudList.add(new File(objects[i].getKey()));
		}
	}
	
}
