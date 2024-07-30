# ExcelReader

# Excel Reader App

## Overview
The Excel Reader App is an Android application designed to read and display Excel files (.xls and .xlsx). This app is integrated with AdMob to display ads, helping to monetize the application.

## Features
- Open and read Excel files from device storage.
- Display data in a user-friendly format.
- Support for both .xls and .xlsx file formats.
- AdMob integration for displaying banner and interstitial ads.

## Screenshots
![Screenshot 1](screenshots/screenshot1.png)
![Screenshot 2](screenshots/screenshot2.png)

## Installation

1. **Clone the repository**
    ```sh
    https://github.com/samyak2403/ExcelReader.git
    cd excel-reader-app
    ```

2. **Open the project in Android Studio**
    - Launch Android Studio and open the project folder.

3. **Setup AdMob**
    - Add your AdMob App ID in the `AndroidManifest.xml` file:
        ```xml
        <meta-data
            android:name="com.google.android.gms.ads.APPLICATION_ID"
            android:value="YOUR_ADMOB_APP_ID"/>
        ```

4. **Build and Run**
    - Connect your Android device or use an emulator.
    - Build and run the app from Android Studio.

## Usage

1. **Open the App**
    - Launch the Excel Reader App from your device.

2. **Load Excel File**
    - Click on the "Open File" button.
    - Navigate to the desired Excel file and select it.

3. **View Data**
    - The app will display the data in a readable format.

4. **Ads**
    - The app will display banner ads at the bottom of the screen.
    - Interstitial ads will appear at appropriate intervals to enhance user experience and app monetization.


## Dependencies

- **Apache POI** for reading Excel files:
    ```gradle

    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.11.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'org.jetbrains:annotations:23.0.0'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'

    implementation 'com.airbnb.android:lottie:6.3.0'
    implementation 'com.karumi:dexter:6.2.3'
    //ADs
    implementation 'com.google.android.gms:play-services-ads:22.6.0'
    implementation files('libs/lib_office.jar')

    implementation 'com.github.ybq:Android-SpinKit:1.4.0'
    ```
- **AdMob SDK** for ads integration:
    ```gradle
    implementation 'com.google.android.gms:play-services-ads:22.1.0'
    ```

## Contributing

Contributions are welcome! Please fork this repository and submit a pull request with your changes.

1. Fork the repository.
2. Create a new branch: `git checkout -b feature-branch-name`
3. Make your changes and commit them: `git commit -m 'Add some feature'`
4. Push to the branch: `git push origin feature-branch-name`
5. Open a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

For any questions or support, please open an issue or contact the maintainer at [your-email@example.com].


 
