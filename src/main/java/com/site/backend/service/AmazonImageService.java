package com.site.backend.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.site.backend.domain.Anime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
@Profile("amazon-storage")
@Primary
public class AmazonImageService implements ImageService {

    private String bucketName;
    private String endpointUrl;

    private final AmazonS3 s3Client;

    @Autowired
    public AmazonImageService(AmazonS3 s3Client,
                              @Value("${amazonProperties.bucketName}") String bucketName,
                              @Value("${amazonProperties.endpointUrl}") String endpointUrl) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.endpointUrl = endpointUrl;
    }

    @Override
    public void uploadPoster(Anime anime, MultipartFile multipartFile) throws IOException {
        File file = null;
        try {
            file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            String fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            anime.setPoster(fileUrl);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
            if (file != null)
                file.delete();
        }
    }

    @Override
    public boolean deletePoster(Anime anime) {
        String fileName = anime.getPoster().substring(anime.getPoster().lastIndexOf("/") + 1);
        s3Client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        anime.setPoster(null);
        return true;
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile poster) {
        String uuidFileName = UUID.randomUUID().toString();
        return uuidFileName + "." + poster.getOriginalFilename();
    }
}
