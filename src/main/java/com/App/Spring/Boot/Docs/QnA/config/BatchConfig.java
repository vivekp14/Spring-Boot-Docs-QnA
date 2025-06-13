package com.App.Spring.Boot.Docs.QnA.config;
import com.App.Spring.Boot.Docs.QnA.batch.DocumentBatchProcessor;
import com.App.Spring.Boot.Docs.QnA.batch.DocumentItemReader;
import com.App.Spring.Boot.Docs.QnA.dto.DocumentDTO;
import com.App.Spring.Boot.Docs.QnA.entity.Document;
import com.App.Spring.Boot.Docs.QnA.repository.DocumentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {
    private static final Logger logger = LoggerFactory.getLogger(BatchConfig.class);

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final DocumentRepository documentRepository;

    public BatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                       DocumentRepository documentRepository) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.documentRepository = documentRepository;
    }

    @Bean
    public ItemReader<DocumentDTO> documentItemReader() {
        return new DocumentItemReader();
    }

    @Bean
    public ItemProcessor<DocumentDTO, Document> documentProcessor() {
        return new DocumentBatchProcessor();
    }

    @Bean
    public ItemWriter<Document> documentWriter() {
        RepositoryItemWriter<Document> writer = new RepositoryItemWriter<>();
        writer.setRepository(documentRepository);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step documentProcessingStep() {
        return new StepBuilder("documentProcessingStep", jobRepository)
                .<DocumentDTO, Document>chunk(100, transactionManager)
                .reader(documentItemReader())
                .processor(documentProcessor())
                .writer(documentWriter())
                .build();
    }

    @Bean
    public Job documentIngestionJob() {
        return new JobBuilder("documentIngestionJob", jobRepository)
                .start(documentProcessingStep())
                .build();
    }
}