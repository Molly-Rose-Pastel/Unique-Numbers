import java.util.concurrent.ConcurrentHashMap;

class GeneratorThread extends Thread {
	Thread t;
	private String threadName;
	int numberAmount = 0;
	Generator generator;
	ConcurrentHashMap<String, String> map;

	GeneratorThread(String name, int value, ConcurrentHashMap<String, String> mapT) {
		threadName = name;
		numberAmount = value;
		map = mapT;
		t = new Thread(this, name);

		System.out.println("Creating Thread " + threadName);
	}

	public void run() {
		System.out.println("Running Thread " + threadName);
		generator = new Generator();
		int counterDuplicates = 0;

		for (int i = 0; i < numberAmount; i++) {
			String number = generator.createNumber(12);
			while ((map.put(String.valueOf(number.hashCode()), String.valueOf(number))) != null) {
				counterDuplicates++;
				number = generator.createNumber(12);
			}
		}
		System.out.println("Thread " + threadName + " created " + counterDuplicates + " duplicates.");
		System.out.println("Thread " + threadName + " exiting.");
	}

	public void start() {
		System.out.println("Starting Thread: " + threadName);
		

		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}

}
