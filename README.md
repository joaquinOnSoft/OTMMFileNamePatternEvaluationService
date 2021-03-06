# OTMM Filename Pattern Evaluation Service
Evaluation condition to be used in an OpenText Media Manager (OTMM) workflow. 

This condition will evaluate if the asset filename matches a specific pattern (expressed as a regular expression):

``` 
HB_\d{2}_\d{2}_[a-zA-Z0-9]{1,17}_((\d{2})|(##(|\d{2})))_((4c)|(1c))_((DE)|(AT)|(CH)|(CZ)|(FCH)|(ICH)|(NL)|(SE)|(RO)|(LU)|(SK))[.]((tif)|(tiff)|(TIF)|(TIFF)|(jpg)|(JPG)|(jpeg)|(JPEG)|(eps)|(EPS)|(psd)|(PSD))
``` 

Valid File name examples:

``` 
 HB_22_10_hola_20_4c_DE.jpg
 HB_16_10_hello_##20_1c_AT.jpeg 
``` 

## Generate project .jar file (in Eclipse)
1. Right click on Project folder
2. Click on **Export**
3. Select **JAR file**
4. Click on **Next**
5. Select only **src** folder at **Select the resource to export** list
6. Check **Select class files an resource**
7. Set **Select the export destination: JAR file**: **OTMM-condition-evaluation.jar**
6. Click on **Finish**


## Evaluate
Evaluates a given value. You can specify whether it evaluates an expression, a variable, or a custom implementation class.
A standard Java **EvaluationService interface** is defined to perform evaluation. 

All the **EvaluationService** implementation classes must implement the **evaluate()** method and return the evaluation result. 

All custom implementations must be deployed to Media Management.

## Deploy new custom classes
To deploy new custom classes:
1. Build your custom classes in a JAR file. For example, MyClasses.jar.
2. Copy the JAR file to the <TEAMS_HOME>/plugins directory.
3. Deploy the JAR to OTMM.

### Deploy customizations
> **Important**

> If you add new files or make modifications to existing files in artesia-ejb.jar, your changes are preserved when you upgrade OTMM if you copy these files to the <TEAMS_HOME>/staging/ear.add/ejb.add folder. If you are adding an existing file to the staging folder, prior to copying the file to the staging folder, ensure the customizations are applied to the latest version of the file.

> For example, you can add a new ejb-jar.xml file to the artesia-ejb.jar file make performance adjustments. If you created or modified any configuration files in the artesia-ejb.jar file, copy these files to the <TEAMS_HOME>/staging/ear.add/ejb.add folder. The files and folders in the <TEAMS_HOME>/staging/ear.add/ejb.add folder will be added to or merged into artesiaejb.jar as part of the update installation. For example, if the folder contains a META-INF/ejb-jar.xml file, then META-INF/ejb-jar.xml will be added to artesia-ejb.jar.

#### To deploy customizations on TomEE:

1. Deploy the OTMM customization in the **<TEAMS_HOME>/plugins** directory.
2. At a command prompt or a terminal, navigate to the **<TEAMS_HOME>/install/ant** folder, and then run the following Ant target:

```
ant deploy-customizations
``` 

#### Alternative way to deploy customizations

1. Copy the .jar file to the **<TEAMS_HOME>/ear/artesia/lib** directory.
2. Restart the application server 

On linux:
```
   $ cd <TEAMS_HOME>/bin
   $ ./startup.sh
   $ ./stutdown.sh
``` 

On Windows:
```
   > cd <TEAMS_HOME>\bin
   > .\startup.bat
   > .\stutdown.bat
``` 


## log4j.xml

Log4j is a simple and flexible logging framework. The most common configuration options issuing  log4j.xml

Follow these steps:
1.	Copy these text:

``` 
<!-- Custom added by Joaqu�n -->
			
<logger name="com.opentext.otmm.sc.jobmodeler"  level="DEBUG" additivity="false">
	<AppenderRef ref="CONSOLE" />
	<AppenderRef ref="FILE" />
</logger>
			
<logger name="com.opentext.otmm.sc.jobmodeler.helpers" level="DEBUG" additivity="false">
	<AppenderRef ref="CONSOLE" />
	<AppenderRef ref="FILE" />
</logger>	
		
``` 

2. Paste the paragraph before the **</Loggers>** label into **C:\Apps\TomEE-OTMM\conf\log4j2.xml**


# Required .jar files

> This section is only included to know the original location of the .jar files used in the project.

Import the indicated set of files to the indicated project folders:

**Set 1**
1. From: **C:\Apps\MediaManagement\jars**
 - artesia-server-tools.jar 
 - commons-httpclient-3.1.jar
 - commons-io-2.6.jar 
 - commons-logging-1.2.jar
 - TEAMS-common.jar
 - TEAMS-mock-services.jar
 - TEAMS-sdk.jar
 - TEAMS-toolkit.jar
 
2. To project folder: **lib**

**Set 2**
1. From: **C:\Apps\MediaManagement_TomEE\lib**
 - servlet-api.jar
 
 2. To project folder: **lib**

**Set 3**
1. From: **C:\Apps\MediaManagement\deploy\commons**
 - commons-collections-3.2.2.jar 
 - commons-collections4-4.3.jar 
 - commons-fileupload-1.3.3.jar 
 - commons-lang-2.4.jar 
2. To project folder: **lib**

**Set 4**
1. From: **C:\Apps\MediaManagement\deploy\artesia**
 - otmm-rest-interfaces.jar
 - otmm-server-ext-api.jar
2. To project folder: **lib**


**Set 4**
1. From: **C:\Apps\MediaManagement\ear\artesia.ear**
 - artesia-ejb.jar
2. To project folder: **lib**
