package com.youss.demoBatchProcessing.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@Slf4j
public class ShedulerConfig {

    private final JobLauncher jobLauncher;
    private final Job job;

    public ShedulerConfig(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    /*
    Programmtion de l'execution automatique d'un Job. Le cron est defini de tels sorte que
    chaque minute la methode est executée.
     */
    @Scheduled(cron = "0 * * * * *")
    public void sheduleImportCSVToDBJob() {
        //log.info("Demarrage du Job Programmer");
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();
        try {
            jobLauncher.run(job, jobParameters);
        } catch (JobExecutionAlreadyRunningException
                 | JobInstanceAlreadyCompleteException
                 | JobRestartException
                 | JobParametersInvalidException e) {
            e.printStackTrace();
        }
        //log.info("Fin du Job Programmer");
    }
}
