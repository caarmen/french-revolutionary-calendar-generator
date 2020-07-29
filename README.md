French Revolutionary Calendar Generator
=======================================

This project generates an ICS calendar file containing events for one full Gregorian year. Each event contains:
* A title with the date and object of the day. Example:
    ```
     12 Thermidor 228 : Salicorne
    ```
* A description which adds the day of the week and the type of object. Example:
    ```
    Duodi, 12 Thermidor 228
    La plante du jour : Salicorne
    ```


Building
---------
Build the project with `./gradlew build`

Usage
------
Run the project with the `--help` option to see the available parameters. All parameters are optional.

The calendar file is dumped to standard out.

```
java -jar ./build/libs/french-revolutionary-calendar-generator-1.0-SNAPSHOT.jar --help
Usage: frcg options_list
Options: 
    --year, -y [2020] -> Gregorian year for which to generate the calendar { Int }
    --event-template, -et [src/main/resources/event.ics.template] -> Path to event ics template file { String }
    --calendar-template, -ct [src/main/resources/calendar.ics.template] -> Path to calendar ics template file { String }
    --language, -l [fr] { Value should be one of [ca, de, en, es, eu, fr, it] }
    --method, -m [romme] { Value should be one of [equinox, romme, von_madler] }
    --help, -h -> Usage info 
```

Download generated calendar files
---------------------------------
Generated ics files are available for download in the [releases](https://github.com/caarmen/french-revolutionary-calendar-generator/releases) page on github.

⚠️ Warning ⚠️
-------------
The generated calendar file contains hundreds of events (365 or 366 depending on the year). Take care when importing the calendar file into your calendar software. It is recommended to **import it into a new calendar, and not an existing one**. This way, if you're not satisfied with the result, or decide you don't want to use it after a while, it will be much easier to delete the entire new calendar than to delete hundreds of events one by one from your existing calendar.
