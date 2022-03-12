package io.springbatch.springbatchlecture;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  @Bean
  public Job batchJob1() {
    return jobBuilderFactory.get("batchJob1")
            .start(step1())
            .next(step2())
            .preventRestart()
//            .validator(new CustomJobParametersValidator())
            .validator(new DefaultJobParametersValidator(new String[]{"name", "date"}, new String[]{"count"}))
            .incrementer(new CustomerJobParametersIncrementer())
            .build();
  }

  @Bean
  public Step step1() {
    return stepBuilderFactory.get("step1")
            .tasklet((contribution, chunkContext) -> {
              System.out.println("execute step1");
              return RepeatStatus.FINISHED;
            }).build();
  }

  @Bean
  public Step step2() {
    return stepBuilderFactory.get("step2")
            .tasklet((contribution, chunkContext) -> {
              System.out.println("execute step2");
              return RepeatStatus.FINISHED;
            }).build();
  }
}
