package auth;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/*
 * A "static" class not meant to be instantiated that contains the location data and methods 
 * necessary for users to Log-In.
 */
public final class UserAuthentication {

	private static final String AUTH_FILE_PATH = "auth.config";
	private static final String serverHost = "";
	private static final int serverPort = 0;
	
	/*
    Attempts to authenticate an inputed username and password against information in
    a local file with user info.
    */
    public static boolean localAuthenticate(String username, String password){
    	makeFileExist();
        if(username.isEmpty() && password.isEmpty()){
            System.out.println("Error: Non-null username and password expected.");
        }
        Boolean login = false;
        
        try {
        BufferedReader readFile = new BufferedReader(new FileReader(AUTH_FILE_PATH));
        String line = readFile.readLine();
        	while (line != null) {
        		if (line.equals(username)) {
        			line = readFile.readLine();
        			if (PasswordHash.validatePassword(password, line)){
        				login = true;
        				break;
        			}
        		}
            line = readFile.readLine();
        	}
        readFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return login;
    }

    /*
     * Checks the local authFile for a specific username. Returns true if the name is already in use, false otherwise.
     */
    public static Boolean nameExists(String username) {
    	if(username.isEmpty()){
            System.out.println("Error: Non-null username expected.");
            return null;
        }
    	
    	Boolean exists = false;
    	try {
            BufferedReader readFile = new BufferedReader(new FileReader(AUTH_FILE_PATH));
            String line = readFile.readLine();
            	while (line != null) {
            		if (line.equals(username)) {
            			exists = true;
            			break;
            		}
                line = readFile.readLine();
            	}
            readFile.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    	
    	return exists;
    }
    
    
    /*
     * Creates encrypted user Log-In info for testing. 
     * The number of hash iterations, salt, and hashed password are stored in the line below the username.
     */
    public static void storeNewUser(String username, String password){
    	makeFileExist();
		try {
			BufferedWriter writeFile = new BufferedWriter(new FileWriter(AUTH_FILE_PATH, true));
			String userHash = PasswordHash.createHash(password);
			writeFile.write("\n" + username + "\n", 0, username.length() + 2);
			writeFile.write(userHash, 0, userHash.length());
			writeFile.close();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private static void makeFileExist(){
    	File authFile = new File(AUTH_FILE_PATH);
    	if(!authFile.exists()){
    		try {
				authFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}   	
    }
}