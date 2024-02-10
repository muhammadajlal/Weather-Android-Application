# Weather Android Application

## Description

This is a Java mobile application developed in Android Studio. It uses an API to provide weather information based on the user's location or a specified city.

## Features

1. **User Interface**: The app includes an Edit Text field for entering the name of a city, a "Fetch Weather" button, and a table for displaying weather data.

2. **Retrieve Weather Data**: When the "Fetch Weather" button is pressed, the app hits the URL `https://api.openweathermap.org/data/2.5/weather?q=<city name>,pk&appid=<API KEY>` to retrieve the weather data for the city entered in the Edit Text field. The result of this request is a JSON file.

3. **Display Weather Data**: The app parses the JSON file and displays the following weather data in the table:
    - Overall weather conditions string
    - Current temperature in Celsius
    - Feels like temperature
    - Max temperature
    - Min temperature
    - Humidity
    - Visibility
    - Wind speed
    - Wind direction
    - Sunrise time
    - Sunset time

4. **Getting the User's Location**: This step involves obtaining the user location permissions using the `LocationManager` class and its associated methods. If location permissions are not granted already, the user is prompted to grant the necessary permissions.

5. **Retrieve the Current Location and Weather Information**: With the location permissions obtained, the app retrieves the last known location of the phone. After obtaining the current location of the phone, it uses the latitude and longitude values to query `openWeatherMap.org` for the current weather information.

6. **Display Weather Information in the App**: Once the weather information is retrieved, it is displayed to the user in the Weather app. The Weather app has been modified to use the location information instead of a city name to fetch the weather data.

## Setup

To set up the Weather App on your local machine, follow these steps:

1. **Clone the repository**: Clone the repository to your local machine using the command `git clone <repository-url>`.

2. **Open the project in Android Studio**: Navigate to the directory where you cloned the repository and open the project in Android Studio.

3. **Run the app**: Click on the `Run` button in Android Studio to build and run the app. You can choose to run the app on an emulator or on an Android device connected to your computer.

4. **Grant location permissions**: When you first run the app, it will prompt you to grant location permissions. Click on `Allow` to grant the necessary permissions.

5. **Use the app**: Once the permissions are granted, the app will automatically fetch and display the current weather information based on your location or the city you specify.

## Contributing

Contributions are welcome! Please read the contributing guidelines before getting started.

## License

This project is an open source project and it is free to use for academic purposes. 
