package com.example.BookSpringCURD.Model;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadHelper
{
	public final String UPLOAD_DIR="/home/inferyx/Downloads/MySqlCurd/src/main/resources/static/image";
	
	public boolean uploadFile(MultipartFile multipartfile)
	{
		boolean f=false;
		try
		{
			/*InputStream is=multipartfile.getInputStream();
			byte data[]=new byte[is.available()];
			is.read(data);
			
			//write
			FileOutputStream fos=new FileOutputStream(UPLOAD_DIR+File.separator+multipartfile.getOriginalFilename());
			fos.write(data);
			
			fos.flush();
			fos.close();*/
			
			Files.copy(multipartfile.getInputStream(),Paths.get( UPLOAD_DIR+File.separator+multipartfile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
			
			f=true;
			
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return f;
	}
	



}