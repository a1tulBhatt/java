package com.rackspace.word.count.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class FileService {

	//read file
	public File readFile(String fileName) throws IOException  {
		
		Resource resource =  new ClassPathResource(fileName);
		return resource.getFile();
	}
	
	public Long splitFile(File f) throws FileNotFoundException, IOException {
		
		//count of files in which we have split the master file
		Long fileCount = 0l;
		
		//max size of each splitted file
		int sizeOfFiles = 1024*8 ;
        byte[] buffer = new byte[sizeOfFiles];
        
        //count of files 
       
        String fileName = f.getName();
        
        int counter = 1; 
        try (FileInputStream fileInputStream = new FileInputStream(f);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {

               int numberOfBytesToWrite = 0;
               int startOffset = 0;
               while ((numberOfBytesToWrite = bufferedInputStream.read(buffer)) > 0) {
            	   fileCount = fileCount+1;
                   String filePartName = String.format(fileName+counter, counter++);
                   File newFile = new File(f.getParent(), filePartName);
                   try (FileOutputStream out = new FileOutputStream(newFile)) {
                       out.write(buffer, startOffset, numberOfBytesToWrite);
                   }
               }
           }
        
		return fileCount;
	}
	
}
