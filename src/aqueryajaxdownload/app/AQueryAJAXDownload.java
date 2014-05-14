package aqueryajaxdownload.app;

import java.io.File;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

public class AQueryAJAXDownload extends Activity {
	//String url = "https://picasaweb.google.com/data/feed/base/featured?max-results=8";

	AQuery aq;
	ProgressDialog prgDialog;
	String url = "http://android.programmerguru.com/wp-content/uploads/2014/01/jai_ho.mp3";
	public MediaPlayer mPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aquery_ajaxdownload); 
		
		aq = new AQuery(this);
		aq.id(R.id.btnProgressBar).clicked(this, "async_file"); 
	}
	
	
	public void async_file() {

		File file = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/pgfolder/jai_ho.mp3");
		Log.d("File", file + "");
		File ext = Environment.getExternalStorageDirectory();
		File target = new File(ext, "pgfolder/jai_ho.mp3");

		if (file.exists()) {
			this.playMusic();
			Log.d("Failed exist", "========");
			Toast.makeText(aq.getContext(), "File exist", Toast.LENGTH_SHORT)
					.show();
		} else {
			aq = new AQuery(this);
			aq.id(R.id.btnProgressBar).enabled(false);
			prgDialog = new ProgressDialog(this); // Instantiate Progress Dialog  Bar
			prgDialog.setMessage("Downloading MP3 from Internet. Please wait..."); // Set Progress Dialog Bar message
			prgDialog.setIndeterminate(false);
			prgDialog.setMax(100); // Progress Bar max limit
			prgDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // Progress Bar style
			prgDialog.setCancelable(false); // Progress Bar cannot be cancelable
			// Display progress dialog bar and initiate download of Mp3 file

			aq.progress(prgDialog).download(url, target,
					new AjaxCallback<File>() {

						public void callback(String url, File file,
								AjaxStatus status) {
					

							if (file != null) {
								
								Toast.makeText(aq.getContext(), "Download Complete",
										Toast.LENGTH_SHORT).show();
								playMusic();
								//Log.d("File t", file.length() + ":"+ file + status);
								Log.d("File", "null");
							} else {
							/*	Log.d("File not exist:", file.length() + ":"
										+ file + status);
								Toast.makeText(aq.getContext(),
										"File not exist " + status,
										Toast.LENGTH_SHORT).show();*/
								Log.d("File", "not " +
										"null");
							}
						}

					});
		}

	}
	
	/*public void downLoadSongandPlay(View v) {
		 

		File file = new File(Environment.getExternalStorageDirectory()
				.getPath() + "/pgfolder/jai_ho.mp3");
		File ext = Environment.getExternalStorageDirectory();
		File target = new File(ext, "pgfolder/jai_ho.mp3");

		aq.id(R.id.btnProgressBar).enabled(false);
		if (file.exists()) {
			Toast.makeText(getApplicationContext(),
					"File already exist under SD card, playing Music",
					Toast.LENGTH_LONG).show();
			playMusic();
		} else {
			Toast.makeText(
					getApplicationContext(),
					"File doesn't exist under SD Card, downloading Mp3 from Internet",
					Toast.LENGTH_LONG).show();
			prgDialog = new ProgressDialog(this); // Instantiate Progress Dialog
													// Bar
			prgDialog.setMessage("Downloading MP3 from Internet. Please wait..."); // Set  Progress  Dialog  Bar message
			prgDialog.setIndeterminate(false);
			prgDialog.setMax(100); // Progress Bar max limit
			prgDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL); // Progress Bar style
			prgDialog.setCancelable(false); // Progress Bar cannot be cancelable
			// Display progress dialog bar and initiate download of Mp3 file
			aq.progress(prgDialog).download(url, target, new AjaxCallback<File>() {
						// Once download is complete
						public void callback(String url, File file,AjaxStatus status) {
							
							Log.d("status", status.toString() + "");
							if (file != null) {
								playMusic();
								// If file doesn't exist display error message
							} else {
								Toast.makeText(aq.getContext(),
										"Error occured: Status" + status,
										Toast.LENGTH_SHORT).show();
							}
						}
					});
		}
	}*/

	@SuppressLint("SdCardPath")
	protected void playMusic() {
		// Read Mp3 file present under SD card 
		Toast.makeText(aq.getContext(),
				"Now Playing ",
				Toast.LENGTH_SHORT).show();
		
		Uri myUri1 = Uri.parse("file:///sdcard/pgfolder/jai_ho.mp3");
		mPlayer = new MediaPlayer();
		mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		try {
			mPlayer.setDataSource(aq.getContext(), myUri1);
			mPlayer.prepare();
			// Start playing the Music file
			mPlayer.start();
			mPlayer.setOnCompletionListener(new OnCompletionListener() {
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					// Once Music is completed playing, enable the button
					aq.id(R.id.btnProgressBar).enabled(true);
					Toast.makeText(getApplicationContext(),
							"Music completed playing", Toast.LENGTH_LONG)
							.show();
				}
			});
		} catch (IllegalArgumentException e) {
			Toast.makeText(getApplicationContext(),
					"You might not set the URI correctly!", Toast.LENGTH_LONG)
					.show();
		} catch (SecurityException e) {
			Toast.makeText(getApplicationContext(),
					"URI cannot be accessed, permissed needed",
					Toast.LENGTH_LONG).show();
		} catch (IllegalStateException e) {
			Toast.makeText(getApplicationContext(),
					"Media Player is not in correct state", Toast.LENGTH_LONG)
					.show();
		} catch (IOException e) {
			Toast.makeText(getApplicationContext(), "IO Error occured",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.aquery_ajaxdownload, menu);
		return true;
	}

}
