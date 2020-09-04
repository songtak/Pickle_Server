package com.pickle.web.file;

import com.pickle.web.commons.Box;
import com.pickle.web.commons.FileHandler;

import com.pickle.web.commons.Path;
import com.pickle.web.notices.Notice;
import com.pickle.web.notices.NoticeRepository;
import com.pickle.web.subjects.Subject;
import com.pickle.web.subjects.teacher.TSubjectRepository;
import com.pickle.web.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.io.File;
import java.util.HashMap;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping("/file")
public class FileController {
    private final FileService fileService;
    private final FileHandler fileHandler;
    private final NoticeRepository noticeRepository;
    private final Box box;
    @Autowired Notice notice;
    @Autowired Subject subject;

    public FileController(FileService fileService, FileHandler fileHandler, UserRepository userRepository, TSubjectRepository tSubjectRepository, NoticeRepository noticeRepository, Box box) {
        this.fileService = fileService;
        this.fileHandler = fileHandler;
        this.noticeRepository = noticeRepository;
        this.box = box;
    }

    @PostMapping("/upload/{articleNo}/{subjectId}")
    public void postFile(@RequestParam("file") MultipartFile file, @PathVariable String articleNo, @PathVariable String subjectId) {
        com.pickle.web.file.File saveFile = new com.pickle.web.file.File();
        String fname = file.getOriginalFilename();
        saveFile.setFileName(fname);
        String extension = fname.substring(fname.lastIndexOf(".") + 1);
        saveFile.setExtension(extension);
        saveFile.setContentType(file.getContentType());
        String uploadFolder = Path.UPLOAD_PATH.toString();
        if(!articleNo.equals("null")){
            notice.setId(Long.parseLong(articleNo));
            saveFile.setNotice(notice);
        }else if(!subjectId.equals("null")) {
            subject.setId(Long.parseLong(subjectId));
            saveFile.setSubject(subject);
        }
        fileHandler.uploadFile(file, uploadFolder);
        fileService.save(saveFile);
    }

    @GetMapping("/list/{fileId}")
    public HashMap<?, ?> findList(@PathVariable Long fileId) {
        box.clear();
        box.put("fileList", fileService.findAll());
        return box.get();
    }
    @GetMapping("/list/subject/{subjectId}")
    public HashMap<?,?> subjectFileList(@PathVariable Long subjectId){
        box.clear();
        box.put("fileList",fileService.findBySubjectId(subjectId));
        System.out.println(fileService.findBySubjectId(subjectId));
        return box.get();
    }

    @GetMapping("/download/{fileId}")
    public StreamingResponseBody getFile(@PathVariable Long fileId, HttpServletResponse response) throws IOException {
        System.out.println("download 요청 "+fileId);
        com.pickle.web.file.File file = fileService.findById(fileId.toString()).get();
        response.setContentType(file.getContentType());
        response.setHeader("Content-Disposition", "attachment; filename=\"webpage." + file.getExtension() + "\"");
        InputStream inputStream = new FileInputStream(new File(Path.UPLOAD_PATH.toString() + "pickle\\" + file.getFileName()));
        return outputStream -> {
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                outputStream.write(data, 0, nRead);
            }
            inputStream.close();
        };
    }

    @GetMapping("/delete/{fileId}")
    public void deleteFile(@PathVariable String fileId){

        fileService.delete(fileId);
        com.pickle.web.file.File file = fileService.findById(fileId.toString()).get();
        System.out.println(file);
        File deleteFile = new File(Path.UPLOAD_PATH.toString()+"pickle\\"+file.getFileName());
            if(deleteFile.exists()){
                deleteFile.delete();
                System.out.println("파일 삭제");
            }else {
                System.out.println("파일이 존재하지 않음");
            }
    }

}


