package pl.api.itoffers.shared.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;

@Service
public class AWSCloudUtil {

    @Value("${application.aws.s3.accessKey}")
    private String accessKey;
    @Value("${application.aws.s3.secretKey}")
    private String secretKey;

    private AWSCredentials awsCredentials(String accessKey, String secretKey) {
        return new BasicAWSCredentials(accessKey, secretKey);
    }

    private AmazonS3 awsS3ClientBuilder(String accessKey, String secretKey) {
        return AmazonS3ClientBuilder
            .standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCredentials(accessKey, secretKey)))
            .withRegion(String.valueOf(Region.EU_NORTH_1))
            .build();
    }

    public S3ObjectInputStream download(String fileName, String accessKey, String secretKey, String bucket) {
        AmazonS3 s3client = awsS3ClientBuilder(accessKey, secretKey);
        S3Object s3object = s3client.getObject(bucket, fileName);
        return s3object.getObjectContent();
    }
}
