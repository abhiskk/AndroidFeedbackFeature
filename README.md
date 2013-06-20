
* FeedbackTestLib is an Android Library Project .

* Can be used for providing a feedback feature for apps .

* To use it download it and add it as a library to your project . To add as a library go to Project -> properties -> Add library -> browse and select.

* Add the following lines to your androidmanifest.xml
	- < uses-permission android:name = "android.permission.INTERNET" / >
	- < uses-permission android:name = "android.permission.READ_LOGS" / >
	- < activity android:name = "com.example.feedbacktestlib.FeedbackActivity / >
	- < activity android:name = "com.example.feedbacktestlib.LogList / >
	- < activity android:name = "com.example.feedbacktestlib.MakeMessage" / >
	- < activity android:name = "com.example.feedbacktestlib.Preview / >
	- < activity android:name = "com.exmaple.feedbacktestlib.ProcessList" / >

* For starting the feedback activity create an intent and put the location of the screenshot image file in the intent and start the activity. Eg
	- Intent feedbackIntent = new Intent( this , FeedbackActivity.class ) ;
	- feedbackIntent.putExtra ( "screentshotFilePath" , "nameOfFileToBeAttachedToMail.jpeg" ) ;
	- startActivity ( feedbackIntent );
  
