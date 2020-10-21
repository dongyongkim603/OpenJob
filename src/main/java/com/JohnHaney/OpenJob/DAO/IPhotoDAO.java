package com.JohnHaney.OpenJob.DAO;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.JohnHaney.OpenJob.models.Photo;

public interface IPhotoDAO {

	void savePhotoImage(Photo photo, MultipartFile imageFile) throws Exception;

	void save(Photo photo);

}