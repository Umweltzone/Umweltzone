# Umweltzone changelog



## v.1.2.0

* Published: 2013-02-13

### Bug fixes

* Changelog is displayed correctly now thanks to [cketti][cketti], [a9113](https://bitbucket.org/tbsprs/umweltzone/commits/a91136801ce9748a758bd603206625c2da54c535)
* Fix [HTC bug][htcbug]. This let's us finally publish the application for these devices, too, [81744](https://bitbucket.org/tbsprs/umweltzone/commits/81744dfb42353c9ab39e5316e7fecb3f68c5b1bb)

### Features

* Added new zones for Augsburg, Hagen, Neuss, Ulm and the Ruhr region, [ef60b](https://bitbucket.org/tbsprs/umweltzone/commits/ef60b3a56dfd7bbe804d15264f32e482b6b67884)
* Improve resource loading times, use caching to avoid reparsing raw JSON data [ab545](https://bitbucket.org/tbsprs/umweltzone/commits/ab545646076c49dd51aa92645fc35f2b140788b8)
* Add badge online link to city info, [b0794](https://bitbucket.org/tbsprs/umweltzone/commits/b079478ab873f1c15a5edbe33dd2d8c88a7a616f)
* Append city name to tracking parameter for more detailed analytics, [ace71](https://bitbucket.org/tbsprs/umweltzone/commits/ace710c403c951951a19f680aa823169643a0376)
* Mention OpenStreetMap as a data source, [44e7e](https://bitbucket.org/tbsprs/umweltzone/commits/44e7ee7714bda4f061b0e50ddb6ab513b1c587d2)
* Update build tools to v.19.0.1., [8d63b](https://bitbucket.org/tbsprs/umweltzone/commits/8d63b6686d6dfd767bb4d2c60783d5b73d250491)
* Upgrade Gradle to v.0.7.+ to use Android Studio 0.4.2., [685d0](https://bitbucket.org/tbsprs/umweltzone/commits/685d0a3ef26f174ea50e8a09bf72c959514c245d)


## [v.1.1.0](https://bitbucket.org/tbsprs/umweltzone/commits/tag/v.1.1.0)

* Published: 2013-12-25

### Bug fixes

* Fixed image size and densities for the emission badge, [18e36](https://bitbucket.org/tbsprs/umweltzone/commits/18e364f427fb58fd5c8aafc60b3bc8faa81262bd)
* Fixed image size and densities for the application logo, [bba5b](https://bitbucket.org/tbsprs/umweltzone/commits/bba5ba4c033a0f66b3fa6895e70356f95e60974b)
* Removed endless loop when changing screens between Zoneinfo and FAQs, [e4726](https://bitbucket.org/tbsprs/umweltzone/commits/e4726f597efc53366957d335d2a48d9e1f2f4b92)

### Features

* Added changelog dialog to the application using [ckChangeLog][ckchanglog], [e8963](https://bitbucket.org/tbsprs/umweltzone/commits/e8963288e6ea5517b5bd486a13f8f34df2c4f743)
* Extend error reporting when Google Play Services is not installed on the device, [66054](https://bitbucket.org/tbsprs/umweltzone/commits/6605442fb8c9c303e9088ced7bb9ce6c010f68fa)
* Added new cities: Dusseldorf, Bremen, Leipzig, Hanover and Muenster, [9ef31](https://bitbucket.org/tbsprs/umweltzone/commits/9ef317e36d66f392a54c833ba0baf04864eaab5f)
* Point out missing `gradle.properties` when project is imported, [df740](https://bitbucket.org/tbsprs/umweltzone/commits/df7405b7be011d880b4d95379b22c40c2586e332)
* Set `versionNameSuffix` for convinient look-up while development, [f373f](https://bitbucket.org/tbsprs/umweltzone/commits/f373f1fabc8e382dd6e443e311160295f44884ca)
* Extracted feedback menu into separate action menu item, [92b80](https://bitbucket.org/tbsprs/umweltzone/commits/92b80c0826eeaf366bd61eafd1c8678d7499d38e)
* Disable tracking for debug builds, [9fd4b](https://bitbucket.org/tbsprs/umweltzone/commits/9fd4bdb6f3dfddfb785455be88e3e941816fef9c)
* Tracking for city selection if none has been selected before, [ecb74](https://bitbucket.org/tbsprs/umweltzone/commits/ecb7431f4471231ae7accb1b53b5862524c89b10)


## [v.1.0.0](https://bitbucket.org/tbsprs/umweltzone/commits/tag/v.1.0.0)

* Published: 2013-11-26

### Features

* This is the initial release published in the [Google Play Store][umweltzoneatplaystore].
* We start with the five biggest German cities: Berlin, Frankfurt, Cologne, Munich and Stuttgart.
* The app is however not available for HTC devices yet since there is a [bug][htcbug] which we need to work on.

[umweltzoneatplaystore]: https://play.google.com/store/apps/details?id=de.avpptr.umweltzone
[htcbug]: http://commonsware.com/blog/2012/07/23/linkify-problem-patent-behavior.html
[ckchanglog]: https://github.com/cketti/ckChangeLog
[cketti]: https://github.com/cketti
