#!/bin/bash
curl -X POST -u "apikey:"$IBM_SpeechToText_KEY --header "Content-Type:audio/flac" --data-binary @src/test/java/jl/project/ScreenReadersTests/audioFiles/audio-file.flac $IBM_SpeechToText_URL"/v1/recognize"
