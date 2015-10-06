public class MyObserver implements Observer {
	public void update(Object obj) {
		if ((int) obj == 1) {
			System.out.println("Change the direction to right");
		} else {
			System.out.println("Change the direction to left");
		}
	}
}