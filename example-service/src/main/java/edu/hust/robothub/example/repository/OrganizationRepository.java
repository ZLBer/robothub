package edu.hust.robothub.example.repository;

import edu.hust.robothub.example.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization,String>  {
    public Organization findById(String organizationId);
}
