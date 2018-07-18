# How to use
- Change google web client ID inside strings.xml
    - You can get one inside Firebase authentication (Enable Google as provider and copy the ID).
- Create a GitLab token and add it
- Change AdMob ID inside strings.xml if you want to get real ads.

# GitHub Username
JavierSegoviaCordoba

# AppName
GitLab

# Description
Log in with your GitLab account and check your Projects.

# Intended User
- Developers

# List the main features of your app
- GitLab explorer
- Login and fetch data from the official GitLab API
- Save offline a list of GitLab projects for all of your GitLab accounts.    

# User Interface Mocks
![MainActivity image](https://i.imgur.com/1k5PO6a.png)
<br>Ninjamock link: [https://www.ninjamock.com/s/DL8C9Rx](https://www.ninjamock.com/s/DL8C9Rx "https://www.ninjamock.com/s/DL8C9Rx")

# Key Considerations

## How will your app handle data persistence?
Firebase Realtime Database and SharedPreferences

## Describe any edge or corner cases in the UX.
- NavigationDrawer buttons will open the respective activities.
- Tabs will show the respective fragments.
- Project items will open the respective kind of item (open a Folder or open code file in a webview)      

## Describe any libraries you’ll be using and share your reasoning for including them.
### Glide
- implementation 'com.github.bumptech.glide:glide:4.7.1'
- annotationProcessor 'com.github.bumptech.glide:compiler:4.7.1'
- implementation 'jp.wasabeef:glide-transformations:3.3.0'
### SearchView
- implementation 'com.lapism:searchview:27.1.1.0.0'
### Firebase
- implementation 'com.google.firebase:firebase-database:16.0.1'
- implementation 'com.google.firebase:firebase-core:16.0.1'
- implementation 'com.firebaseui:firebase-ui-auth:3.2.2'
- implementation 'com.google.firebase:firebase-ads:15.0.1'
- implementation 'com.google.firebase:firebase-auth:16.0.2'
- implementation 'com.google.android.gms:play-services-auth:15.0.1'
- implementation 'com.crashlytics.sdk.android:crashlytics:2.9.4'
### Gson
- implementation 'com.google.code.gson:gson:2.8.5'
### Retrofit
- implementation 'com.squareup.retrofit2:retrofit:2.4.0'
- implementation 'com.squareup.retrofit2:converter-gson:2.4.0'
### Highlight View
- implementation 'com.pddstudio:highlightjs-android:1.5.0'
### Markdown View
- implementation 'us.feras.mdv:markdownview:1.1.0'
### Duolingo
- implementation 'com.duolingo.open:rtl-viewpager:1.0.3'

## Describe how you will implement Google Play Services or other external services.
- I will get all the data from the GitLab API. With GSON I can handle the json objects of this API.
- Firebase realtime database to save the API data.
- AdMob to monetize the app with a bottom banner.    
- Crashlytics to get all crash reports from all activities.  

# Next Steps: Required Tasks

## Task 1: Project Setup
- Use Java to create the project with Android Studio.
- Use Android Wizard to create a project with Navigation Drawer activity.    
- Implement the stable version of all dependencies.    
- Create a Retrofit interface with the GitLab API.    
- All the strings are in the strings.xml file    
- The app has support for RTL on all the layouts.   

## Task 2: Implement UI for Each Activity and Fragment

### Build UI these activities:
- MainActivity    
- GoogleSignInActivity  
- AddGitLabAccountActivity  
- ProjectActivity    
- AboutActivity   

### Implement a Settings activity
Settings activity has custom themes:
- App themes
- CodeView themes
    - CodeView themes setting launch a CodeViewActivity to preview the a code text while the user
     selects themes.

### Build UI of these fragments:
- FragmentReadme    
- FragmentFiles    
- FragmentCommits

### The UI of above fragments include create the items layout.

### Build UI for the NavigationDrawer

## Task 3: Get de data from the API
- Use Retrofit to get the JSONs from the API.    
- Use GSON to handle them.   

## Task 4: Save/Read the data to the database
- Use Firebase Realtime Database to save all the data inside a database.  
- Use Firebase Realtime Database to read all the data and fill UI elements. 

## Task 5: Binding the views, showing the info and handling the clicks.
- Use DataViewUtils to bind all the UI elements and show the data.    
- Handle clicks on the RecyclerView items:    
    - Click on a Folder open a folder    
    - Click on a File open a file in a webview or in a chrome custom tab.   

## Task 6: Implement the SearchView
- Use the SearchView library to:   
- Filter the GitLab projects list by project title.    

## Task 7: Implement the Widget
- Create a widget to show starred project    
- The widget has to use a IntentService to fetch and show the data   

## Task 8: Implement Firebase Crashlytics

## Task 9: Implement AdMob
