import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Rank {
	/**
	 * @uml.property  name="arr"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.Integer"
	 */
	ArrayList<Integer> arr = new ArrayList<Integer>();
	/**
	 * @uml.property  name="r1"
	 */
	int r1;
	/**
	 * @uml.property  name="r2"
	 */
	int r2;
	/**
	 * @uml.property  name="r3"
	 */
	int r3;
	/**
	 * @uml.property  name="r4"
	 */
	int r4;
	/**
	 * @uml.property  name="r5"
	 */
	int r5 = 0;
	/**
	 * @uml.property  name="cnt"
	 */
	int cnt = 0;
	/**
	 * @uml.property  name="nth"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.Integer"
	 */
	int nth = 0;

	public Rank() {
		readRanking(arr);
	}

	public Rank(int score) {
		readRanking(arr);
		arr.add(score);

		Collections.sort(arr);

		int i = arr.size() - 1;
		while (i >= 0 && arr.get(i) != 0) {
			cnt++;
			i--;
		}
		nth = i + 1;

		if (cnt < 5) {
			if (cnt == 0) {
				r1 = 0;
				r2 = 0;
				r3 = 0;
				r4 = 0;
				r5 = 0;
			}

			else {
				int nth = 0;
				while (arr.get(nth) == 0)
					nth++;

				if (cnt == 1) {
					r1 = arr.get(nth);
					r2 = 0;
					r3 = 0;
					r4 = 0;
					r5 = 0;
				} else if (cnt == 2) {
					r1 = arr.get(nth);
					r2 = arr.get(++nth);
					r3 = 0;
					r4 = 0;
					r5 = 0;
				} else if (cnt == 3) {
					r1 = arr.get(nth);
					r2 = arr.get(++nth);
					r3 = arr.get(++nth);
					r4 = 0;
					r5 = 0;
				} else // if (cnt == 4)
				{
					r1 = arr.get(nth);
					r2 = arr.get(++nth);
					r3 = arr.get(++nth);
					r4 = arr.get(++nth);
					r5 = 0;
				}
			}
		} else {
			r1 = arr.get(nth);
			r2 = arr.get(++nth);
			r3 = arr.get(++nth);
			r4 = arr.get(++nth);
			r5 = arr.get(++nth);
		}
		// Collections.reverse(arr);
		writeRanking(arr);
	}

	public String getRank1() {
		return Integer.toString(r1);
	}

	public String getRank2() {

		return Integer.toString(r2);
	}

	public String getRank3() {

		return Integer.toString(r3);
	}

	public String getRank4() {

		return Integer.toString(r4);
	}

	public String getRank5() {

		return Integer.toString(r5);
	}

	public static void printRanking(ArrayList<Integer> arr) {
		try {
			Scanner sc = new Scanner(new File("Ranking.txt"));

			while (sc.hasNext()) {
				String temp = sc.nextLine();
				System.out.println(temp);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void readRanking(ArrayList<Integer> arr) {
		ArrayList<Integer> arrTemp = arr;
		try {
			Scanner sc = new Scanner(new File("Ranking.txt"));

			while (sc.hasNext()) {
				String temp = sc.nextLine();

				int score = Integer.parseInt(temp);

				arrTemp.add(score);
				arr = arrTemp;
			}
			sc.close();
		} catch (FileNotFoundException e) {
			arr.add(0);
			arr.add(0);
			arr.add(0);
			arr.add(0);
			arr.add(0);
		}
	}

	public static void writeRanking(ArrayList<Integer> arr) {
		try {
			FileWriter fw = new FileWriter("Ranking.txt");

			for (int i = 0; i < arr.size(); i++) {
				fw.write(Integer.toString(arr.get(i)));
				fw.write("\n");
			}
			fw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
