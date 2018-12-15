package control;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;

import common.Message;
import common.Message.MessageType;

public class MedianCalculatorMain {

	public static void main(String[] args) {
		
		Consumer<Message> messageConsumer = m -> System.out.println(m.getType() + " : " + m.getMessageText());
		if(args.length == 0 ){
			messageConsumer.accept(new Message(MessageType.ERROR, "No import file specified."));
			return;
		}

		String filePath = args[0];
		
		messageConsumer.accept(new Message(MessageType.INFO, "Start calculation ...."));
		
		double median = Double.NaN;
		
		DBController dbController = new DBController();
		try (Connection connection = dbController.createConnection()){
			
			// create table to store values
			dbController.createNumbersTable(connection);
			
			// import numbers
			new FileImporter().writeNumbersToDB(connection, filePath, messageConsumer);
			
			// load median
			median = dbController.getMedian(connection);
			
		} catch (SQLException | ClassNotFoundException e) {
			messageConsumer.accept(new Message(MessageType.ERROR, "Could not write numbers to database or load median."));
			e.printStackTrace();
		}finally {
			try (Connection connection = dbController.createConnection()) {
				dbController.deleteNumbersTable(connection);
			} catch (SQLException | ClassNotFoundException e) {
				messageConsumer.accept(new Message(MessageType.ERROR, "Could not clean up database."));
				e.printStackTrace();
			}
		}
		
		if(!Double.isNaN(median)){
			messageConsumer.accept(new Message(MessageType.SUCCESS, "Calculated median is : " + median));
		}
	}

}