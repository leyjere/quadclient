package Information;

import java.io.Serializable;

public class Analog extends Data implements Comparable, Serializable{
	
	private int val;
	private Type type;
	
	/* Create an instance of analog data; 
	 * type indicates yaw, pitch, roll, throttle */
	public  Analog(Type type, int val){
		this.type = type;
		this.val = val;
	}
	
	/* Add this element to the main pipeline */
	public void add(){
		try {
			DataPipe.outgoing.put(this);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public Type getType(){
		return type;
	}
	
	public int getVal(){
		return val;
	}
	
	@Override
	public String toString() {
		return String.format("Type %s Value %s", type, val);
	}

	@Override
	public int compareTo(Object o) {
		
		return 0;
	}

}
