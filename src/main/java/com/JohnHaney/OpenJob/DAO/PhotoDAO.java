package com.JohnHaney.OpenJob.DAO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.JohnHaney.OpenJob.models.Photo;

@Component
public class PhotoDAO implements IPhotoDAO {
	
	@Autowired
	private PhotoRepoIF photoRepo;

	@Override
	public void save(Photo photo) {
		photoRepo.save(photo);
	}

	@Override
	public void savePhotoImage(Photo photo, MultipartFile imageFile) throws Exception {
		// this gets us to src/main/resources without knowing the full path (hardcoding)
		Path currentPath = Paths.get(".");
		Path absolutePath = currentPath.toAbsolutePath();
		photo.setPath(absolutePath + "/src/main/resources/static/photos/");
		byte[] bytes = imageFile.getBytes();
		Path path = Paths.get(photo.getPath() + imageFile.getOriginalFilename());
		Files.write(path, bytes);
	}
	
}
