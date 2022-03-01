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
public class ExecutionContextConfiguration {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  @Bean
  public Job job() {
    return jobBuilderFactory.get("job-2")
            .start(step1())
            .next(step2())
            .next(step3())
            .next(step4())
            .build();
  }

  @Bean
  public Step step1() {
    return stepBuilderFactory.get("step1")
            .tasklet((contribution, chunkContext) -> {
              System.out.println("step1 is executed");
              return RepeatStatus.FINISHED;
            }).build();
  }

  @Bean
  public Step step2() {
    return stepBuilderFactory.get("step2")
            .tasklet((contribution, chunkContext) -> {
              System.out.println("step2 is executed");
              return RepeatStatus.FINISHED;
            }).build();
  }

  @Bean
  public Step step3() {
    return stepBuilderFactory.get("step3")
            .tasklet((contribution, chunkContext) -> {
              System.out.println("step3 is executed");
              return RepeatStatus.FINISHED;
            }).build();
  }

  @Bean
  public Step step4() {
    return stepBuilderFactory.get("step4")
            .tasklet((contribution, chunkContext) -> {
              System.out.println("step4 is executed");
              return RepeatStatus.FINISHED;
            }).build();
  }
}