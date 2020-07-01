package tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GCopy {
	
	public static Object copy(Object o) {
		try {
			ByteArrayOutputStream BAOS = new ByteArrayOutputStream();
			ObjectOutputStream OOS = new ObjectOutputStream(BAOS);
			OOS.writeObject(o);
			ByteArrayInputStream BAIS = new ByteArrayInputStream(BAOS.toByteArray());
			ObjectInputStream OIS = new ObjectInputStream(BAIS);
			return OIS.readObject();
		} catch (Exception e) {e.printStackTrace(); return null;}
	}
}