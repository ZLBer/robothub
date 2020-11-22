package edu.hust.robothub.hubservice.repository;

import edu.hust.robothub.hubservice.model.RobotModule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RobotRepository extends CrudRepository<RobotModule,String> {

}
