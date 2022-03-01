package io.springbatch.springbatchlecture;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class ExecutionContextTasklet3 implements Tasklet {

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
    System.out.println("step3 was executed");
    Object name = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().get("name");

    if (name == null) {
      chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("name", "user1");
      throw new RuntimeException("step3 was performed the RuntimeException to intentionally");
    }


//    if (true) new RuntimeException("step3 excepted to Intentionally");

    return RepeatStatus.FINISHED;
  }
}
