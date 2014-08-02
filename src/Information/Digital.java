package Information;

import java.io.Serializable;

import Information.Data.Type;

public class Digital extends Data implements Comparable, Serializable{
	
	private boolean val;
	private Type type;
	
	/* Create an instance of analog data; 
	 * type indicates yaw, pitch, roll, throttle */
	public  Digital(Type type, boolean i){
		this.type = type;
		this.val = i;
	}
	
	/* Add this element to the main pipeline */
	public void add(){
		try {
			DataPipe.outgoing.put(this);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
