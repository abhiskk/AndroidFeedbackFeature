package com.example.feedback_test_3;

public class MakeMessage extends MainActivity {

    private String feedbackBody;

    public MakeMessage(String feedbackBodyValue)
    {
        feedbackBody = feedbackBodyValue;
    }

    public String send() {
        StringBuilder feedbackBodyStringBuilder = new StringBuilder();
        feedbackBodyStringBuilder.append("Sent by : ");

        if(state.contains(StateParameters.sendAsAnonymous))
            feedbackBodyStringBuilder.append("Anonymous");
        else
            feedbackBodyStringBuilder.append(DeviceData.userId);

        feedbackBodyStringBuilder.append("\n\nMessage : ");
        feedbackBodyStringBuilder.append(feedbackBody);

        feedbackBodyStringBuilder.append("\n\nData:\nPackage name : " + DeviceData.packageName +"\nPackage version : "
                + DeviceData.packageVersion + "\nCurrent Date : "+DeviceData.currentDate+"\nDevice : "+DeviceData.device+"\nSdk Version : "+DeviceData.sdkVersion+
                "\nBuild Id : "+DeviceData.buildId+"\nBuild Release : "+DeviceData.buildRelease+"\nBuild Type : "+DeviceData.buildType+"\nBuild fingerprint : "+DeviceData.buildFingerPrint
                +"\nBrand : "+DeviceData.brand+"\nPhone type : "+DeviceData.phoneType+"\nNetwork Type : "+DeviceData.networkType+"\n");

        return feedbackBodyStringBuilder.toString();

    }

}
