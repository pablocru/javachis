package version_1;

public class StartingBox extends Box{
//	Attributes
	private int timesPassed;

//	Constructor
	public StartingBox(int boxNumber) {
		super(boxNumber);
	}

//	Setters
	public void setTimesPassed(int timesPassed) {
		this.timesPassed = timesPassed;
	}
	
//	Getters
	public int getTimesPassed() {
		return timesPassed;
	}
	
}
