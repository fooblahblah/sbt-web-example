# sbt-web-example
This plugin and the accompanying sub-project for testing demonstrate an aggressive incremental change detector.

1. To reproduce the behavior, cd into the test project. sbt-web-example-tester
1. Run sbt
1. ~example

Now open sbt-web-example-tester/src/main/assets/js/blah.js. If you add a change, but do *not* save the file, notice how a build is triggered. If you then save another build is triggered. It appears the change detector is picking up some change possibly at the directory level?




