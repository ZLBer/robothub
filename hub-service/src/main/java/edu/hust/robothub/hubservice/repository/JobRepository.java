package edu.hust.robothub.hubservice.repository;

import edu.hust.robothub.hubservice.model.JobModule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface JobRepository extends CrudRepository<JobModule,String> {

}
