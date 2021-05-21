cd ../../../../../../..
::-----------------------------------------------------------------------------------------------------------------------------
::  Reducing the risk of credential leak.
::-----------------------------------------------------------------------------------------------------------------------------

start mvn clean &

::-----------------------------------------------------------------------------------------------------------------------------
::#  Pushing the code to Git
::-----------------------------------------------------------------------------------------------------------------------------
set commit_prefix=Android tests: worked on UserRequirement
set quotation_mark="
echo git add .
git add .
echo git commit_end: enter the message to append to %commit_prefix%
set /p commit_end=
git commit -m %quotation_mark% %commit_prefix% %commit_end% %quotation_mark%
echo "git push"
git push
echo Commited and pushed  %quotation_mark%  %commit_prefix% %commit_end%  %quotation_mark%