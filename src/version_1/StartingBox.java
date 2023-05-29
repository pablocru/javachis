package version_1;

public class StartingBox extends Box{
	private int timesPassed;

	public StartingBox(int boxNumber) {
		super(boxNumber);
	}

	public int getTimesPassed() {
		return timesPassed;
	}

	public void setTimesPassed(int timesPassed) {
		this.timesPassed = timesPassed;
	}
	
	
}
