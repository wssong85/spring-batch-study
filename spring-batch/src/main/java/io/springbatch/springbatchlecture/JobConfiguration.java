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
public class JobConfiguration {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  @Bean
  public Job thirdJob() {
    return jobBuilderFactory.get("thirdJob")
//            .preventRestart()
            .start(thirdStep1())
            .next(thirdStep2())
            .build();
  }

  @Bean
  public Step thirdStep1() {
    return stepBuilderFactory.get("third_step1")
            .tasklet((contribution, chunkContext) -> {
              System.out.println("third app job step1");
              return RepeatStatus.FINISHED;
            }).build();
  }

  @Bean
  public Step thirdStep2() {
    return stepBuilderFactory.get("third_step2")
            .tasklet((contribution, chunkContext) -> {
              System.out.println("third app job step2");
              return RepeatStatus.FINISHED;
            }).build();
  }
}
