package com.site.backend.service;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.*;
import com.site.backend.domain.Anime;
import org.springframework.cloud.aws.messaging.config.annotation.NotificationMessage;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("amazon-storage")
public class AwsRekognitionService {

    private final AmazonRekognition amazonRekognition;
    private final S3Object s3Object;

    public AwsRekognitionService(AmazonRekognition amazonRekognition, S3Object s3Object) {
        this.amazonRekognition = amazonRekognition;
        this.s3Object = s3Object;
    }

    @SqsListener("uploads-queue")
    public void detectModerationLabels(@NotificationMessage Anime anime) {
        s3Object.setName(anime.getPoster().substring(anime.getPoster().lastIndexOf("/") + 1));
        DetectLabelsRequest request = new DetectLabelsRequest().withImage(new Image().withS3Object(s3Object));
        DetectLabelsResult detectLabelsResult = amazonRekognition.detectLabels(request);
        for (Label label : detectLabelsResult.getLabels()) {
            System.out.println(label);
        }
    }
}
