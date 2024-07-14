package kz.bitlab.portal.minio.minioservice.service;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import kz.bitlab.portal.minio.minioservice.dto.FileDto;
import kz.bitlab.portal.minio.minioservice.mapper.FileMapper;
import kz.bitlab.portal.minio.minioservice.model.UploadFile;
import kz.bitlab.portal.minio.minioservice.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final MinioClient minioClient;
    private final FileRepository fileRepository;
    private final FileMapper fileMapper;

    @Value("${minio.bucket}")
    private String bucket;

    public String uploadFile(MultipartFile file) {

        try {
            UploadFile uploadFile = new UploadFile();
            uploadFile.setOriginalName(file.getOriginalFilename());
            uploadFile.setMimeType(file.getContentType());
            uploadFile.setFileSize(file.getSize());
            uploadFile.setFileDateTimeUpload(LocalDateTime.now());

            uploadFile = fileRepository.save(uploadFile);

            if (uploadFile.getId() != null) {

                String fileName = DigestUtils.sha1Hex(uploadFile.getId() +
                        uploadFile.getFileDateTimeUpload().toString());

                uploadFile.setFileName(fileName);

                minioClient.putObject(
                        PutObjectArgs
                                .builder()
                                .bucket(bucket)
                                .object(fileName)
                                .stream(file.getInputStream(), file.getSize(), -1)
                                .contentType(file.getContentType())
                                .build()
                );

                fileRepository.save(uploadFile);

                return "File Uploaded Successfully!";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Some errors on uploading file";
    }

    public ByteArrayResource downloadFile(String fileName){
        try {
            GetObjectArgs getObjectArgs = GetObjectArgs
                    .builder()
                    .bucket(bucket)
                    .object(fileName)
                    .build();

            InputStream stream = minioClient.getObject(getObjectArgs);
            byte [] byteArray = IOUtils.toByteArray(stream);
            stream.close();

            return new ByteArrayResource(byteArray);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<FileDto> getFileList() {
        return fileMapper.toDtoList(fileRepository.findAll());
    }
}
