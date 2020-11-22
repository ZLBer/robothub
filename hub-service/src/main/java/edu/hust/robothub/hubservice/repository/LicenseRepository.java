package edu.hust.robothub.hubservice.repository;

import edu.hust.robothub.hubservice.model.License;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Deprecated
@Repository
public interface LicenseRepository extends CrudRepository<License,String>  {
     List<License> findByOrganizationId(String organizationId);
     License findByOrganizationIdAndLicenseId(String organizationId, String licenseId);
}
