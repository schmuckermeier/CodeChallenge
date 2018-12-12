package control;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class MedianCalculatorControllerTest {

	@Test
	public void testCalculate_sortedList_odd() {
		List<Double> numbers = Arrays.asList(-3.0, -1.5, 1.0, 3.5, 23543.43546);
		double median = new MedianCalculatorController().calculate(numbers);
		assertThat(median).isEqualTo(1.0);
	}
	
	@Test
	public void testCalculate_unsortedList_odd() {
		List<Double> numbers = Arrays.asList(3.5 ,-3.0, 1.0,  -1.5, 23543.43546);
		double median = new MedianCalculatorController().calculate(numbers);
		assertThat(median).isEqualTo(1.0);
	}
	
	@Test
	public void testCalculate_sortedList_even() {
		List<Double> numbers = Arrays.asList(-3.0, -1.5, 1.0, 2.0, 3.5, 23543.43546);
		double median = new MedianCalculatorController().calculate(numbers);
		assertThat(median).isEqualTo(1.5);
	}
	
	@Test
	public void testCalculate_unsortedList_even() {
		List<Double> numbers = Arrays.asList(3.5 ,-3.0, 1.0, 2.0, -1.5, 23543.43546);
		double median = new MedianCalculatorController().calculate(numbers);
		assertThat(median).isEqualTo(1.5);
	}

}
