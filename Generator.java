import java.util.Random;

public class Generator {

	Random rnd = new Random();

	String createNumber(int digits) {

		StringBuilder randomNumber = new StringBuilder();

		for (int i = 0; i < digits; i++) {
			
			randomNumber.append(Integer.toString(rnd.nextInt(10)));
			
		}

		return randomNumber.toString();
	}

}
