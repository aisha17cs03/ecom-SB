package com.ecommerce.project.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImplementation implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //Logic to upload image to server


        //File name of current/original image
        String originalFileName=file.getOriginalFilename();
        //Extract file extension from original file name


        //Generate unique file name using current time in milliseconds
        String randomId= UUID.randomUUID().toString();
        //Create the complete file path by combining path, randomId, and file extension
        //mat.jpg--->1234-->1234.jpg
        String fileName=randomId.concat(originalFileName.substring(originalFileName.lastIndexOf(".")));
        //complete file name with path
        String filePath=path + File.separator + fileName;
        //Create a File object for the destination path

        //check if path exists, if not create
        File floder=new File(path);
        if(!floder.exists()) {
            floder.mkdir();
            //create directory if not exists
            //mkdir() method creates the directory
        }
        //Upload the file to the server
        Files.copy(file.getInputStream(), Paths.get(filePath));
        //Files.copy() method copies the file to the destination path

        //Return the file name
        return fileName;
        //return the file name to the caller
    }
}
