package com.devlink1.devlink1.Service.CertificationService;

import com.devlink1.devlink1.Entity.Certification;

import java.util.List;

public interface CertificationService {
    List<Certification> getCertifications();
    Certification getCertificationById(Long id);
    Certification addCertification(Certification certification);

}
