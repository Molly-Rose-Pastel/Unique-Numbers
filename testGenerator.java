
import java.util.concurrent.*;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.*;

public class testGenerator {
	public static void main(String[] args) throws IOException {
		/* create new Generator and Hashmap object */

		Generator generator = new Generator();
		ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();

		long startTime = System.nanoTime();

		/* create new file if it doesnt exist */
		File dataFile = new File("data.csv");
		/* this method will only create a new file if it does not already exist */
		dataFile.createNewFile();

		/* load the data.csv into string variable */
		String data = new String(Files.readAllBytes(Paths.get("data.csv")));

		if (!data.isEmpty()) {
			/* split the entries into key/value pairs */
			String[] keyValuePairs = data.split("\n");

			/* iterate over the pairs */
			for (String pair : keyValuePairs) {
				/* split the pairs into key and value */
				String[] entry = pair.split(",");

				/* add the entry to the hashmap */
				map.put(entry[0], entry[1]);
			}
		}

		long endTime = System.nanoTime();
		System.out.printf("reading .csv file took %d ms\n\n", (endTime - startTime) / 1000000);
		
		/* create new numbers specified in the for loop and put them into
		 * the hashmap
		 */
		int counter = 0;
		int counterDuplicates = 0;
		startTime = System.nanoTime();
		for (long i = 0; i < 4000000; i++) {
			counter++;
			String number = generator.createNumber(12);
			while ((map.put(String.valueOf(number.hashCode()), String.valueOf(number))) != null) {
				counter++;
				counterDuplicates++;
				number = generator.createNumber(12);
			}
		}
		endTime = System.nanoTime();
		System.out.printf("creating %d elements took %d ms\n\n", counter, (endTime - startTime) / 1000000);
		System.out.printf("%d elements in the map\n", map.size());
		System.out.printf("%d duplicates generated\n", counterDuplicates);

		
		/* convert the Hasmap to a String variable */
		startTime = System.nanoTime();

		StringBuilder builder = new StringBuilder();
		for (ConcurrentHashMap.Entry<String, String> kvp : map.entrySet()) {
			builder.append(kvp.getKey());
			builder.append(",");
			builder.append(kvp.getValue());
			builder.append("\n");
		}

		String content = builder.toString().trim();

		/* Write the string to a .csv file */
		try {
		    Files.write(Paths.get("data.csv"), content.getBytes(), WRITE);
		}catch (IOException e) {
			e.printStackTrace();
		}
		endTime = System.nanoTime();

		System.out.printf("writing .csv file took %d ms\n\n", (endTime - startTime) / 1000000);

	}
}