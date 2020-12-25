# android_cbc_java
 
Initially approached the challenge in Kotlin and then decided a switch to Java was a better use of time.

Created a straightforward MVVM database test here:
app/src/androidTest/java/com/example/android_cbc_java/NewsDatabaseTest.java

I wanted to demonstrate 'browsing offline' as an option because it takes advantage of the MVVM architecture
The application checks for connectivity every 5 seconds and informs the user with a Popup, giving them the option to:

RETRY (after reconnecting to the internet)
or 
Continue Offline

The type filter works with contentpackage and story as those were the only 2 different ones I could see in the cbc-news-feed
