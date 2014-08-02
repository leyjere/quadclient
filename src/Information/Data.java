package Information;

public abstract class Data {

	public abstract void add();
	
	public abstract String toString();
	
	public enum Type{
		Yaw(1), Pitch(2), Roll(3), Throttle(4), Trigger(5), Thumb(6), Thumb2(7),
		Top(8), Top2(9), Pinkie(10), Base(11), Base2(12), Base3(13), Base4(14),
		Base5(15), Base6(16), POV(17), ACK(18);
		private int value;

		private Type(int value) {
			this.value = value;
		}
	}
	
}
