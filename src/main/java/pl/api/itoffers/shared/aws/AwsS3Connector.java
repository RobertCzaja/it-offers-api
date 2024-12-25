package pl.api.itoffers.shared.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.api.itoffers.shared.utils.monitor.MemoryUsage;
import software.amazon.awssdk.regions.Region;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
@Slf4j
@RequiredArgsConstructor
public class AwsS3Connector {

    @Value("${application.aws.s3.accessKey}")
    private String accessKey;
    @Value("${application.aws.s3.secretKey}")
    private String secretKey;
    @Value("${application.aws.s3.bucket}")
    private String bucket;
    private final MemoryUsage memoryUsage;

    public String fetchJson(String fileName) throws IOException {
        log.info(memoryUsage.getLog("before download"));

        S3ObjectInputStream inputStream = null;
        BufferedReader reader = null;

        try {
            inputStream = this.download(fileName);
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();

            log.info(memoryUsage.getLog("after download"));
            int cp;
            while ((cp = reader.read()) != -1) {
                sb.append((char) cp);
            }
            log.info(memoryUsage.getLog("after string builder"));

            log.info(memoryUsage.getLog("after Reader closing"));
            return sb.toString();
        } finally {
            reader.close();
            inputStream.close();
        }
    }

    private S3ObjectInputStream download(String fileName) {
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
