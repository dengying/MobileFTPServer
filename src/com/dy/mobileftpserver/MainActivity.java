package com.dy.mobileftpserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	private static FtpServer mFtpServer;  
    private static String ftpConfigDir= Environment.getExternalStorageDirectory().getAbsolutePath()+"/ftpConfig/";  
    private static Handler handler=new Handler();
    private static EditText path;
	
	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
		
		AsyncTask< String, Integer, String> task=new AsyncTask<String, Integer, String>(){
			@Override
			protected String doInBackground(String... arg0) {
				// TODO Auto-generated method stub
				File f=new File(ftpConfigDir);  
				if(!f.exists()) { 
					f.mkdir();  
				}
				copyResourceFile(R.raw.users, ftpConfigDir+"users.properties");  
				Config1();  
				return null;
			}
		};
		task.execute("");
		
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						PlaceholderFragment.newInstance(position + 1)).commit();
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_section1);
			break;
		case 2:
			mTitle = getString(R.string.title_section2);
			break;
		case 3:
			mTitle = getString(R.string.title_section3);
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			TextView textView = (TextView) rootView
					.findViewById(R.id.section_label);
			textView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			path=(EditText) rootView.findViewById(R.id.txt_path);
			path.setText("ftp://"+getLocalIpAddress()+":4050");
			final Button btn_launcher=(Button) rootView.findViewById(R.id.btn_ftp_launcher);
			btn_launcher.setText("关闭");
			btn_launcher.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					if(mFtpServer!=null){
						if(mFtpServer.isStopped()){
							try{
								Config1();
								btn_launcher.setText("关闭");
							}catch(Exception e){
								e.printStackTrace();
							}
						}else{
							mFtpServer.stop();
							btn_launcher.setText("启动");
						}
					}
					
				}
			});
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MainActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
	}
	
	public static String getLocalIpAddress() {
		String strIP = null;
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						strIP = inetAddress.getHostAddress().toString();
						if(strIP!=null && isIPAddress(strIP.trim())){
							return strIP;
						}
					}
				}
			}
		} catch (SocketException ex) {
			Log.e("msg", ex.toString());
		}
		return strIP;
	}
	
	/**
	 * 校验 IP地址
	 * @param ipaddr
	 * @return
	 */
	public static boolean isIPAddress(String ipaddr) {
		boolean flag = false;
		Pattern pattern = Pattern
				.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
		Matcher m = pattern.matcher(ipaddr);
		flag = m.matches();
		return flag;
	}

	private void copyResourceFile(int rid, String targetFile) {
		InputStream fin = ((Context) this).getResources().openRawResource(rid);
		FileOutputStream fos = null;
		int length;
		try {
			fos = new FileOutputStream(targetFile);
			byte[] buffer = new byte[1024];
			while ((length = fin.read(buffer)) != -1) {
				fos.write(buffer, 0, length);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void Config1() {
		// Now, let's configure the port on which the default listener waits for
		// connections.

		FtpServerFactory serverFactory = new FtpServerFactory();

		ListenerFactory factory = new ListenerFactory();

		PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();

		String[] str = { "mkdir", ftpConfigDir };
		try {
			Process ps = Runtime.getRuntime().exec(str);
			try {
				ps.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		String filename = ftpConfigDir + "users.properties";// "/sdcard/users.properties";
		try{
			Properties props=new Properties();
			props.load(new FileInputStream(filename));
			Enumeration<?> e = props.propertyNames();
			if(e.hasMoreElements()){
			    while(e.hasMoreElements()){
			    	String s = (String)e.nextElement();
			    	props.setProperty(s, props.getProperty(s));
			    }
			}
		    props.setProperty("ftpserver.user.admin.homedirectory", Environment.getExternalStorageDirectory().getAbsolutePath());
		    props.setProperty("ftpserver.user.anonymous.homedirectory",  Environment.getExternalStorageDirectory().getAbsolutePath());
		    props.store(new FileOutputStream(filename), null);
	   }catch (Exception e2) {
		e2.printStackTrace();
	   }

		File files = new File(filename);
		userManagerFactory.setFile(files);
		serverFactory.setUserManager(userManagerFactory.createUserManager());
		// set the port of the listener
		factory.setPort(4050);

		// replace the default listener
		serverFactory.addListener("default", factory.createListener());

		// start the server
		FtpServer server = serverFactory.createServer();
		mFtpServer = server;
		try {
			server.start();
			handler.post(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if(path!=null){
						path.setText("ftp://"+getLocalIpAddress()+":4050");
					}
				}
			});
		} catch (FtpException e) {
			e.printStackTrace();
		}
	}
	
	@Override  
    protected void onDestroy() {  
        super.onDestroy();  
          
        if(null != mFtpServer) {  
            mFtpServer.stop();  
            mFtpServer = null;  
        }  
    }  

}
