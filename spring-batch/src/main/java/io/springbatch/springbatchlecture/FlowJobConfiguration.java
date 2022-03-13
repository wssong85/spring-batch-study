package io.springbatch.springbatchlecture;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FlowJobConfiguration {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  private final String JOB_NAME = "JOB-";
  private final String STEP_NAME = "STEP-";
  private final String NUMBERING = "2";

  @Bean(name = JOB_NAME + NUMBERING)
  public Job job() {
    return jobBuilderFactory.get(JOB_NAME + NUMBERING)
            .incrementer(new RunIdIncrementer())
            .start(step1())
            .on("COMPLETED").to(step3())
            .from(step1())
            .on("FAILED").to(step2())
            .end()
            .build();
  }

  @Bean(name = STEP_NAME + NUMBERING + "-1")
  public Step step1() {
    return stepBuilderFactory.get(STEP_NAME + NUMBERING + "-1")
            .tasklet((contribution, chunkContext) -> {
              System.out.println("step1 was executed");
              if (true) throw new RuntimeException("step1 was failed");
              return RepeatStatus.FINISHED;
            })
            .build();
  }

  @Bean(name = STEP_NAME + NUMBERING + "-2")
  public Step step2() {
    return stepBuilderFactory.get(STEP_NAME + NUMBERING + "-2")
            .tasklet((contribution, chunkContext) -> {
              System.out.println("step2 was executed");
              return RepeatStatus.FINISHED;
            })
            .build();
  }

  @Bean(name = STEP_NAME + NUMBERING + "-3")
  public Step step3() {
    return stepBuilderFactory.get(STEP_NAME + NUMBERING + "-3")
            .tasklet((contribution, chunkContext) -> {
              System.out.println("step3 was executed");
              return RepeatStatus.FINISHED;
            })
            .build();
  }
}
