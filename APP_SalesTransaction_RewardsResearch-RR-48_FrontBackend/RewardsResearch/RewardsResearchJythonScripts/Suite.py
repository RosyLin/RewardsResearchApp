from java.io import File
from java.io import FilenameFilter
from java.io import FileFilter
from java.io import FileInputStream
from java.io import IOException
from java.util import Properties
from java.util import ArrayList
from jarray import zeros
from jarray import array
from org.python.core import PyString

import sys
import java

scriptsSucceeded = 0
scriptsFailed = 0
scriptProps = Properties()
removeOnly = 0
createOnly = 0
showHelp = 0
baseDir = java.lang.String("")
fileSep = java.lang.System.getProperty("file.separator")

# return all files that start with "Remove" and end with ".py"
class RemoveFilenameFilter (FilenameFilter):
	def accept(self, dirName, fileName):
		if fileName.startswith("Remove") and fileName.endswith(".py"):
			return 1
		else:
			return 0
			
# return all files that don't start with "Remove", but do end with ".py"	
class NonRemoveFilenameFilter (FilenameFilter):
	def accept(self, dirName, fileName):
		if fileName.find("Remove") == 0:
			return 0
		elif fileName.endswith(".py"):
			return 1
		else:
			return 0
			
# return all sub-directories
class DirectoryFilter (FileFilter):
	def accept(self, fileObj):
		if fileObj.isDirectory() and not java.lang.String(fileObj.getName()).equalsIgnoreCase(".svn"):
			return 1
		else:
			return 0
	
# execute each script in the passed in array of scripts		
def execSubSuite(aBaseDir, aFiles):
	global scriptsSucceeded
	global scriptsFailed
	i = 0
	for i in range(0, len(aFiles)):
		if not aFiles[i] == "Suite.py" :
			print "Executing " + aFiles[i]
			try:
				script = getFileAsString(str(aBaseDir) + fileSep + str(aFiles[i]))
				script = replaceTokens(script)
				exec(PyString(script))
			except (Exception,java.lang.Exception),ex:
				scriptsFailed = scriptsFailed + 1
				print aFiles[i] + " failed: "
				if(failContinue):
					print ex
				else:
					raise ex
			else:
				scriptsSucceeded = scriptsSucceeded + 1
		
# execute the .py scripts in aBaseDir and all of aBaseDir's sub-directories			
def execSuite(aBaseDir):
	global removeOnly
	global creaetOnly
	
	root = File(aBaseDir)
	
	print "Executing Jython Scripts in:\n"
	print root.getAbsoluteFile()
	print "--------------------------------------------------------------"
	a = root.exists()
	# get the lists of files, one list contains only the
	# remove scripts (as these should be run first)		
	removeFiles = root.list(RemoveFilenameFilter())
	nonRemoveFiles = root.list(NonRemoveFilenameFilter())
	subDirectories = root.listFiles(DirectoryFilter())

	# execute the lists of scripts retrieved above
	if(createOnly != 1):
		execSubSuite(aBaseDir, removeFiles)
	if(removeOnly != 1):
		execSubSuite(aBaseDir, nonRemoveFiles)
	
	i = 0
	for i in range(0, len(subDirectories)):
		execSuite(subDirectories[i].getAbsolutePath())
		
# load the properties from was_resources.properties into a Properties object
def loadProperties(aBaseDir):
	try:
		scriptProps.load(FileInputStream(str(aBaseDir) + fileSep + str("was_resources.properties")))
	except (Exception,java.io.IOException),ex:
		print "No properties file found. Not required so continuing."
		
# get the script located at aFilePath as a String
def getFileAsString(aFilePath):
	fileContents = java.lang.String("")
	buffer = zeros(1024, "b") 
	size = 0
	try :
		fis = FileInputStream(aFilePath)
		size = fis.read(buffer)
		while size > 0:
			tmpContents = java.lang.String(buffer, 0, size, "UTF8")
			fileContents = java.lang.String(fileContents.concat(tmpContents))
			size = fis.read(buffer)
		fis.close()
	except (Exception, java.lang.Exception),ex:
		print "Error loading file contents"
		print ex
	return fileContents
	
