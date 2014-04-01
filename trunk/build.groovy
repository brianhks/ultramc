

import tablesaw.*
import tablesaw.rules.*
import tablesaw.addons.java.*
import tablesaw.addons.ivy.*
import tablesaw.addons.junit.*

println "==============================================================="

version = "0.5"
programName = "ultramc"
url = "https://code.google.com/p/ultramc/"

saw.setProperty(saw.PROP_MULTI_THREAD_OUTPUT, "true")

saw.setProperty(JavaProgram.PROGRAM_NAME_PROPERTY, programName)
saw.setProperty(JavaProgram.PROGRAM_DESCRIPTION_PROPERTY, "Java Memcached client")
saw.setProperty(JavaProgram.SOURCE_DIRECTORY_PROPERTY, "src/java")
saw.setProperty(JavaProgram.TEST_SOURCE_DIRECTORY_PROPERTY, "test/src")
saw.setProperty(JavaProgram.PROGRAM_VERSION_PROPERTY, version)
saw.setProperty(PomRule.GROUP_ID_PROPERTY, "org.agileclick.ultramc")
saw.setProperty(PomRule.URL_PROPERTY, url)
saw.setProperty(PomRule.SCM_URL_PROPERTY, url)
saw.setProperty(PomRule.SCM_CONNECTION_PROPERTY, "scm:svn:"+url)

jp = new JavaProgram().setup()

ivy = new IvyAddon().setup()
//ivy.createPomRule("build/jar/pom.xml")

jp.getTestCompileRule().addDepend(ivy.getResolveRule("test"))

junit = new JUnitRule("test").setDescription("Run JUnit tests")
		.addDepend(jp.getTestCompileRule())
		.addSources(new RegExFileSet("test/src", ".*Test\\.java").recurse().getFilePaths())


pomRule = ivy.createPomRule("build/jar/pom.xml")
		.addDepend(jp.getJarRule())
		.addLicense("LGPL", "http://www.gnu.org/licenses/lgpl.html", "repo")
		.addDeveloper("brianhks", "Brian", "brianhks1+ultramc@gmail.com");

//------------------------------------------------------------------------------
//==-- Maven Artifacts --==
mavenArtifactsRule = new SimpleRule("maven-artifacts").setDescription("Create maven artifacts for maven central")
		.addSource(jp.getJarRule().getTarget())
		.addSource(jp.getJavaDocJarRule().getTarget())
		.addSource(jp.getSourceJarRule().getTarget())
		.addSource("build/jar/pom.xml")
		.setMakeAction("signArtifacts")

void signArtifacts(Rule rule)
{
	for (String source : rule.getSources())
	{
		cmd = "gpg -ab "+source;
		saw.exec(cmd);
	}
}

new JarRule("maven-bundle", "build/bundle.jar").setDescription("Create bundle for uploading to maven central")
		.addDepend(mavenArtifactsRule)
		.addFileSet(new RegExFileSet(saw.getProperty(JavaProgram.JAR_DIRECTORY_PROPERTY), ".*"));

saw.setDefaultTarget("jar")


/*make.createPhonyRule("test", testClassFiles, "depUnitTest");
depunitDefinition = make.getDefinition("depunit");
if (make.getProperty("DEBUG", "off").equals("on"))
	depunitDefinition.setOption("debug");
void depUnitTest(tablesawRule rule)
	{
	depCP = new ClassPath(testCompileClasspath);
	if (make.getProperty("TEST_TAGS") != null)
		depunitDefinition.setOption("testTags", make.getProperty("TEST_TAGS").split(","));
		
	depunitDefinition.setOption("classpath", depCP.toString());
	depunitDefinition.setOption("xmlInput", "test/unittest.xml");
	depunitDefinition.setOption("reportFile", bldoutdir+"/results.xml");
	cmd = depunitDefinition.getCommand("runtest");
	make.exec(cmd);
	}*/
