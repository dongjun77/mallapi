package com.project.mallapi.config;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = "com.project.mallapi.mongoRepository",
        mongoTemplateRef = "mongodbTemplate"
)
public class MongoConfig {

    @Bean
    public MongoClient mongodbClient() {
        return MongoClients.create("mongodb+srv://malldbuser:malldbuser@malldb.5xfb2n6.mongodb.net/");
    }

    @Bean
    public MongoDatabaseFactory mongodbDatabaseFactory() {
        return new SimpleMongoClientDatabaseFactory(mongodbClient(), "malldb");
    }

    @Bean
    public MongoTemplate mongodbTemplate(MongoDatabaseFactory mongoDatabaseFactory, MappingMongoConverter converter) {
        return new MongoTemplate(mongoDatabaseFactory, converter);
    }

    @Bean
    public MappingMongoConverter mappingMongoConverter(
            MongoDatabaseFactory mongoDatabaseFactory,
            MongoMappingContext mongoMappingContext
    ) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDatabaseFactory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));
        return converter;
    }

//    @Bean
//    public MongoMappingContext mongoMappingContext() {
//        MongoMappingContext context = new MongoMappingContext();
//        context.setAutoIndexCreation(true);  // 인덱스 자동 생성 활성화
//        return context;
//    }

}