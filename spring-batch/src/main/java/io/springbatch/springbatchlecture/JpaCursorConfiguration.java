package io.springbatch.springbatchlecture;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class JpaCursorConfiguration {

    private final EntityManagerFactory entityManagerFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final DataSource dataSource;

    private final String JOB_NAME = "JOB-";
    private final String STEP_NAME = "STEP-";
    private final String NUMBERING = "3";

    private int chuckSize = 5;

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
                .<Customer, Customer>chunk(chuckSize)
                .reader(customerItemReader())
                .writer(customerItemWriter())
                .build();
    }

    @Bean(name = "customerItemReaderByJpa")
    public ItemReader<Customer> customerItemReader() {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("firstname", "A%");

        return new JpaCursorItemReaderBuilder<Customer>()
                .name("jpaCursorItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select c from Customer c where firstName like :firstName")
                .parameterValues(parameters)
                .build();
    }

    @Bean(name = "customerItemWriterByJpa")
    public ItemWriter<Customer> customerItemWriter() {
        return items -> {
            for (Customer item : items) {
                System.out.println("item = " + item.toString());
            }
        };
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
