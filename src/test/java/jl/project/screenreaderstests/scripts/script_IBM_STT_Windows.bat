cmd.exe /c ^
"curl -X POST -u apikey:%IBM_SpeechToText_KEY% --header Content-Type:audio/flac --data-binary @src/test/java/jl/project/ScreenReadersTests/audioFiles/%1 %IBM_SpeechToText_URL%/v1/recognize"
