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


Building:
---------
Build the project with `./gradlew build`

Usage:
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
