package com.devlink1.devlink1.Service.CertificationService;

import com.devlink1.devlink1.Entity.Certification;
import com.devlink1.devlink1.Repository.CertificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificationServiceImpl implements CertificationService {

    private final CertificationRepository certificationRepository;

    public CertificationServiceImpl(CertificationRepository certificationRepository) {
        this.certificationRepository = certificationRepository;
    }


    @Override
    public List<Certification> getCertifications() {
        return certificationRepository.findAll();
    }

    @Override
    public Certification getCertificationById(Long id) {
        return certificationRepository.findById(id).orElse(null);
    }

    @Override
    public Certification addCertification(Certification certification) {
        return certificationRepository.save(certification);
    }
}
