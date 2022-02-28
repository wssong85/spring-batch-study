package io.springbatch.springbatchlecture;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DBJobConfiguration {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  @Bean
  public Job dbJob() {
    return jobBuilderFactory.get("job")
            .start(dbStep1())
            .next(dbStep2())
            .build();
  }

  @Bean
  public Step dbStep1() {
    return stepBuilderFactory.get("step1")
            .tasklet(new Tasklet() {
              @Override
              public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("db step1");
                return RepeatStatus.FINISHED;
              }
            })
            .build();
  }

  @Bean
  public Step dbStep2() {
    return stepBuilderFactory.get("step2")
            .tasklet(new Tasklet() {
              @Override
              public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("db step2");
                return RepeatStatus.FINISHED;
              }
            })
            .build();
  }

}
