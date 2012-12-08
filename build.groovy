

import cpmake.*
import cpmake.rules.*
import cpmake.addons.java.*

println "==============================================================="

version = "0.5";
programName = "ultramc-"+version;

sourceDir = "src/java";

make.setProperty(make.PROP_MULTI_THREAD_OUTPUT, "true");

make.setProperty("cpmake.java.program.source_directory", sourceDir);
jp = new JavaProgram().setProgramName(programName).setup();


make.setDefaultTarget("jar");


/* make.createPhonyRule("test", testClassFiles, "depUnitTest");
depunitDefinition = make.getDefinition("depunit");
if (make.getProperty("DEBUG", "off").equals("on"))
	depunitDefinition.setOption("debug");
void depUnitTest(CPMakeRule rule)
	{
	depCP = new ClassPath(testCompileClasspath);
	if (make.getProperty("TEST_TAGS") != null)
		depunitDefinition.setOption("testTags", make.getProperty("TEST_TAGS").split(","));
		
	depunitDefinition.setOption("classpath", depCP.toString());
	depunitDefinition.setOption("xmlInput", "test/unittest.xml");
	depunitDefinition.setOption("reportFile", bldoutdir+"/results.xml");
	cmd = depunitDefinition.getCommand("runtest");
	make.exec(cmd);
	} */
