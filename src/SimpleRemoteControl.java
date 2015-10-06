public class SimpleRemoteControl {
	/**
	 * @uml.property  name="slot"
	 * @uml.associationEnd  
	 */
	Command slot;

	public SimpleRemoteControl() {
	}

	public void setCommand(Command command) {
		slot = command;
	}

	public void sendCommand() {
		slot.execute();
	}
}