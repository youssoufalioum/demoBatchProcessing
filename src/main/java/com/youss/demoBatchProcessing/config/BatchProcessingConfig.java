package com.youss.demoBatchProcessing.config;

import com.youss.demoBatchProcessing.entities.Transactions;
import com.youss.demoBatchProcessing.repositories.TransactionsRepository;
import com.youss.demoBatchProcessing.services.TransactionsFieldSetMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchProcessingConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final TransactionsRepository transactionsRepository;

    public BatchProcessingConfig(TransactionsRepository transactionsRepository, JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        this.transactionsRepository = transactionsRepository;
        this.jobRepository = jobRepository;
        this.platformTransactionManager = platformTransactionManager;
    }

    @Bean
    public FlatFileItemReader<Transactions> itemReader() {
        return new FlatFileItemReaderBuilder<Transactions>()
                .resource(new ClassPathResource("transactions.csv"))
                .name("transactionsReader")
                .linesToSkip(1)
                .lineMapper(lineMapper())
                .build();
    }

    @Bean
    public TransactionsProcessor itemProcessor() {
        return new TransactionsProcessor();
    }

    @Bean
    public RepositoryItemWriter<Transactions> itemWriter() {
        RepositoryItemWriter<Transactions> itemWriter = new RepositoryItemWriter<>();
        itemWriter.setRepository(transactionsRepository);
        itemWriter.setMethodName("save");
        return itemWriter;
    }

    @Bean
    public Step importTransactionsDataStep() {
        return new StepBuilder("importTransactionsData", jobRepository)
                .<Transactions, Transactions>chunk(10, platformTransactionManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Job saveTransactions() {
        return new JobBuilder("saveTransactions", jobRepository)
                .start(importTransactionsDataStep())
                .build();
    }

    private LineMapper<Transactions> lineMapper() {
        DefaultLineMapper<Transactions> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("transactionId", "accountNumber", "accountHolderName", "transactionDate", "transactionType", "amount", "transactionStatus");

        //BeanWrapperFieldSetMapper<Transactions> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        //fieldSetMapper.setTargetType(Transactions.class);
        TransactionsFieldSetMapper fieldSetMapper = new TransactionsFieldSetMapper();

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }
}