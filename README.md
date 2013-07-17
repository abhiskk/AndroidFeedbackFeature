* FeedbackLibrary is an Android Library Project.

* SampleApp is an example app which uses FeedbackLibrary.

* To use FeedbackLibrary :
	- Download and import the module FeedbackLibrary in your app. 
	
	- Add the following lines to your AndroidManifest.xml
		- \<uses-permission android:name = "android.permission.INTERNET" />
		- \<uses-permission android:name = "android.permission.READ_LOGS" />
		- \<uses-permission android:name = "android.permission.GET_ACCOUNTS />
		- \<activity
			android:theme = "@android:style/Theme.Holo.Light.Dialog" 
			android:name = "com.example.feedbacktestlib.FeedbackActivity />
		- \<activity 
			android:theme = "@android:style/Theme.Holo.Light.Dialog" 
			android:name = "com.example.feedbacktestlib.LogList />
		- \<activity 
			android:theme = "@android:style/Theme.Holo.Light.Dialog" 
			android:name = "com.example.feedbacktestlib.Preview />
		- \<activity 
			android:theme = "@android:style/Theme.Holo.Light.Dialog" 
			android:name = "com.example.feedbacktestlib.ProcessList" />
		- \<activity
			android:theme = "@android:style/Theme.Holo.Light.Dialog"
			android:name = "com.example.feedbacktestlib.FingerPaint" />
	
	- For taking screenshot use takeScreenshot method of ScreenShot class. Eg
		- Screenshot screenshot = new Screenshot();
		- screenshot.takeScreenShot(yourView,getFilesDir().getAbsolutePath()); 
			//this will save the screenshot of your view in a file

	- For starting the feedback activity do following:
		- Starter starter = new Starter(context, "sendingaccount@gmail.com", "passwordOfSendingAccount" , "receivingAccount1@abc.com,receivingAccount2@abc.com");
			// if there are more than one receiving accounts they should be comma separated
		- starter.start(); 

