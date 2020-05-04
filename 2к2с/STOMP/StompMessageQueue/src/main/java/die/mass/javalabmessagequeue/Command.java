package die.mass.javalabmessagequeue;

public enum Command {
	send, //producer --> server
	subscribe, //consumer --> server
	receive, //server --> consumer
	accepted, //consumer --> server
	completed //consumer --> server
}
