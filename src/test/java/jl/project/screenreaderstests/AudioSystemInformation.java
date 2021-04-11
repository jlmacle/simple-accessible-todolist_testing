//https://docs.oracle.com/javase/7/docs/technotes/guides/sound/

package jl.project.screenreaderstests;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.Mixer.Info;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 
 * Class designed to discover some concepts related to audio recording
 * With thanks to DannyM
 * https://stackoverflow.com/questions/3705581/java-sound-api-capturing-microphone
 */
public class AudioSystemInformation 
{
	static Logger logger = LoggerFactory.getLogger(AudioSystemInformation.class);
	public static void main(String[] args) {
		
		// https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/AudioSystem.html
		// "This class lets you query and access the mixers that are installed on the system."
		
		// AudioSystem.getMixerInfo()
		// Obtains an array of mixer info objects that represents the set of audiomixers that are currently installed on the system.
		logger.debug("*******   Set of audiomixers that are currently installed on the system.");
		Info[] mixersInfos = AudioSystem.getMixerInfo();
		for(Info mixer_info:mixersInfos)
		{
			logger.debug("Mixer information");
			if (logger.isDebugEnabled()) logger.debug(String.format("Name: %s",mixer_info.getName()));
			if (logger.isDebugEnabled()) logger.debug(String.format("Description: %s",mixer_info.getDescription()));
			if (logger.isDebugEnabled()) logger.debug(String.format("Vendor: %s",mixer_info.getVendor()));
			if (logger.isDebugEnabled()) logger.debug(String.format("Version: %s",mixer_info.getVersion()));
			logger.debug("-----------------------------------------------------");
			
		}
		
		// AudioSystem.getAudioFileTypes()
		// Obtains the file types for which file writing support is provided by the system.
		logger.debug("*******   File types for which file writing support is provided by the system");
		Type[] fileTypesWithFileWritingSupport = AudioSystem.getAudioFileTypes();
		for(Type file_type_with_file_writing_support: fileTypesWithFileWritingSupport)
		{
			if (logger.isDebugEnabled()) logger.debug(String.format("Common file name extension for this type: %s",file_type_with_file_writing_support.getExtension()));
			
		}
		
		
		
		
		
		
	}
	
}
