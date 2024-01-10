package application;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.net.URI;
import java.nio.file.Paths;

public class LocalStack {
    public static void main(String[] args) {
        String bucketName = "bucket1";
        String fileName = "SecondFile.rtf";
        String filePath = "/Users/hassaan/AWS S3/"+fileName;
        createBucket(bucketName ,"us-west-2");
        String endPoint = "http://localhost:4566";

        uploadFileToS3(bucketName, fileName, filePath);

        downloadFileFromS3(bucketName, fileName);
    }
    public static void createBucket(String bucketName, String regionName){

        try {
            S3Client s3Client = S3Client.builder().region(Region.of(regionName))
                    .endpointOverride(URI.create("http://localhost:4566"))
                    .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create("hassaan", "Helloworld")))
                    .build();

            CreateBucketRequest createBucketRequest = CreateBucketRequest.builder().bucket(bucketName).build();


            s3Client.createBucket(createBucketRequest);

            System.out.println("The bucket \""+bucketName+"\" has been created successfully.");
        } catch (BucketAlreadyExistsException e){
            System.out.println("The bucket \""+bucketName+"\" already exists.");
        }
        catch (BucketAlreadyOwnedByYouException e){
            System.out.println("The bucket \""+bucketName+"\" is already owned by you.");
        }
        catch (S3Exception e){
            System.err.println("Bucket creation failed: " + e.getMessage());
        }
    }
    public static void uploadFileToS3(String bucketName, String fileName, String filePath){
        try {
            S3Client s3Client = S3Client.builder().region(Region.US_WEST_2)
                    .endpointOverride(URI.create("http://localhost:4566"))
                    .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create("hassaan", "Helloworld")))
                    .build();
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromFile(Paths.get(filePath)));
            System.out.println("The file \""+fileName+"\" has been successfully placed in S3 bucket "+bucketName+".");
        } catch (Exception e){
            System.out.println("Error placing the file on S3 bucket. "+e.getMessage());
        }
    }
    public static void downloadFileFromS3(String bucketName, String fileName){
        try{
            S3Client s3Client = S3Client.builder().region(Region.US_WEST_2)
                    .endpointOverride(URI.create("http://localhost:4566"))
                    .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create("hassaan", "Helloworld")))
                    .build();
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            s3Client.getObject(getObjectRequest);
            System.out.println("The file \""+fileName+"\" has been successfully downloaded from S3 bucket "+bucketName+".");
        }catch (Exception e){
            System.out.println("Error downloading the file from S3 bucket. "+e.getMessage());
        }
    }

}

/*
package application;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.net.URI;
import java.nio.file.Paths;

public class LocalStack {
    public static void main(String[] args) {
        String bucketName = "bucket1";
        String fileName = "SecondFile.rtf";
        String filePath = "/Users/hassaan/AWS S3/" + fileName;
        createBucket(bucketName, "http://localhost:4566");

        uploadFileToS3(bucketName, fileName, filePath);

        downloadFileFromS3(bucketName, fileName);
    }

    public static void createBucket(String bucketName, String localStackEndpoint) {
        try {
            S3Client s3Client = S3Client.builder()
                    .endpointOverride(URI.create(localStackEndpoint))
                    .region(Region.US_WEST_2) // Set your desired region
                    .credentialsProvider(StaticCredentialsProvider.create(
                            AwsBasicCredentials.create("fakeAccessKeyId", "fakeSecretAccessKey")))
                    .build();

            CreateBucketRequest createBucketRequest = CreateBucketRequest.builder().bucket(bucketName).build();

            s3Client.createBucket(createBucketRequest);

            System.out.println("The bucket " + bucketName + " has been created successfully.");
        } catch (S3Exception e) {
            System.err.println("Bucket creation failed: " + e.getMessage());
        }
    }

    public static void uploadFileToS3(String bucketName, String fileName, String filePath) {
        try {
            S3Client s3Client = S3Client.builder()
                    .endpointOverride(URI.create("http://localhost:4566"))
                    .region(Region.US_WEST_2) // Set your desired region
                    .credentialsProvider(StaticCredentialsProvider.create(
                            AwsBasicCredentials.create("fakeAccessKeyId", "fakeSecretAccessKey")))
                    .build();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            s3Client.putObject(putObjectRequest, Paths.get(filePath));
            System.out.println("The file " + fileName + " has been successfully placed in S3 bucket " + bucketName + ".");
        } catch (Exception e) {
            System.out.println("Error placing the file on S3 bucket. " + e.getMessage());
        }
    }

    public static void downloadFileFromS3(String bucketName, String fileName) {
        try {
            S3Client s3Client = S3Client.builder()
                    .endpointOverride(URI.create("http://localhost:4566"))
                    .region(Region.US_WEST_2) // Set your desired region
                    .credentialsProvider(StaticCredentialsProvider.create(
                            AwsBasicCredentials.create("fakeAccessKeyId", "fakeSecretAccessKey")))
                    .build();

            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .build();

            s3Client.getObject(getObjectRequest);
            System.out.println("The file " + fileName + " has been successfully downloaded from S3 bucket " + bucketName + ".");
        } catch (Exception e) {
            System.out.println("Error downloading the file from S3 bucket. " + e.getMessage());
        }
    }
}

 */