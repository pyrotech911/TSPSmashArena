package game;
import java.io.*;
import com.google.gson.*;

public abstract class Participant {

	protected Actor thePlayer;
	protected String controllerString;
	protected Controller controller;
	protected BufferedReader reader;
	protected BufferedWriter writer;
	protected Gson json;
	protected Boolean active;
	protected Message messageFromClient;
	protected String name;
	protected Boolean spectator;
	protected Boolean confirmedSpectator;
	
	public void setIsSpectator(Boolean spectator) {
		this.spectator = spectator;
	}
	
	public Boolean isSpectator() {
		return this.spectator;
	}
	
	public void setIsConfirmedSpectator(Boolean confirmedSpectator) {
		this.confirmedSpectator = confirmedSpectator;
	}
	
	public Boolean isConfirmedSpectator() {
		return this.confirmedSpectator;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Actor getPlayer() {
		return this.thePlayer;
	}
	
	public void setPlayer(Actor player) {
		this.thePlayer = player;
	}
	
	public void setControllerString(String comm) {
		this.controllerString = comm;
	}
	
	public String getControllerString() {
		return this.controllerString;
	}

	public Controller getController() {
		return this.controller;
	}
	
	public void setController(Controller c){
		this.controller = c;
	}
	
	public void setMessageFromClient(Message messageFromClient) {
		this.messageFromClient = messageFromClient;
	}
	
	public void writeToClient(String outbound) throws IOException {
		this.getWriter().write(outbound);
		this.getWriter().newLine();
		this.getWriter().flush();
	}
	
	public void setWriter(BufferedWriter writer) {
		this.writer = writer;
	}
	
	public BufferedWriter getWriter() {
		return this.writer;
	}
	
	public void setReader(BufferedReader bufferedReader) {
		this.reader = bufferedReader;
	}
	
	public BufferedReader getReader() {
		return this.reader;
	}
	
	public Message getMessageFromClient() {
		return this.messageFromClient;
	}
	
	// Read controller from Reader. Reader may take from a connection or from an AI source.
	public void readController() throws IOException {
		setController(json.fromJson(getReader().readLine(), Controller.class));
	}
	
	public void readMessage() throws IOException {
		setMessageFromClient(json.fromJson(getReader().readLine(), Message.class));
	}
	
	public void setActive(Boolean bool) {
		active = bool;
	}
	
	public Boolean isActive() {
		return active;
	}
	
	public abstract void readControllerString() throws IOException;
}
