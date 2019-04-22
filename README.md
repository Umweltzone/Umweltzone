[![Build Status](https://travis-ci.org/Umweltzone/Umweltzone.svg?branch=master)](https://travis-ci.org/Umweltzone/Umweltzone)
[![GPL license][gpl-license-badge]][gpl-license-link]

# Umweltzone

This application enables people to look up the areas of cities
which are restricted for being used by cars limited by their
specific emission profile. The app shows the outline of the low emission
zones and provides additional information about dates or special
regulations such as for vehicles registered abroad. Moreover, the
user can use the FAQs to read about emission groups, fine particles,
stickers for cars or motorbikes, punishments, exception permits and
many more. Further, there are several links included
pointing to external websites which serve more information to
read through.


[![Available for Android at Google Play][google-play-badge]][google-play-link]


## Screenshot

The map screen of the app shows the outline of the low-emission zone

![Umweltzone][app-screenshot]


## Android versions

The application is designed to work both on smartphones and on tablets.
Android 2.3 (Gingerbread) and newer versions are supported.


## Feedback

Users can leave their feature request on a special website which not
only allows to post own wishes but also to vote on ideas of others.

* [http://umweltzone.uservoice.com][uservoice-link]


## Configuration

Before the app can be built a bunch of values have to be set.

* Google Maps v2 API key

Please make sure to provide two API keys both for the release and the debug build
of the application. The package name of the debug build has the `.debug` suffix
to allow parallel installation on one device.

* TraceDroid email address
* Contact email address

These settings can be found in following places:

* `/Umweltzone/src/debug/res/values/config.xml`
* `/Umweltzone/src/main/res/values/config.xml`
* `/Umweltzone/src/release/res/values/config.xml`

* Google Analytics tracking id

These settings can be found in `/Umweltzone/src/main/res/values/analytics.xml`.

In order to build a release version of the app signing information must be provided.
Therefore, put the needed information into a `gradle.properties` file. There is an
example included which contains the properties which are referenced in the build configuration.


## Testing

Please make sure to run tests before submitting contributions.
Tests can be executed with the following commands.

### Unit tests

``` bash
$ ./gradlew clean test
```

### Integration tests

Before running integrations tests make sure to connect a device or to start an emulator.

``` bash
$ ./gradlew clean connectedAndroidTest
```


## References

* [Fünfunddreißigste Verordnung zur Durchführung des Bundes-Immissionsschutzgesetzes][immissionsschutzgesetz-link]


## Authors

* [Tobias Preuss](https://bitbucket.org/tbsprs)
* [Peter Vasil](https://github.com/ptrv)


## Contributors

* [cketti](https://github.com/cketti)
* [daus-salar](https://bitbucket.com/daus-salar)


## License

The source code is licensed under [GPLv3][gpl-license-link].

This content is licensed, if not stated otherwise, under a
[Creative Commons Attribution-ShareAlike 3.0 Unported License][cc-by-sa-link].

![Creative Commons Attribution-ShareAlike 3.0 Unported License][cc-by-sa-image]


[google-play-badge]: google-play-badge.png
[google-play-link]: https://play.google.com/store/apps/details?id=de.avpptr.umweltzone
[app-screenshot]: screenshot.png
[uservoice-link]: http://umweltzone.uservoice.com
[immissionsschutzgesetz-link]: http://de.wikipedia.org/wiki/Verordnung_zum_Erlass_und_zur_%C3%84nderung_von_Vorschriften_%C3%BCber_die_Kennzeichnung_emissionsarmer_Kraftfahrzeuge#Feinstaubplakette
[gpl-license-link]: http://www.gnu.org/licenses/gpl-3.0.txt
[cc-by-sa-link]: http://creativecommons.org/licenses/by-sa/3.0/
[cc-by-sa-image]: http://i.creativecommons.org/l/by-sa/3.0/88x31.png
[gpl-license-badge]: http://img.shields.io/badge/license-GPL--3.0-lightgrey.svg
