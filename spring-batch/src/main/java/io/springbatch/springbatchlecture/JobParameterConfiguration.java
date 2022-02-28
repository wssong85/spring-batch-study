package io.springbatch.springbatchlecture;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobParameterConfiguration {

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
