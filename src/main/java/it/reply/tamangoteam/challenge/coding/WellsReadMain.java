package it.reply.tamangoteam.challenge.coding;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.reply.tamangoteam.utils.FileUtils;

public class WellsReadMain {

	private static final Logger log = LogManager.getLogger();

	public static void main(String[] args) {
		
		List<String> wordsList = FileUtils.readFile("wellsread/words.txt");
		Set<String> words = new HashSet<>(wordsList);
		
		StringBuilder stringBuilder = new StringBuilder();
		List<String> text = FileUtils.readFile("wellsread/The Time Machine by H. G. Wells.txt");
		int n = 0;
		for(Iterator<String> i = text.iterator(); i.hasNext(); n++) {
			String line = i.next().replace(" ","");
			for(String keyword: words) {
				line = line.replaceAll(keyword, "");
			}
			if(!line.isBlank() ) {
				stringBuilder.append(line);
			}
			if(n%100 == 0) {
				log.info("{}/{}", n, text.size());
			}
			i.remove();
		}
		String lastLine = stringBuilder.toString();
		for(String keyword: words) {
			lastLine = lastLine.replaceAll(keyword, "");
		}
		log.info(lastLine);	
	}
}
