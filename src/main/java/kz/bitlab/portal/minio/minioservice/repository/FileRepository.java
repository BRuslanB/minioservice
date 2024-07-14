package kz.bitlab.portal.minio.minioservice.repository;

import jakarta.transaction.Transactional;
import kz.bitlab.portal.minio.minioservice.model.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface FileRepository extends JpaRepository<UploadFile, Long> {

}