# replace any tokens in the string scipt with their corresponding values from the
# was_resources.properties file. A token is represented in the child script as
# <<TOKEN_ID>>. This might be something like <<ProductMaint.ProductMaint_GEN_XA.server>>
def replaceTokens(script):
	global scriptProps
	curChar = ""
	prevChar = ""
	curToken = java.lang.String("")
	insideToken = 0
	pos = 0
	tokenList = ArrayList()
	
	scriptLength = script.length()
	while (pos < (scriptLength - 1)):
		prevChar = java.lang.String(curChar)
		curChar = java.lang.String(script.substring(pos, pos + 1))
		
		if(prevChar.equals(">") and curChar.equals(">")):
			tokenList.add(curToken.substring(0,curToken.length() - 1))
			curToken = java.lang.String("")
			insideToken = 0
		
		if(insideToken):
			curToken = java.lang.String(curToken.concat(curChar))
		
		if(prevChar.equals("<") and curChar.equals("<")):
			insideToken = 1
		
			
		
				
		pos = pos + 1
	
	i = 0	
	for i in range(0, tokenList.size()):
		script = java.lang.String(script)
		replaceVal = scriptProps.get(tokenList.get(i))
		if(replaceVal == None):
			print "Unable to find property " + str(tokenList.get(i)) + " in properties file"

		script = script.replaceAll(java.lang.String("<<" + str(tokenList.get(i)) + ">>"), replaceVal)
		
	return script
		
# get the base dir from the list of arguments
# the branching is to support debugging (where the script path is passed as the first arg)
argCounter = 0
failContinue = 1
failContinueStr = "On"
singleScriptMode = 0
singleScript = ""

for argCounter in range(0, len(sys.argv)):
	curArg = java.lang.String(sys.argv[argCounter])

	if(argCounter < len(sys.argv) - 1):
		nextArg = java.lang.String(sys.argv[argCounter + 1])
	else:
		nextArg = java.lang.String("")
		
	if(curArg.equalsIgnoreCase("--P")):
		baseDir = java.lang.String(nextArg)
	
	if(curArg.equalsIgnoreCase("--F")):
		if(nextArg.equalsIgnoreCase("FALSE")):
			failContinue = 0
			failContinueStr = "Off"

	if(curArg.equalsIgnoreCase("--S")):
		singleScriptMode = 1
		singleScript = nextArg
	
	if(curArg.equalsIgnoreCase("--R")):
		removeOnly = 1
	
	if(curArg.equalsIgnoreCase("--C")):
		createOnly = 1
	
	if(curArg.equalsIgnoreCase("--HELP")):
		showHelp = 1
		
# ensure that the path is provided		
if (showHelp == 1):
	print "Usage: Suite.py PATH [--F TRUE/FALSE] [--S SINGLE-SCRIPT] [--R] [--C]"
	print "\t--P PATH is an optional argument, and should be the absolute path to the suite to run. If not provided the current working directory will be used."
	print "\t--F (FAIL-CONTINUE) is an optional argument. It can take a value of true or false to indicate if Suite.py should continue on failure. Default is true."
	print "\t--S (SINGLE-SCRIPT) is an optional argument. It takes the name of a script (located at level PATH) that Suite.py will run alone."
	print "\t--R (REMOVE-ONLY) is an optional argument. It causes only the remove scripts to be run."
	print "\t--C (CREATE-ONLY) is an optional argument. It causes only the create scripts to be run."
	print "\t--HELP (SHOW-HELP) shows this output."
	sys.exit()
	
if(baseDir.equals("")):
	baseDir = java.lang.String(".")
	
print "Continue on fail mode: " + failContinueStr
if(createOnly == 1):
	print "Running only the create scripts"
if(removeOnly == 1):
	print "Running only the remove scripts"
	
loadProperties(baseDir)

if singleScriptMode:
	execSubSuite(baseDir, array([singleScript], java.lang.String))
else:
	execSuite(baseDir)

print "Execution complete(" + str(scriptsSucceeded) + " succeeded " + str(scriptsFailed) + " failed)"
