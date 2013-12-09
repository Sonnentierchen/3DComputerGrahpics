mkdir bin
set path=h:\jogl2\lib;%path% 
set classpath=.;h:\jogl2\jar\jogl-all.jar;h:\jogl2\jar\gluegen-rt.jar;%classpath%
javac -cp ".;/jogl2/jar/gluegen-rt.jar;/jogl2/jar/jogl-all.jar" src/assignment/*.java src/object/*.java src/object/animation/*.java src/object/dynamicobjects/*.java src/object/dynamicobjects/dynamicbarrel/*.java src/object/dynamicobjects/lamp/*.java src/object/modification/*.java src/object/staticobjects/*.java -d bin
java -cp ".;/jogl2/jar/gluegen-rt.jar;/jogl2/jar/jogl-all.jar;bin" assignment.Assignment