@GrabResolver(name='apache', root='http://repository.apache.org/snapshots/')
@Grab('commons-configuration:commons-configuration:2.0-SNAPSHOT')
import static groovy.io.FileType.FILES
import org.apache.commons.configuration.*

def oldPassword
def newPassword
def firstParam = args[0].split('=')
def secondParam = args[1].split('=')
if(firstParam[0] =='oldPwd'){
    oldPassword = firstParam[1]
}else if(secondParam[0] =='oldPwd') {
    oldPassword = secondParam[1]
}

if(firstParam[0] =='newPwd'){
    newPassword = firstParam[1]
}else if(secondParam[0] =='newPwd') {
    newPassword = secondParam[1]
}

def filePaths = '../../../src'
println "You entered: $oldPassword $newPassword $filePaths"

filePaths.split(",").each { filePath ->
    new File(filePath).eachFileRecurse(FILES) {
        if(it.name.endsWith('.properties')) {
            println it
            Properties props = new Properties()
            props.load(it.newDataInputStream())
            Configuration config = new PropertiesConfiguration();
            PropertiesConfigurationLayout propertiesConfigurationLayout = new PropertiesConfigurationLayout();
            propertiesConfigurationLayout.load(config, new FileReader(it));
            props.each { line ->
                def string = line.value.toString()
                if (string.startsWith("ENC(") && string.endsWith(")")) {
                    def originalEncVal = string.substring(4, string.size() - 1)
                    def output = "java -classpath ./../lib/jasypt-1.9.2.jar org.jasypt.intf.cli.JasyptPBEStringDecryptionCLI input=$originalEncVal password=$oldPassword".execute().text
                    def decryptedVal = output.readLines().get(16)
                    println decryptedVal
                    output = "java -classpath ./../lib/jasypt-1.9.2.jar org.jasypt.intf.cli.JasyptPBEStringEncryptionCLI input=$decryptedVal password=$newPassword".execute().text
                    def newEncVal = output.readLines().get(16)
                    println newEncVal
                    config.setProperty(line.key.toString(), "ENC($newEncVal)")
                }
            }
            propertiesConfigurationLayout.save(config, new FileWriter(it));
        }
    }
}

