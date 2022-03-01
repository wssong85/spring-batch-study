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
public class JobStepConfiguration {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  @Bean
  public Job testJob() {
    return jobBuilderFactory.get("sviu2Job")
            .start(Step1())
            .next(Step2())
            .build();
  }

  @Bean
  public Step Step1() {
    return stepBuilderFactory.get("step1")
            .tasklet(new CustomTasklet()).build();
  }

  @Bean
  public Step Step2() {
    return stepBuilderFactory.get("step2")
            .tasklet((contribution, chunkContext) -> {
              System.out.println("step2 is executed");
              return RepeatStatus.FINISHED;
            }).build();
  }
}
