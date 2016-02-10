# CustomURLScheme-Android
Playing around app to learn about opening up your app when someone browses to your website. 

So install the app on your device, open up http://curiosityio.com from email, browser, anywhere and your app will launch (well, the dialog will pop up asking you what app to launch and your app will be listed)

**Note: All info obtained from this [official google doc](https://developer.android.com/training/app-links/index.html)**

How to get it working:

* AndroidManifest.xml:  

```xml
        ...
        <activity android:name=".YourActivity">
           ...            
            <intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />
                <data android:scheme="http" android:host="curiosityio.com"/> <!-- here, you can add more such as android:path="" or android:pathPrefix="" to open only certain paths -->
            </intent-filter>
            ...
        </activity>
        ...
```
(The intent filter can be located in the same intent filter as the activity set to launch in the app, or you can use a separate activity. Either works)

This intent filter is what tells Android your activity will open up links to http://curiosityio.com. Here, we are stating we open http://curiosityio.com and that is it. We do not open https://curiosityio.com, www.curiosityio.com, none of those. We need to create multiple <data .../> entries to do that. Create one <data .../> for each different one (one for https, one for www, etc.)

Also, keep in mind here that we are accepting *all* paths for curiosityio.com. If you only want to catch curiosityio.com/about-us and nothing else, add android:pathPrefix, android:path to the <data .../> do catch it.
 
Other things you can add to this intent filter:  
android:launchMode="singleTask". If this is not set, you run the chances of having two instances of your app running if another app launches your app via the custom scheme like we want to do here and if the user manually launches the app later. If you are just showing off a map or sending a tweet, having two instances if not a big deal but other times it may be a big deal especially with using MainActivity that does a lot.
                                                  
android:alwaysRetainTaskState="true". If user launches your app, leaves the app, then opens your app later on manually or from the custom scheme, the state of the app is retained. 

* In your activity that is launched from the intent:  
```java
        final Intent intent = getIntent();
        final String action = intent.getAction();

        // below is how you get the path from the intent filter. so if http://curiosityio.com/about launched this activity, then segments.get(1) should return back "about"....I think....(it might actually return ?user=101 or something. I am not sure.
        if (Intent.ACTION_VIEW.equals(action)) {
            final List<String> segments = intent.getData().getPathSegments();
            if (segments.size() > 1) {
                //mUsername = segments.get(1);
            }
        }
```
This is how you get info on the intent on what URL the user used to get to this app/activity. 

* Cool. Now time to test. Install the app on your device and use this adb command to test that your app launches:  
```
adb shell am start -a android.intent.action.VIEW -c android.intent.category.BROWSABLE -d "http://curiosityio.com"
```
Enter whatever URL you want to test. If your app is shown in the dialog to the user asking what app to launch, you have success. 

* You are really done now to this point. However, there is something optinal that you can also do. Right now, when you launch the intent, the user is approached with many different apps they can launch (browsers mostly come up). If you own this domain and only want your app to launch bypassing this dialog, you can do so. 

This is called "declaring website associations" and it validates the intent filter through your website. Check [this doc](https://developer.android.com/training/app-links/index.html#web-assoc) out for help on this. 