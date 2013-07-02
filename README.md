
(README SHOULD BE UPDATED)

* FeedbackTestLib is an Android Library Project .

* Can be used for providing a feedback feature for apps .

* To use it download it and add it as a library to your project . To add as a library go to Project -> properties -> Add library -> browse and select.

* Add the following lines to your AndroidManifest.xml
	- <uses-permission android:name = "android.permission.INTERNET" />
	- <uses-permission android:name = "android.permission.READ_LOGS" />
	- <uses-permission android:name = "android.permission.GET_ACCOUNTS />
	- <activity
		android:theme = "@android:style/Theme.Holo.Light.Dialog" 
		android:name = "com.example.feedbacktestlib.FeedbackActivity />
	- <activity 
		android:theme = "@android:style/Theme.Holo.Light.Dialog" 
		android:name = "com.example.feedbacktestlib.LogList />
	- <activity 
		android:theme = "@android:style/Theme.Holo.Light.Dialog" 
		android:name = "com.example.feedbacktestlib.Preview />
	- <activity 
		android:theme = "@android:style/Theme.Holo.Light.Dialog" 
		android:name = "com.exmaple.feedbacktestlib.ProcessList" />

* For taking screenshot use takeScreenshot method of ScreenShot class. Eg
	- Screenshot screenshot = new Screenshot();
	- screenshot.takeScreenShot(yourView); 
		//this will save the shot of your view in a file

* For starting the feedback activity do following:
	- Starter starter = new Starter(context, "sendingaccount@gmail.com", "passwordOfSendingAccount" , "receivingAccount1@abc.com,receivingAccount2@abc.com");
		// if there are more than one receiving accounts they should be comma separated
	- starter.start(); 

