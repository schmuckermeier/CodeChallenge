package control;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import common.Message;
import common.Message.MessageType;

public class FileImporter {

	/**
	 * Read integer and doubles from given file. Invalid inputs will be ignored.
	 * @param filePath
	 * @param messageConsumer 
	 * @return unsorted list of numbers
	 */
	List<Double> readNumbersFromFile(String filePath, Consumer<Message> messageConsumer) {

		File importFile = new File(filePath);

		List<Double> numbers = new ArrayList<>();
		String currentLine = "";
		try {
			BufferedReader reader = createBufferedReader(importFile);

			int lineNumber = 0;
			currentLine = reader.readLine();
			
			while (currentLine != null) {
				lineNumber++;
				Double number = parseStringToNumber(currentLine, lineNumber, messageConsumer);
				currentLine = reader.readLine();
				
				if(number != null){
					numbers.add(number);
				}
			}

			reader.close();

		} catch (IOException e) {
			messageConsumer.accept(new Message(MessageType.ERROR, "Could not read file with path " + filePath));
		}

		return numbers;
	}

	//TEST
	BufferedReader createBufferedReader(File importFile) throws FileNotFoundException {
		return new BufferedReader(new FileReader(importFile));
	}
	
	/**
	 * Converts the given line to a number. <b>Empty lines</b> will be skipped without warning
	 * @param line
	 * @param lineNumber current line number for error message
	 * @param messageConsumer 
	 * @return number or null if line is empty or value could not be parsed
	 */
	Double parseStringToNumber(String line, int lineNumber, Consumer<Message> messageConsumer) {
		Double number = null;

		if (!line.isEmpty()) {

			try {
				if (line.contains(".")) {
					number = Double.valueOf(line);
				} else {
					number = new Double( Integer.valueOf(line));
				}
			} catch (NumberFormatException e) {
				messageConsumer.accept(new Message(MessageType.WARNING, "File contains invalid input in line " + lineNumber + " with content \"" + line + "\"."));
			}
		}
		return number;
	}
}