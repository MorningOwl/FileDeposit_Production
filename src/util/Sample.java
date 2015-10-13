package util;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStreamReader;

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
 * This class includes all the code samples as listed in the Google Storage
 * <a href="http://code.google.com/apis/storage/docs/developer-guide.html">Developer's Guide</a>.
 * <p>
 * This code is provided as a convenience for those who are reading through the guide and don't want
 * to type out the examples themselves.
 * </p>
 *
 * @author Google Developers
 */
public class Sample {

	private static final String ACCESS_KEY = "GOOGD72WUOK3WMRTDZW2";
	private static final String SECRET_KEY = "rzy903m2rJfy5r4dDGannvH7QB5Vddo9Y0F/lga+";
	private static final String BUCKET = "filedepositcloud-1093.appspot.com";
	
    public static void main(String[] args) throws Exception {
    	GSCredentials gsCredentials = new GSCredentials(ACCESS_KEY, SECRET_KEY);
    	
    	// To communicate with Google Storage use the GoogleStorageService.
    	GoogleStorageService gsService = new GoogleStorageService(gsCredentials);
    	System.out.println(gsService);
    	// A good test to see if your GoogleStorageService can connect to GS is to list all the buckets you own.
    	// If a bucket listing produces no exceptions, all is well.

    	//GSBucket[] myBuckets = gsService.listAllBuckets();
    	//System.out.println("How many buckets do I have in GS? " + myBuckets.length);
    	
    	File testFile = new File("C:/Users/Edward/Desktop/JavaScript Essential Training Intro, 1, & 2 Notes.pdf");
    	GSObject fileObject = new GSObject(testFile);
    	fileObject.setName("Users/Edward/Desktop/JavaScript Essential Training Intro, 1, & 2 Notes.pdf");
    	gsService.putObject("filedepositcloud-1093.appspot.com",fileObject);
    	
    	SimpleThreadedStorageService simpleMulti = new SimpleThreadedStorageService(gsService);
    	
    	GSBucket bucket = new GSBucket("filedepositcloud-1093.appspot.com");
    	GSObject[] objects = gsService.listObjects(BUCKET);
    	DownloadPackage[] downloadPackages = new DownloadPackage[objects.length];
    	
    	for(int i = 0; i < objects.length; i++){
    		downloadPackages[i] = new DownloadPackage(objects[i], new File("C:/Users/Edward/Desktop/test/" + objects[i].getKey()));
    	}
    	simpleMulti.downloadObjects(BUCKET, downloadPackages);
    	
    }

}
