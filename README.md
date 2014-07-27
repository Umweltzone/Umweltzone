Umweltzone
==========

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


[![Available for Android at Google Play][1]][2]


Screenshot
------------------
The map screen of the app shows the outline of the low-emission zone

![Umweltzone][3]


Android versions
----------------
The application is designed to work both on smartphones and on tablets.
Android 2.2 (Froyo) and never versions are supported.


Feedback
--------
Users can leave there feature request on a special website which not
only allows to post own wishes but also to vote on ideas of others.

* [http://umweltzone.uservoice.com][4]


Configuration
---------------
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
Therefore, put the needed information into a `gradle.properties` file. There is a
example included which contains the properties which are referenced in the build configuration.


References
----------
* [Fünfunddreißigste Verordnung zur Durchführung des Bundes-Immissionsschutzgesetzes][5]


Authors
-------

* [Tobias Preuss](https://bitbucket.org/tbsprs)
* [Peter Vasil](https://github.com/ptrv)


Contributors
------------

* [cketti](https://github.com/cketti)
* [daus-salar](https://bitbucket.com/daus-salar)


License
-------

The source code is licensed under [GPLv3][6].

This content is licensed, if not stated otherwise, under a
[Creative Commons Attribution-ShareAlike 3.0 Unported License][7].

![Creative Commons Attribution-ShareAlike 3.0 Unported License][8]


[1]: http://bitbucket.org/tbsprs/umweltzone/raw/9ae472a8cc7ad0ef09179e57ca911a46ecd8abb8/google-play-badge.png
[2]: https://play.google.com/store/apps/details?id=de.avpptr.umweltzone
[3]: http://bitbucket.org/tbsprs/umweltzone/raw/18d887e823828af217156f3b68690445b3ba6129/screenshot.png
[4]: http://umweltzone.uservoice.com
[5]: http://de.wikipedia.org/wiki/Verordnung_zum_Erlass_und_zur_%C3%84nderung_von_Vorschriften_%C3%BCber_die_Kennzeichnung_emissionsarmer_Kraftfahrzeuge#Feinstaubplakette
[6]: http://www.gnu.org/licenses/gpl-3.0.txt
[7]: http://creativecommons.org/licenses/by-sa/3.0/
[8]: http://i.creativecommons.org/l/by-sa/3.0/88x31.png
