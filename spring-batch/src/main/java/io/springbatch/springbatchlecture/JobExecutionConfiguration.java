package io.springbatch.springbatchlecture;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class JobExecutionConfiguration {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  @Bean
  public Job Job() {
    return jobBuilderFactory.get("Job")
            .start(Step1())
            .next(Step2())
            .build();
  }

  @Bean
  public Step Step1() {
    return stepBuilderFactory.get("_step1")
            .tasklet((contribution, chunkContext) -> {

              JobParameters jobParameters = contribution.getStepExecution().getJobExecution().getJobParameters();
              String name = jobParameters.getString("name");
              Long seq = jobParameters.getLong("seq");
              Date date = jobParameters.getDate("date");
              Double age = jobParameters.getDouble("age");

              Map<String, Object> parameters = chunkContext.getStepContext().getJobParameters();

              System.out.println("step1 has executed");
              return RepeatStatus.FINISHED;
            }).build();
  }

  @Bean
  public Step Step2() {
    return stepBuilderFactory.get("_step2")
            .tasklet((contribution, chunkContext) -> {
              System.out.println("step2 has executed");
              return RepeatStatus.FINISHED;
            }).build();
  }
}
