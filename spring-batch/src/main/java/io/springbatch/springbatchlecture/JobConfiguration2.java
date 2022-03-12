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
import java.util.Locale;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration2 {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  @Bean
  public Job batchJob2() {
    return jobBuilderFactory.get("batchJob2")
            .incrementer(new RunIdIncrementer())
//            .start(taskStep2())
            .start(checkStep2())
            .build();
  }

  @Bean
  public Step taskStep2() {
    return stepBuilderFactory.get("taskStep")
            .tasklet((contribution, chunkContext) -> {
              System.out.println("step was executed");
              return RepeatStatus.FINISHED;
            }).build();
  }

  @Bean
  public Step checkStep2() {
    return stepBuilderFactory.get("checkStep")
            .<String, String>chunk(10)
            .reader(new ListItemReader<>(Arrays.asList("item1", "item2", "item3", "item4", "item5")))
            .processor(new ItemProcessor<String, String>() {
              @Override
              public String process(String s) throws Exception {
                return s.toLowerCase();
              }
            })
            .writer(new ItemWriter<String>() {
              @Override
              public void write(List<? extends String> list) throws Exception {
                list.forEach(item -> System.out.println("item = " + item));
              }
            })
            .build();
  }
}
