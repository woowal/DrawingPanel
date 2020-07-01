package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GFile {
	
	private File file;
	
	public GFile() {
		this.file = null;
		
	}
	
	public Object readObject(File file) {
		try {
			this.file = file;
			FileInputStream fileInputStream;
			fileInputStream = new FileInputStream(this.file);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			Object object = objectInputStream.readObject();
			objectInputStream.close();
			return object;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void saveObject(Object object,File file) {
		try {
			this.file = file;
			FileOutputStream fileOutputStream;
			fileOutputStream = new FileOutputStream(this.file);
			ObjectOutputStream objectInputStream = new ObjectOutputStream(fileOutputStream);
			objectInputStream.writeObject(object);
			objectInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
