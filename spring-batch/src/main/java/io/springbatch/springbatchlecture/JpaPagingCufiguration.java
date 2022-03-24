package io.springbatch.springbatchlecture;

import io.springbatch.springbatchlecture.entity.Customer2;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class JpaPagingCufiguration {

    private final EntityManagerFactory entityManagerFactory;

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final DataSource dataSource;

    private final String JOB_NAME = "JOB-";
    private final String STEP_NAME = "STEP-";
    private final String NUMBERING = "6";

    private int chuckSize = 10;

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
                .<Customer2, Customer2>chunk(chuckSize)
                .reader(customerItemReader())
                .writer(customerItemWriter())
                .build();
    }

    @Bean(name = "customerItemReaderByJpaPaging")
    public ItemReader<Customer2> customerItemReader() {
        return new JpaPagingItemReaderBuilder<Customer2>()
                .name("jpaItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(4)
                .queryString("select c from Customer2 c join fetch c.address")
                .build();
    }

    @Bean(name = "customerItemWriterByJpaPaging")
    public ItemWriter<Customer2> customerItemWriter() {
        System.out.println("### writer! ###");
        return items -> {
            for (Customer2 item : items) {
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
