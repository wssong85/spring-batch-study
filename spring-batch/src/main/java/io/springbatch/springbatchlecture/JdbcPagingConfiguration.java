package io.springbatch.springbatchlecture;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class JdbcPagingConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final DataSource dataSource;

    private final String JOB_NAME = "JOB-";
    private final String STEP_NAME = "STEP-";
    private final String NUMBERING = "4";

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
                .<Customer, Customer>chunk(chuckSize)
                .reader(customerItemReader())
                .processor(new ItemProcessor<Customer, Customer>() {
                    @Override
                    public Customer process(Customer customer) throws Exception {
                        System.out.println("### process ###");
                        return customer;
                    }
                })
                .writer(customerItemWriter())
                .build();
    }

    @Bean(name = "customerItemReaderByJdbcPaging")
    public ItemReader<Customer> customerItemReader() {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("firstName", "A%");

        return new JdbcPagingItemReaderBuilder<Customer>()
                .name("jdbcPagingReader")
                .pageSize(2)
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(Customer.class))
                .queryProvider(createQueryProvider())
                .parameterValues(parameters)
                .build();
    }

    @Bean
    public PagingQueryProvider createQueryProvider() {
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource);
        queryProvider.setSelectClause("id, firstName, lastName, birthdate");
        queryProvider.setFromClause("from customer");
        queryProvider.setWhereClause("where firstName like :firstName");

        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("id", Order.ASCENDING);

        queryProvider.setSortKeys(sortKeys);

        PagingQueryProvider object = null;
        try {
            object = queryProvider.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    @Bean(name = "customerItemWriterByJdbcPaging")
    public ItemWriter<Customer> customerItemWriter() {
        System.out.println("### writer ###");
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
