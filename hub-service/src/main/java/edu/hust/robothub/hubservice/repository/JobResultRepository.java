package edu.hust.robothub.hubservice.repository;

import edu.hust.robothub.core.result.JobResult;
import edu.hust.robothub.hubservice.model.JobResultModule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobResultRepository extends CrudRepository<JobResultModule,String> {
}
