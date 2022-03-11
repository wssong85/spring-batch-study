package io.springbatch.springbatchlecture;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//@Component
//@RequiredArgsConstructor
public class JobRunner implements ApplicationRunner {

  private final JobLauncher jobLauncher;

  private final Job job;

  public JobRunner(JobLauncher jobLauncher, @Qualifier("thirdJob") Job thirdJob) {
    this.jobLauncher = jobLauncher;
    this.job = thirdJob;
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {

    JobParameters jobParameters = new JobParametersBuilder()
//            .addString("requestDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
            .addString("requestDate", LocalDateTime.now().toString())
            .toJobParameters();

    jobLauncher.run(job, jobParameters);
  }
}
