package fr.ippon.kompetitors.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.ippon.kompetitors.web.rest.CompetitorsResource;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.mail.internet.ContentType;
import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.*;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@Service
public class AWSService {

    private AmazonS3 s3client;

    //@Value("${amazon.s3.bucketName}")
    @Value("radar.ippon.raw.ob33ka86qk289jt4")
    private String BUCKET;

    //@Value("${amazon.s3.region}")
    @Value("eu-west-1")
    private String REGION;

    //@Value("${amazon.s3.publicUrlTimeout}")
    @Value("10000")
    private Long PUBLIC_URL_TIMEOUT;

    private static final String DELIMITER = "/";
    private static final String DOT = ".";

    private final Logger log = LoggerFactory.getLogger(CompetitorsResource.class);

    @Autowired
    public AWSService(AmazonS3 s3client) {
        this.s3client = s3client;
    }

    public AWSService(AmazonS3 s3client, String bucket, Long timeout, Long timeoutVA, Integer thumbWidth, Integer thumbHeight, boolean isFFMPEGInstalled) {
        this.s3client = s3client;
        this.BUCKET = bucket;
        this.PUBLIC_URL_TIMEOUT = timeout;
    }



    public Optional<Transfer> downloadObject(String objectKey, String filePath, TransferManager manager) {
        Optional<Transfer> result = Optional.empty();
        try {
            result = Optional.of(manager.download(
                BUCKET, objectKey, new File(filePath)));
            result.get().waitForCompletion();
        } catch (AmazonServiceException | InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }




    public boolean deleteObject(String objectKey) {
        try {
            String bucketVersionStatus = s3client.getBucketVersioningConfiguration(BUCKET).getStatus();
            if (BucketVersioningConfiguration.ENABLED.equals(bucketVersionStatus)) {
                S3Object obj = s3client.getObject(new GetObjectRequest(BUCKET, objectKey));
                s3client.deleteVersion(new DeleteVersionRequest(BUCKET, objectKey, obj.getObjectMetadata().getVersionId()));
            } else {
                s3client.deleteObject(BUCKET, objectKey);
            }
        } catch (SdkClientException e) {

            return false;
        }
        return true;
    }


    private ResponseHeaderOverrides getResponseHeaderOverrides(String name, boolean download) {
        String contentDisposition = "inline";
        if (download) {
            String utf8Name = "";
            try {
                utf8Name = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
            } catch (UnsupportedEncodingException e) {

            }
            contentDisposition = "attachment; filename*=UTF-8''" + utf8Name;
        }
        return new ResponseHeaderOverrides()
            .withContentDisposition(contentDisposition)
            .withContentEncoding("UTF-8");
    }


    public long getObjectTotalSize(String objectKey) {
        long result = 0;
        if (doesObjectExist(objectKey) || doesFolderExist(objectKey)) {
            ListObjectsRequest request = new ListObjectsRequest()
                .withBucketName(BUCKET)
                .withPrefix(objectKey);
            for (S3ObjectSummary summary : s3client.listObjects(request).getObjectSummaries()) {
                result += summary.getSize();
            }
        }
        return result;
    }


    public long getFolderTotalSize(String objectKey) {
        if (!objectKey.endsWith(DELIMITER))
            objectKey += DELIMITER;
        return getObjectTotalSize(objectKey);
    }


    public boolean doesObjectExist(String objectKey) {
        return s3client.doesObjectExist(BUCKET, objectKey);
    }


    public boolean doesFolderExist(String folderKey) {
        boolean result = false;
        if (isDirectory(folderKey)) {
            ListObjectsRequest request = new ListObjectsRequest()
                .withBucketName(BUCKET)
                .withPrefix(folderKey);
            ObjectListing objectListing = s3client.listObjects(request);
            result = objectListing.getObjectSummaries().size() > 0;
        } else {

        }
        return result;
    }

    private List<String> getAllFolderFiles(String folderKey) {
        ListObjectsRequest request = new ListObjectsRequest()
            .withBucketName(BUCKET)
            .withPrefix(folderKey);
        return s3client.listObjects(request).getObjectSummaries()
            .stream()
            .map(S3ObjectSummary::getKey)
            .collect(Collectors.toList());
    }

    private List<S3ObjectSummary> getAllFolderFilesEx(String folderKey) {
        ListObjectsRequest request = new ListObjectsRequest()
            .withBucketName(BUCKET)
            .withPrefix(folderKey);
        return s3client.listObjects(request).getObjectSummaries()
            .stream()
            .collect(Collectors.toList());
    }

    // @Scheduled(initialDelay = 5000, fixedRate = 100000000L)
    public void test() {
        log.debug(" XXXXXXXX  >>>>>   ENTERING TEST   <<<<<<   XXXXXXXXX");
        List<S3ObjectSummary> list = getAllFolderFilesEx("");
        log.debug(" XXXXXXXX " + list.size());
        for ( S3ObjectSummary l : list){
            log.debug("YYY >> " + l);
        }

//        TransferManager transferManager = TransferManagerBuilder.standard().build();
//        try {
//            downloadObject("kompetitor", "C:\\_\\kompetitor", transferManager);
//        } finally {
//            transferManager.shutdownNow();
//        }


//        try {
//            byte[] bytes = FileUtils.readFileToByteArray(new File("C:\\_kompetitor\\event.json"));
//            System.out.println(new String(bytes, "utf-8"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        try {
//            upload(
//                // "kompetitor/event.json",
//                //"event.json",
//                new File("C:\\_kompetitor\\event.json"),
//                "application/json"
//            );
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    private String generateFilePath(String folderKey, String fileName) {
        String result = folderKey + UUID.randomUUID() + DELIMITER + fileName;
        int i = 2;
        String coreName = result;
        String ext = "";
        if (result.contains(DOT)) {
            coreName = result.substring(0, result.lastIndexOf(DOT));
            ext = result.substring(result.lastIndexOf(DOT));
        }
        while (doesObjectExist(result)) {
            result = coreName + " - (" + i++ + ")" + ext;
        }
        return result;
    }

    private boolean isDirectory(String key) {
        return key == null || key.lastIndexOf(DELIMITER) == key.length() - 1;
    }

    private String keyToName(String key) {
        String result = key;
        if (isDirectory(key)) {
            String[] folders = key.split(DELIMITER);
            result = folders[folders.length - 1];
        } else if (key.contains(DELIMITER)) {
            result = key.substring(key.lastIndexOf(DELIMITER) + 1);
        }
        return result;
    }

    // application/json
    public void upload(File file, String contentType) throws IOException {
        ObjectMetadata data = new ObjectMetadata();
        data.setContentType(contentType);
        data.setContentLength(file.length());
        try (FileInputStream inputStream = new FileInputStream(file)) {
            PutObjectResult putObjectResult = s3client.putObject("radar.ippon.raw.ob33ka86qk289jt4", "kompetitor/event.json", inputStream, data);
            System.out.println(putObjectResult);
        }
    }
}
