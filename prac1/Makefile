#Make file for Assignment1 of CSC2002S 2014
#COPY RIGHT ogbone Aziozu

JAVAC=javac
JFLAGS=-g

.SUFFIXES:.java .class

#Default Rule

.java.class:
	$(JAVAC) $(JFLAGS) $<
#default rule
DEFAULT: Driver.class CrossCorrelation.class MaxCorrelation.class Correlation.class

#rebuild Driver.class

Driver.class: Driver.java CrossCorrelation.class MaxCorrelation.class

#rebuild Correlation.class which only depends
#on Correlation.java

Correlation.class: Correlation.java

#Make rule to clean the .class files

clean:
	@rm -vf *.class 



