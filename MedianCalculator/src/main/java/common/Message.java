package common;

public class Message {
	
	public enum MessageType{
		SUCCESS,
		INFO,
		WARNING,
		ERROR
	}
	
	private final MessageType type;
	private final String messageText;
	
	public Message(MessageType type, String messageText) {
		this.messageText = messageText;
		this.type = type;
	}
	
	public MessageType getType() {
		return type;
	}

	public String getMessageText() {
		return messageText;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((messageText == null) ? 0 : messageText.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (messageText == null) {
			if (other.messageText != null)
				return false;
		} else if (!messageText.equals(other.messageText))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
}
