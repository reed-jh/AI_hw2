JFLAGS = -g
JC = javac
JVM= java
FILE=
.SUFFIXES: .java .class
.java.class:
    $(JC) $(JFLAGS) $*.java
CLASSES = \
    UI.java \
    Grid.java \
    Cell.java \
    Search.java 

MAIN = Main

default: classes

classes: $(CLASSES:.java=.class)

run: classes
    $(JVM) $(MAIN)

clean:
    $(RM) *.class