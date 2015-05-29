# [Eclipse Summit 2010 Tutorial](http://www.eclipsecon.org/summiteurope2010/sessions/?page=sessions&id=1833) #

As in many modern platforms, programming Android apps requires a mixture of several languages. For example, an Android Activity has a declarative XML part and a behavioral Java part. To make Activity a cross-language abstraction, you usually use an Android-specific IDE that ensures that both parts are in sync, yet such an IDE will usually not allow you to introduce your own abstractions.

A solution to this problem is to define your abstractions in a separate language and compile that language to Android-specific artifacts. While that sounds rather complicated, the Eclipse Modeling Project offers a plethora of frameworks and tools that make it easily possible.

In this tutorial, you will create working Android applications using Eclipse modeling techniques. This talk will start with a short presentation of Android and its development tools. Then you will learn how to define your own abstractions using EMF Ecore and how to create a language and an editor for these with Xtext. Finally, we will implement a generator that creates the Java and XML source code of the Android application with the help of Acceleo. We will demonstrate how these ingredients integrate into the Eclipse workbench just like Java and XML do.


# Tutorial Plan #

  * Introduction to Android (15min)
  * The Demo Application (20min)
  * Creation of a Programming Language With Xtext (1h35)
  * Break (20min)
  * Creation of a code generator for Android with Acceleo (1h35)

# Links #

  * Acceleo: http://www.eclipse.org/acceleo
  * EMF: http://www.eclipse.org/modeling/emf/
  * Xtext: http://www.eclipse.org/Xtext