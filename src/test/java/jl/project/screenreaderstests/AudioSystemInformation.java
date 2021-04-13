//https://docs.oracle.com/javase/7/docs/technotes/guides/sound/

package jl.project.screenreaderstests;

import javax.sound.sampled.AudioSystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.Mixer.Info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jl.project.StringExternalization;

/**
 * @author 
 * Class designed to discover some concepts related to audio recording
 * With thanks to DannyM
 * https://stackoverflow.com/questions/3705581/java-sound-api-capturing-microphone
 */
public class AudioSystemInformation 
{
	static Logger logger = LoggerFactory.getLogger(AudioSystemInformation.class);
	static String infoFile_URI = System.getProperty("user.dir")+"/src/test/java/jl/project/screenreaderstests/logs/"
			+"AudioSystemInfo.txt";	
	static Path infoFilePath =  Paths.get(infoFile_URI);
	
	public static void main(String[] args) 
	{
		
		// https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/AudioSystem.html
		// "This class lets you query and access the mixers that are installed on the system."
		// AudioSystem.getMixerInfo()
		// Obtains an array of mixer info objects that represents the set of audiomixers that are currently installed on the system.
		try 
		{
			Files.write(infoFilePath, ("*******   Set of audiomixers that are currently installed on the system."+System.lineSeparator()).getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
		
			Info[] mixersInfos = AudioSystem.getMixerInfo();
			for(Info mixer_info:mixersInfos)
			{
				
				appendToFile("Mixer information");
				appendToFile(String.format("Name: %s",mixer_info.getName()));
				appendToFile(String.format("Description: %s",mixer_info.getDescription()));
				appendToFile(String.format("Vendor: %s",mixer_info.getVendor()));
				appendToFile(String.format("Version: %s",mixer_info.getVersion()));
				appendToFile("-----------------------------------------------------");
				
			}
			
			// AudioSystem.getAudioFileTypes()
			// Obtains the file types for which file writing support is provided by the system.
			appendToFile("*******   File types for which file writing support is provided by the system");
			Type[] fileTypesWithFileWritingSupport = AudioSystem.getAudioFileTypes();
			for(Type file_type_with_file_writing_support: fileTypesWithFileWritingSupport)
			{
				appendToFile(String.format("Common file name extension for this type: %s",file_type_with_file_writing_support.getExtension()));
				
			}
		} 
		catch (IOException e) 
		{
			System.err.println(StringExternalization.EXCEPTION_IO);
			e.printStackTrace();
		}		
		
	}
	
	static void appendToFile(String data)
	{
		try 
		{
			Files.write(infoFilePath,(data+System.lineSeparator()).getBytes(),StandardOpenOption.APPEND);
		} 
		catch (IOException e) 
		{
			System.err.println(StringExternalization.EXCEPTION_IO);
			e.printStackTrace();
		}
	}
	
}
