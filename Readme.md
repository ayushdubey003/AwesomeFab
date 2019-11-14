# Awesome FAB

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://travis-ci.org/joemccann/dillinger)

- A typical FloatingActionButton library with loads of features, built as part of the Build-brake-code-1.0 organized by PCON, NIT Jamshedpur .
- Through the entire library, material dimensions have been followed and that is why, fab size is 56dp and fabminisize is 40dp 

# Requirements

  - The library requires Android API Level 21+.

# Features

> Ripple effect for android L and L+ devices
> Option to use minified FAB
> Option to change FAB background color
> Option to set drawable to FAB
> Option to set up shadow and choose shadow color
> Option to change ripple color
> Option to inflate menu to FAB
> Fully customizable placing of FAB icons and label

# Demo

![Imgur](https://i.imgur.com/LoiedRn.gifv)


# Usage

- Add it in your root `build.gradle` at the end of repositories:
    ```
   allprojects {
          repositories {
    			maven { url 'https://jitpack.io' }
    		}
    }
  ```
- Add this dependency :
   ```
   dependencies {
	       implementation 'com.github.ayushdubey003:AwesomeFab:Tag'
	}
	```

Add the `com.ayush.awesomefab.AwesomeFab` to your layout XML file.


# Floating Action Button
```
<com.ayush.awesomefab.AwesomeFab
        android:id="@+id/fab"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentRight="true"
        android:layout_alignParentBottom="true"
        android:layout_margin="16dp"
        app:afterClickSrc="@drawable/ic_location_off_black_24dp"
        app:alignFabTowards="1"
        app:beforeClickSrc="@drawable/ic_location_on_black_24dp"
        app:fabColor="#fff"
        app:fabMenuAlignment="1"
        app:id="@id/fab"
        app:inflateMenuAt="0"
        app:rippleColor="#00ffaa"></com.ayush.awesomefab.AwesomeFab>
   ```     
   
   # Floating Action Menu
   ```
        mAwesomeFab = findViewById(R.id.fab);
        mAwesomeFab.inflateMenu("Option 1", getDrawable(R.drawable.ic_location_off_black_24dp), mAwesomeFab.getId());
        mAwesomeFab.inflateMenu("Option 2", getDrawable(R.drawable.ic_location_on_black_24dp), mAwesomeFab.getId());
        mAwesomeFab.inflateMenu("Option 3", getDrawable(R.drawable.ic_remove_black_24dp), mAwesomeFab.getId());
        mAwesomeFab.inflateMenu("Option 4", getDrawable(R.drawable.ic_remove_black_24dp), mAwesomeFab.getId());
   ```
