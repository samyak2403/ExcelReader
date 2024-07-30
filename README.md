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
    [git clone https://github.com/yourusername/excel-reader-app.git](https://github.com/samyak2403/ExcelReader.git)
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

## AdMob Integration

1. **Banner Ads**
    - Implemented at the bottom of the screen in `MainActivity.java`:
        ```java
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        ```

2. **Interstitial Ads**
    - Loaded and displayed at specific points in the app:
        ```java
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("YOUR_INTERSTITIAL_AD_UNIT_ID");
        interstitialAd.loadAd(new AdRequest.Builder().build());

        // Show the ad at an appropriate point
        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
        ```

## Dependencies

- **Apache POI** for reading Excel files:
    ```gradle
    implementation 'org.apache.poi:poi:5.2.2'
    implementation 'org.apache.poi:poi-ooxml:5.2.2'
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


 
