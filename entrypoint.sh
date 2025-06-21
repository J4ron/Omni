#!/bin/sh

# Build immer ausführen
./gradlew build

if [ "$1" = "test" ]; then
  ./gradlew test --info --console=plain
else
  java -jar build/libs/omni-*.jar "$@"
fi
