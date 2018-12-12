package control;
import java.util.List;
import java.util.function.Consumer;

import common.Message;
import common.Message.MessageType;

public class MedianCalculatorMain {

	public static void main(String[] args) {
		
		if(args.length == 0 ){
			System.out.println("No import file specified.");
			return;
		}

		String filePath = args[0];
		
		Consumer<Message> messageConsumer = m -> System.out.println(m.getType() + " : " + m.getMessageText());
		
		List<Double> numbers = new FileImporter().readNumbersFromFile(filePath, messageConsumer);

		double median = new MedianCalculatorController().calculate(numbers);

		messageConsumer.accept(new Message(MessageType.INFO, "Calculated median is : " + median));
	}

}