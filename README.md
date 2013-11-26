Umweltzone
==========

This application enables people to look up the areas of cities
which are restricted for being used by cars limited by their
specific emission profile. The app shows the outline of the low-emision
zones and provides additional information about dates or special
regulations such as for vehicles registered abroad. Moreover, the
user can use the FAQs to read about emission groups, fine particles,
badges for cars or motorbikes, punishments, exception permits and
many more. Further, there are several links included
pointing to external websites which serve more information to
read through.


Screenshot
------------------
The map screen of the app shows the outline of the low-emission zone

![Umweltzone][1]


Android versions
----------------
The application is designed to work both on smartphones and on tablets.
Android 2.2 (Froyo) and never versions are supported.


Feedback
--------
Users can leave there feature request on a special website which not
only allows to post own wishes but also to vote on ideas of others.

* [http://umweltzone.uservoice.com][2]


Configuration
---------------
Before the app can be built a bunch of values have to be set.

* Google Maps v2 API key
* TraceDroid email address
* Contact email address

These settings can be found in `/Umweltzone/src/main/res/values/config.xml`.

* Google Analytics tracking id

These settings can be found in `/Umweltzone/src/main/res/values/analytics.xml`.

In order to build a release version of the app signing information must be provided.
Therefore, put the needed information into a `gradle.properties` file. There is a
example included which contains the properties which are referenced in the build configuration.


References
----------
* [Fünfunddreißigste Verordnung zur Durchführung des Bundes-Immissionsschutzgesetzes][3]


License
-------

The source code is licensed under [GPLv3][4].

This content is licensed, if not stated otherwise, under a
[Creative Commons Attribution-ShareAlike 3.0 Unported License][5].

![Creative Commons Attribution-ShareAlike 3.0 Unported License][6]


[1]: http://bitbucket.org/tbsprs/umweltzone/raw/18d887e823828af217156f3b68690445b3ba6129/screenshot.png
[2]: http://umweltzone.uservoice.com
[3]: http://de.wikipedia.org/wiki/Verordnung_zum_Erlass_und_zur_%C3%84nderung_von_Vorschriften_%C3%BCber_die_Kennzeichnung_emissionsarmer_Kraftfahrzeuge#Feinstaubplakette
[4]: http://www.gnu.org/licenses/gpl-3.0.txt
[5]: http://creativecommons.org/licenses/by-sa/3.0/
[6]: http://i.creativecommons.org/l/by-sa/3.0/88x31.png
