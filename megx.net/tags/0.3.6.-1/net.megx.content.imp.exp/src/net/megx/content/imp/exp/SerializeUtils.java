package net.megx.content.imp.exp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtils {
	public static IETag getFromBytes(byte [] data) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		ObjectInputStream ois = new ObjectInputStream(bis);
		return (IETag) ois.readObject();
	}
	
	public static byte[] toBytes(IETag tag) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(tag);
		return bos.toByteArray();
	}
	
	public static void main(String[] args) throws Exception {
		IETag tag = new IETag();
		tag.setPath("/www/node1");
		byte[] bytes = SerializeUtils.toBytes(tag);
		System.out.println("Bajtite " + bytes);
		
		IETag obj = SerializeUtils.getFromBytes(bytes);
		System.out.println("Returned path: " + obj.getPath());
		
		
	}
}
