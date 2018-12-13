package control;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;

import common.Message;
import common.Message.MessageType;

public class FileImporter {

	private static final int UPDATE_INTERVAL = 10;
	
	private static class ProgressWriter implements Runnable {
		
		private Consumer<Message> messageConsumer;
		private Connection connection;
		
		boolean isStopped = false;
		
		public ProgressWriter(Consumer<Message> messageConsumer, Connection connection) {
			this.messageConsumer = messageConsumer;
			this.connection = connection;
		}
		
		public void run() {
			while(!isStopped){
				try {
					Thread.sleep(UPDATE_INTERVAL);
					long count = new DBController().getCount(connection);
					messageConsumer.accept(new Message(MessageType.INFO, "Wrote " + count  + " to database."));
				} catch (Exception e) {
					// do nothing just try again
				}
			}
		}
	}

	public void writeNumbersToDB(Connection connection, String filePath, Consumer<Message> messageConsumer) throws SQLException {
		File importFile = new File(filePath);
		
		ProgressWriter progress = new ProgressWriter(messageConsumer, connection);
		Thread writeProgress = new Thread(progress);
		writeProgress.start();
		
		try (BufferedReader reader = createBufferedReader(importFile)){

			int lineNumber = 1;
			String currentLine = reader.readLine();
			
			DBController dbController = new DBController();
				
				while (currentLine != null) {
					Integer number = parseStringToNumber(currentLine, lineNumber, messageConsumer);
					
					if(number != null){
						dbController.addNumber(connection, number);
					}
					
					// prepare next iteration
					lineNumber++;
					currentLine = reader.readLine();
				}

		} catch (IOException e) {
			messageConsumer.accept(new Message(MessageType.ERROR, "Could not read file with path " + filePath));
		}

		progress.isStopped = true;
	}

	//TEST
	BufferedReader createBufferedReader(File importFile) throws FileNotFoundException {
		return new BufferedReader(new FileReader(importFile));
	}

	Integer parseStringToNumber(String line, int lineNumber, Consumer<Message> messageConsumer) {
		try {
			return Integer.valueOf(line);
		} catch (NumberFormatException e) {
			messageConsumer.accept(new Message(MessageType.WARNING,
					"File contains invalid input in line " + lineNumber + " with content \"" + line + "\"."));
			return null;
		}
	}

}