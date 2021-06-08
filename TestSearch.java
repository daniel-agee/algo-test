import java.util.*;

public class TestSearch {
	protected double[] arr;
	protected String searchType; //algorithm to use
	protected int searches; //number of searches to perform

	/**
	* @param arraySize The size of the array to be filled
	* @return A random value from the array
	* **/
	public double fillArray(int arraySize) {
		arr = new double[arraySize];
		Random rnd = new Random();

		double stepSize = Double.MIN_VALUE / arr.length;
		arr[0] = Double.MIN_VALUE + rnd.nextDouble() / stepSize;
		for (int i=1; i<arr.length; i++) {
			arr[i] = arr[i-1] + Double.MIN_VALUE + rnd.nextDouble() / stepSize;
		}

		//with 50% probability, return a random value from the array
		if (rnd.nextInt(2) == 0) {
			return arr[rnd.nextInt(arraySize)];
		}
		//or not from the array
		return rnd.nextDouble();
	}

	/**
	 * @param arr the array to be searched
	 * @param target the target value to be found
	 * @return the index of the target in the array or -1 if target is not in the array
	 * **/
	private int linearSearch(double[] arr, double target) {
		for (int i=0; i<arr.length; i++) {
			if (target == arr[i]) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Recursive implementation of the binary search algorithm
	 * @param arr The array to be searched
	 * @param target The target value to be found
	 * @param low The index of the first item to be searched
	 * @param high The index of the last item to be searched
	 * @return The index of the target in the array or -1 if the target is not in the array
	 * */

	private int binRecursive(double[] arr, double target, int low, int high) {
		if (low > high){ //Base case
			return -1;
		}

		int mid = (low+high)/2;
		if (target == arr[mid]) {
			return mid;
		}
		else if (target < arr[mid]) {
			return binRecursive(arr, target, low, mid-1); //Cannot include mid
		}
		else {
			return binRecursive(arr, target, mid+1, high);
		}
	}

	/**
	 * Iterative implementation of the binary search algorithm
	 * @param arr The array to be searched
	 * @param target The target value to be found in the array
	 * @return The index of the target in the array or -1 if the target is not in the array
	 * */

	private int binIterative(double[] arr, double target) {
		int low = 0;
		int high = arr.length-1;

		while (low <= high) {
			int mid = (low+high)/2;
			if (target == arr[mid]) {
				return mid;
			}
			else if (target < arr[mid]) {
				high = mid-1;
			}
			else {
				low = mid+1;
			}
		}
		return -1;
	}

	/**
	 * This function allows switch between the different algorithms / implementations
	 * @param arr The array to be searched.
	 * @param target The target to be found in the array.
	 * @param searchMethod One of the search methods -- Linear, BinRecursive, BinIterative.
	 * @return The index of the target in the array.
	 * @throws Exception When an invalid searchMethod is passed.
	 * */

	public int search(double[] arr, double target, String searchMethod) throws Exception {
		if (searchMethod.contains("Linear")) {
			return linearSearch(arr, target);
		}
		else if (searchMethod.contains("BinRecursive")) {
			return binRecursive(arr, target, 0, arr.length-1);
		}
		else if (searchMethod.contains("BinIterative")) {
			return binIterative(arr, target);
		}
		else {
			throw new Exception("Search method does not exist");
		}
	}

	/**
	 * This method executes a number of searches in the array and keeps track of the time
	 * it takes to execute the searches
	 * @param searches The number of searches to perform.
	 * @param arraySize The size of the array to be created and subsequently searched
	 * @param searchType The algorithm use to in searching the array
	 * */

	public void timeSearches(int searches, int arraySize, String searchType) {
		System.out.println("Filling the " +arraySize+"-item array. . .");
		double target = fillArray(arraySize); //set target to rnd double(in or out of array)
		System.out.println("Searching the array "+searches+" times using "+searchType+" for value "+target+"... \n");
		long totalTime = 0; //Keep a running total of time spent searching

		try {
			for (int i=0; i<searches;i++) {
				long startTime = System.currentTimeMillis();
				search(arr, target, searchType);
				totalTime += System.currentTimeMillis() - startTime;
			}
		}
		catch(Exception e) {
			System.out.println("Exception on searching via "+searchType+" for array size "+arraySize);
			e.printStackTrace();
		}
		System.out.println(searchType+" search: total time searching = "+totalTime+"ms");
	}

	/**
	 * Fills the array according to arguments and determines the time required to search/
	 * @param args An array containing: # of searches; array size; search algorithm
	 * */

	public static void main(String[] args) {
		int searches = 100;
		int arraySize = 100000000;
		String searchAlgo = "BinRecursive";

		//Pick up arguments from command line
		try {
			if (args.length != 3) {
				throw new Exception("Incorrect number of arguments");
			}
			searches = Integer.parseInt(args[0]);
			arraySize = Integer.parseInt(args[1]);
			searchAlgo = args[2];
		}
		catch(Exception e) {
			System.out.println("Arguments are incorrect; reverting to default\n");
		}

		TestSearch test = new TestSearch();
		test.timeSearches(searches, arraySize, searchAlgo);
	}
}