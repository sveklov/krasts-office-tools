cd modules/group-journal
cp src/test/resources/B-C.html target
xcopy "../group-journal-templates/B-C" "target/B-C" /I
cd target
java -jar group-journal-1.0-SNAPSHOT-jar-with-dependencies.jar