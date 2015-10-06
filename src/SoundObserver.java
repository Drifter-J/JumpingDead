public class SoundObserver implements Observer {

	private Sound effect_1 = new Sound("/eff_1.wav");

	private Sound effect_2 = new Sound("/eff_2.wav");

	public void update(Object obj) {
		if ((int) obj == 1) {
			effect_1.play();
		} else {
			effect_2.play();
		}
	}
}
