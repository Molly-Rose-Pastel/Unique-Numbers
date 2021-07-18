import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentHashMap;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class MainProgram {
//	static ConcurrentHashMap<String, String> map;

	public static void main(String[] args) throws IOException, InterruptedException {
		/* create new Generator and Hashmap object */
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
		startTime = System.nanoTime();
		/*
		 * now we want to create 3 Threads and create 40000 random numbers with them
		 */
		

		try {
			GeneratorThread T1 = new GeneratorThread("1", 4000000, map);
			GeneratorThread T2 = new GeneratorThread("2", 4000000, map);
//			GeneratorThread T3 = new GeneratorThread("3", 40000, map);
			T1.t.start();
			T2.t.start();
//			T3.t.start();
			T1.t.join();
			T2.t.join();
//			T3.t.join();
		} catch (Exception e) {
			System.out.println(e);
		}
		
		endTime = System.nanoTime();
		System.out.printf("creating specified amount of elements took %d ms\n\n", (endTime - startTime) / 1000000);
		System.out.printf("%d elements in the map\n", map.size());

		/* write the hashmap to csv file using a buffered writer */
		startTime = System.nanoTime();
		
		BufferedWriter writer = new BufferedWriter(new FileWriter("data.csv", true));
		for(String key : map.keySet()) {
			writer.write(key+","+map.get(key)+"\n");
		}
		writer.close();
		endTime = System.nanoTime();

		System.out.printf("writing .csv file took %d ms\n\n", (endTime - startTime) / 1000000);

	}
}
