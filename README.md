# Senior Assessment: Section 2 - API

This repository is created to showcase the authors API testing abilities.

The goal of the assessment to automate some endpoints of the spotify API, docs can be found here: 
https://developer.spotify.com/documentation/web-api

This repo consist of two parts:
 - Java Api automation framework
 - Postman Collection
 
# Java Api automation framework

Requirements before run: 
1) The automation needs spotify ClientSecret and ClientID, which you can get after you create an application on spotifies developer portal.
2) The automation uses OAUth2 so you will need to add a redirectUri to the created application. You can do so from your spotify application dashboard
and going to settings. See below image:

![image](https://github.com/RyanKruger1/senior-assesment-api/assets/44663422/d438953f-adbd-4402-8434-718653998122)

3) You will need a user account for Spotify. I.e a spotify username/email and password.

All the above requirements an be provided using the data.json file which can be found at he base of the repo. The detials should be added accordingly. 

After adding all the requirements, to run the automation tests on Windows, follow these steps:

1. Clone the repository to your local machine.
2. Enter all the requirements in the data.json
3. Open a command prompt or PowerShell window.
4. Navigate to the root directory of the cloned repository.
5. Run the following command to execute the tests:

gradlew.bat clean test

To run the automation tests on macOS, follow these steps:

1. Clone the repository to your local machine.
2. Enter all the requirements in the data.json
3. Open a terminal window.
4. Navigate to the root directory of the cloned repository.
5. Run the following command to execute the tests:

./gradlew clean test

# Postman Collection

The postman collection has two files:
1) Environment Variables
Where all the required information is provided for the Postman calls to work. This contains the same data as the data.json
2) The actual collection.

The postman collection uses OAuth2.0 so the client will prompt you to sign in with your spotify credentials. When attempting to authorize.
