package fr.ippon.kompetitors.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration to access the amazon's services
 */
@Configuration
public class AWSConfig {

    @Bean
    public Regions region() {
        return Regions.EU_WEST_1;
    }

    @Bean
    public AWSCredentialsProvider defaultAWSCredentialsProvider() {
        return new DefaultAWSCredentialsProviderChain();
    }
}
