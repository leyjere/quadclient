package com.example.clientapp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import Information.ACK;
import Information.Analog;
import Information.Data;
import Information.DataPipe;
import Information.Digital;
import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;

public class Client extends IntentService{

	private ObjectInputStream commandIn;
	private ObjectOutputStream commandOut;

	public Client(){
		super("Client");
	}


	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {        
		super.onCreate();
	}

	@Override
	public void onDestroy() {        
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {        
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub

		try
		{
			System.out.println("Attempting to connect");
			Socket client = new Socket("203.100.211.189",5900);
			System.out.println("Just connected to "
					+ client.getRemoteSocketAddress());
			OutputStream outToServer = client.getOutputStream();

			commandOut = new ObjectOutputStream(client.getOutputStream());
			commandIn = new ObjectInputStream(client.getInputStream());

			while(true){

				commandOut.writeObject(new Digital(Data.Type.Base,true));

				System.out.println("CLIENT: Object Sent");

				try {
					Object o = (Object)commandIn.readObject();
					if(o instanceof Digital){
						Digital d = (Digital)o;
						System.out.println(d.toString());
						DataPipe.incoming.put(d);
					}else if(o instanceof Analog){
						Analog a = (Analog)o;
						System.out.println(a.toString());
						DataPipe.incoming.put(a);
					}
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.out.println("CLIENT: Cannot add received object to queue");
				}
				System.out.println("CLIENT: Object received");
			}

		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}


}
