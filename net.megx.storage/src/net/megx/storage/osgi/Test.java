package net.megx.storage.osgi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import net.megx.storage.Context;
import net.megx.storage.ResourceAccessException;
import net.megx.storage.StorageContext;
import net.megx.storage.StorageException;
import net.megx.storage.StorageSecuirtyException;
import net.megx.storage.StorageSession;
import net.megx.storage.StorageSessionProvider;
import net.megx.storage.StoredResource;
import net.megx.storage.impl.StorageSessionProviderImpl;


public class Test {
	public static void main(String[] args) throws StorageException, URISyntaxException, UnsupportedEncodingException, IOException {
		System.out.println("Test the storage session.");
		StorageSessionProvider provider = new StorageSessionProviderImpl();
		Context context = new StorageContext("test-user", new HashMap<String, Object>());
		
		StorageSession session = provider.openSession(context);
		URI fu = new URI("file://storage-root/myWorkspace/someSubDir/theFile.txt");
		StoredResource rc = null;
		if(!session.exists(fu)){
			System.out.println("The file does not exist. Creating new one.");
			rc = session.create(fu);
			OutputStream os = rc.write();
			os.write("This is a test content in the file.".getBytes("UTF-8"));
			os.close();
		}else{
			System.out.println("The resource already exist.");
			rc = session.lookup(fu);
		}
		
		testReadData(fu, session);
		
		System.out.println("\n\n****** MOVE TEST *****");
		URI moveToURI = new URI("file://storage-root/myWorkspace/someSubDir/theFileMoved.txt");
		StoredResource moved = session.relocate(rc, moveToURI);
		testReadData(moveToURI, session);
		
		System.out.println("\n\n****** COPY TEST *****");
		URI copyToURI = new URI("file://storage-root/myWorkspace/someSubDir/copyOfThefile.txt");
		StoredResource copy = session.copy(moved, copyToURI);
		testReadData(copyToURI, session);
		
		System.out.println("\n\n****** DELETE TEST *****");
		session.delete(copy);
		System.out.println("Deleted resource exits: " + session.exists(copyToURI));
		
	}
	
	
	private static void testReadData(URI uri, StorageSession session) throws StorageSecuirtyException, ResourceAccessException, IOException{
		StoredResource rc = session.lookup(uri);
		InputStream is = rc.read();
		
		int data = 0;
		System.out.println("File:");
		System.out.println("\tDate Created: " + rc.getResourceMeta().getDateCreated());
		System.out.println("\tLast Modified: " + rc.getResourceMeta().getDateModified());
		System.out.println("\tSize: " + rc.getResourceMeta().getSize());
		System.out.println("\tURI: " + rc.getURI());
		System.out.println("\tAccess URI (Resolved): " + rc.getAccessURI());
		System.out.println("File Data:");
		System.out.println("======================");
		while((data = is.read()) != -1){
			System.out.print((char)data);
		}
		System.out.println("\n======================");
		is.close();
	}
}
