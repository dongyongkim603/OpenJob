package com.JohnHaney.OpenJob.DAO;

import org.springframework.web.multipart.MultipartFile;

import com.JohnHaney.OpenJob.models.PhotoDTO;

public interface IPhotoDAO {

	void savePhotoImage(PhotoDTO photo, MultipartFile imageFile) throws Exception;

	void save(PhotoDTO photo);

}