package control;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import common.Message;
import common.Message.MessageType;

public class FileImporterTest {
	
	private List<Message> messages;
	private Consumer<Message> messageConsumer;
	private FileImporter fileImporter;
	private BufferedReader reader;
	
	private static final String STOP = null;  // return null to terminate loop
	
	@Before
	public void setUp()throws Exception{
		this.messages = new ArrayList<>();
		this.messageConsumer = messages::add;
		this.fileImporter = spy(new FileImporter());
		
		this.reader = Mockito.mock(BufferedReader.class);
		doReturn(reader).when(fileImporter).createBufferedReader(Mockito.any());
	}
	
//	@Test
//	public void testReadNumbersFromFile_singelInt() throws Exception {
//		when(reader.readLine()).thenReturn("12", STOP);
//		double numbers = fileImporter.readNumbersFromFile("test/example.txt", messageConsumer);
//		assertThat(numbers).containsExactly(12.0);
//		assertThat(messages).isEmpty();
//	}
//	
//	@Test
//	public void testReadNumbersFromFile_multipleInts() throws Exception {
//		when(reader.readLine()).thenReturn("12", "5", STOP);
//		List<Double> numbers = fileImporter.readNumbersFromFile("test/example.txt", messageConsumer);
//		assertThat(numbers).containsExactly(12.0, 5.0);
//		assertThat(messages).isEmpty();
//	}
//	
//	@Test
//	public void testReadNumbersFromFile_singelDouble() throws Exception {
//		when(reader.readLine()).thenReturn("12.5", STOP);
//		List<Double> numbers = fileImporter.readNumbersFromFile("test/example.txt", messageConsumer);
//		assertThat(numbers).containsExactly(12.5);
//		assertThat(messages).isEmpty();
//	}
//	
//	@Test
//	public void testReadNumbersFromFile_multipleDoubles() throws Exception {
//		when(reader.readLine()).thenReturn("12.5", "5.0003", STOP);
//		List<Double> numbers = fileImporter.readNumbersFromFile("test/example.txt", messageConsumer);
//		assertThat(numbers).containsExactly(12.5, 5.0003);
//		assertThat(messages).isEmpty();
//	}
//
//	@Test
//	public void testReadNumbersFromFile_empty() throws Exception {
//		when(reader.readLine()).thenReturn("", STOP);
//		List<Double> numbers = fileImporter.readNumbersFromFile("test/example.txt", messageConsumer);
//		assertThat(numbers).isEmpty();
//		assertThat(messages).isEmpty();
//	}
//
//	@Test
//	public void testReadNumbersFromFile_text() throws Exception {
//		when(reader.readLine()).thenReturn("text", STOP);
//		List<Double> numbers = fileImporter.readNumbersFromFile("test/example.txt", messageConsumer);
//		assertThat(numbers).isEmpty();
//		assertThat(messages).containsExactly(new Message(MessageType.WARNING, "File contains invalid input in line 1 with content \"text\"."));
//
//	}
//	
//	@Test
//	public void testReadNumbersFromFile_mixed() throws Exception {
//		when(reader.readLine()).thenReturn("5", "12.5", "", "text", "0.0" , "0", STOP);
//		List<Double> numbers = fileImporter.readNumbersFromFile("test/example.txt", messageConsumer);
//		assertThat(numbers).containsExactly(5.0, 12.5, 0.0, 0.0);
//		assertThat(messages).containsExactly(new Message(MessageType.WARNING, "File contains invalid input in line 4 with content \"text\"."));
//	}

}
