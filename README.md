# Unsecure Bank

This is an intentionally unsecure bank Android app for the SWE 266P course at UCI.

## How to run

Install [Android Studio](https://developer.android.com/studio). Import the project. And then click run on the top right. Before sure to install the required JDK, Android ADK, and emulator if applicable.

## Injected Vulnerabilities

* Insecure data storage. The app data is saved locally by using `SharedPreferences`, which is not encrypted. Anyone with access to the file system or a rooted device and read and modify the app data.
* Exported activities. The activities in the app have `android:exported="true"`, which means the component can be accessed by components in other applications, as well as by the system itself.
* // TODO: Peiming
* // TODO: Harry
* // TODO: Huikun

## Contributors

* Tianyao Chen (tianyc18@uci.edu)
* Peiming Chen (peimingc@uci.edu)
* Harry Wang (zhuoxuw2@uci.edu)
* Huikun Zheng (huikunz1@uci.edu)
