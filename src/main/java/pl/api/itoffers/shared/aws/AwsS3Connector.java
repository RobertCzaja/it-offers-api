package pl.api.itoffers.shared.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;

@Service
public class AwsS3Connector {

    @Value("${application.aws.s3.accessKey}")
    private String accessKey;
    @Value("${application.aws.s3.secretKey}")
    private String secretKey;
    @Value("${application.aws.s3.bucket}")
    private String bucket;

    public S3ObjectInputStream download(String fileName) {
        return awsS3ClientBuilder().getObject(bucket, fileName).getObjectContent();
    }

    private AmazonS3 awsS3ClientBuilder() {
        return AmazonS3ClientBuilder
            .standard()
            .withCredentials(
                new AWSStaticCredentialsProvider(
                    new BasicAWSCredentials(accessKey, secretKey)
                )
            )
            .withRegion(String.valueOf(Region.EU_NORTH_1))
            .build();
    }
}