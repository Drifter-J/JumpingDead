public class SoundOffCommand implements Command {
	/**
	 * @uml.property  name="sound"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	Sound sound;

	public SoundOffCommand(Sound sound) {
		this.sound = sound;
	}

	public void execute() {
		System.out.println("Execute sound stop");
		sound.stop();
	}
}