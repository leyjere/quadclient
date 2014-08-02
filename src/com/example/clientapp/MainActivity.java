package com.example.clientapp;

import Information.Analog;
import Information.Data;
import Information.DataPipe;
import Information.Digital;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity{

	private Button btnStart, btnStop;
	private TextView thr, elev, ail, rudd;
	private Handler handler;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnStart = (Button)findViewById(R.id.start);
		btnStop = (Button)findViewById(R.id.stop);


		thr = (TextView)findViewById(R.id.thr);
		rudd = (TextView)findViewById(R.id.rudd);
		ail = (TextView)findViewById(R.id.ail);
		elev = (TextView)findViewById(R.id.elev);
		
		handler = new Handler();

		Thread t = new Thread(new Dispatcher());
		t.start();


		btnStart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, Client.class);
				i.putExtra("name", "SurvivingwithAndroid");        
				MainActivity.this.startService(i);        
			}
		});

		btnStop.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, Client.class);
				MainActivity.this.stopService(i);
			}
		});


	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private class Dispatcher implements Runnable{
		
		private Analog a;
		private Digital d;
		
		public Dispatcher(){
			
		}
		
		@Override
		public void run(){
			while(true){
				if(DataPipe.incoming.size()>0){
					Object o = DataPipe.incoming.remove();
					if (o instanceof Digital){
						d = (Digital)o;
						o = null;


					}else if(o instanceof Analog){
						a = (Analog)o;
						o = null;
							
						/* PURELY FOR GUI */
						handler.post(new Runnable(){

							@Override
							public void run() {
								
								
								if(a.getType() == Data.Type.Throttle){
									thr.setText(String.valueOf(a.getVal()));
								}else if(a.getType() == Data.Type.Yaw){
									rudd.setText(String.valueOf(a.getVal()));
								}else if(a.getType() == Data.Type.Pitch){
									elev.setText(String.valueOf(a.getVal()));
								}else if(a.getType() == Data.Type.Roll){
									ail.setText(String.valueOf(a.getVal()));
								}
								
							};
						});

					}
				}
			}
		}
		
	}


}
