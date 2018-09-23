# MarLib
A library project with some tools that I use them on all projects, like alert dialogs, progress bars etc..

How to add this library to your project:

- Gradle

Step 1. Add the JitPack in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.gmarmari:MarLib:v1.0'
	}
  
- Maven

Step 1. Add the JitPack repository to your build file

	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
	
Step 2. Add the dependency

	<dependency>
	    <groupId>com.github.gmarmari</groupId>
	    <artifactId>MarLib</artifactId>
	    <version>v1.0</version>
	</dependency>
  
  https://jitpack.io/#gmarmari/MarLib/v1.0
