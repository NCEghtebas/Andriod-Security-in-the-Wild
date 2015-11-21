# Android Security Project MEJC

### Introduction
Our project investigated the security and privacy of Android mobile apps. Our first app demonstrated how an app with internet and camera permissions can secretly take photos of the user and send them to our Parse backend. The code for this app can be found in [SecProj2](./SecProj2). The second portion of the project investigated the capabilities of an app without any permissions. The Android code is found in [AndroidSecurityProject](./AndroidSecurityProject).

### Peeping Tom
This app uses the internet and camera permissions to secretly take photos of a user and exfiltrate the photos to a Parse backend.

### No Permission App
This app poses as a game app, but is able to get a list of all of the apps installed on the device and use an HTTP GET with a parameter string to send the data to our malicious server. The [server](./NoPermissionServer) is implemented in Rails and is hosted at [Heroku](http://androidsecurityproject.herokuapp.com/).
