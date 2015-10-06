public class SoundOnCommand implements Command {
	/**
	 * @uml.property  name="sound"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	Sound sound;

	public SoundOnCommand(Sound sound) {
		this.sound = sound;
	}

	public void execute() {
		System.out.println("Execute sound play");
		sound.play();
	}
}