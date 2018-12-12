package control;
import java.util.Comparator;
import java.util.List;

public class MedianCalculatorController {

	/**
	 * Sort list of numbers and calculate median. If list is even the mean of both centered values will be returned.
	 * @param numbers
	 * @return the median of numbers
	 */
	double calculate(List<Double> numbers) {
		Comparator<Double> comparator = (first, second) -> first.compareTo(second);
		numbers.sort(comparator);

		boolean isEvenLength = numbers.size() % 2 == 0;

		double median;

		if (isEvenLength) {
			int indexBelowMedian = numbers.size() / 2;
			int indexAboveMedian = indexBelowMedian + 1;

			double sumOfMedianElements = numbers.get(indexBelowMedian-1).doubleValue()
					+ numbers.get(indexAboveMedian-1).doubleValue();
			median = sumOfMedianElements / 2;
		} else {
			int center = numbers.size() / 2;
			median = numbers.get(center).doubleValue();
		}

		return median;
	}

}