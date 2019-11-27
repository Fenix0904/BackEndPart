package com.site.backend.service;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.*;
import org.springframework.stereotype.Service;

@Service
public class AwsRekognitionService {

    private final AmazonRekognition amazonRekognition;
    private final S3Object s3Object;

    public AwsRekognitionService(AmazonRekognition amazonRekognition, S3Object s3Object) {
        this.amazonRekognition = amazonRekognition;
        this.s3Object = s3Object;
    }

    public void detectModerationLabels(String imageUrl) {
        s3Object.setName(imageUrl.substring(imageUrl.lastIndexOf("/") + 1));
        DetectLabelsRequest request = new DetectLabelsRequest().withImage(new Image().withS3Object(s3Object));
        DetectLabelsResult detectLabelsResult = amazonRekognition.detectLabels(request);
        for (Label label : detectLabelsResult.getLabels()) {
            System.out.println(label);
        }
    }
}
