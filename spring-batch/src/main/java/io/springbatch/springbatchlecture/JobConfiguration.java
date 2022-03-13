package io.springbatch.springbatchlecture;


import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  private final String JOB_NAME = "JOB-";
  private final String STEP_NAME = "STEP-";
  private final String NUMBERING = "1";

  @Bean(name = JOB_NAME + NUMBERING)
  public Job job() {
    return jobBuilderFactory.get(JOB_NAME + NUMBERING)
            .incrementer(new RunIdIncrementer())
            .start(step1())
            .next(step2())
            .build();
  }

  @Bean(name = STEP_NAME + NUMBERING + "-1")
  public Step step1() {
    return stepBuilderFactory.get(STEP_NAME + NUMBERING + "-1")
            .<String, String>chunk(5)
            .reader(new ListItemReader<>(Arrays.asList("item1", "item2", "item3", "item4", "item5")))
            .processor(new ItemProcessor<String, String>() {
              @Override
              public String process(String s) throws Exception {
                Thread.sleep(300);
                System.out.println("s = " + s);
                return "my" + s;
              }
            })
            .writer(new ItemWriter<String>() {
              @Override
              public void write(List<? extends String> list) throws Exception {
                System.out.println("list3 = " + list);
              }
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
}
