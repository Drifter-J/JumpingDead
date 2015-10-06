import java.util.ArrayList;

public class MyObservable implements Observable {

	private int deltaX = 1;
	private ArrayList<Observer> observers = new ArrayList<Observer>();

	public void setValue(int deltaX) {
		this.deltaX = deltaX;
		notifyObservers();
	}

	public int getValue() {
		return this.deltaX;
	}

	public void addObserver(Observer ov) {
		observers.add(ov);
	}

	public void removeObserver(Observer ob) {
		observers.remove(ob);
	}

	public void notifyObservers() {
		for (Observer ob : observers) {
			System.out.println("Notifying Observers");
			ob.update(deltaX);
		}
	}
}