package it.reply.tamangoteam.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileUtils {
	
	private static final Logger log = LogManager.getLogger();
	
	private FileUtils() {
	}
		
	public static List<String> listDirectory(String directory, boolean ignoreDirs) {
		List<String> items = new ArrayList<>();
		File folder = new File(directory);
		if(!folder.exists()) {
			log.info("Does not exists: {}", directory);
			return items;
		}
		if(!folder.isDirectory()) {
			log.info("Not a directory: {}", directory);
			return items;
		}
		File[] listOfFiles = folder.listFiles();
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile() || (!ignoreDirs && listOfFiles[i].isDirectory())) {
				items.add(listOfFiles[i].getName());
			}
		}
		Collections.sort(items);
		return items;
	}
	
	public static List<String> readFile(String fileName) {
		List<String> output = new ArrayList<>();
		try (
				InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
			) {

			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				output.add(sCurrentLine);
			}
		} catch (NullPointerException e) {
			log.info("File not found in classpath: {}", fileName);
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return output;
	}
	
	public static byte[] readBinaryFile(String fileName) {
		byte[] data = null;
		try (
				InputStream is = FileUtils.class.getClassLoader().getResourceAsStream(fileName);
				DataInputStream dis = new DataInputStream(new BufferedInputStream(is));
			) {

			byte[] dataBuffer = new byte[1000];
		    ByteArrayOutputStream streamBuffer = new ByteArrayOutputStream();
			while(dis.available() > 0) {
				int read = dis.read(dataBuffer);
				if(read < 1000) {
					dataBuffer = Arrays.copyOfRange(dataBuffer, 0, read - 1);
				}
				streamBuffer.write(dataBuffer);
			}
			data = streamBuffer.toByteArray(); 
		} catch (NullPointerException e) {
			log.info("File not found in classpath: {}", fileName);
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return data;
	}

}
