#!/bin/sh
#cd %~dp0
java LexicalAnalysis $1 
mv *.s output
mv *.dot output
