package com.ssafy.myini.initializer.controller;

import com.ssafy.myini.initializer.request.InitializerRequest;
import com.ssafy.myini.initializer.response.InitializerPossibleResponse;
import com.ssafy.myini.initializer.response.PreviewResponse;
import com.ssafy.myini.initializer.service.InitializerService;
import lombok.RequiredArgsConstructor;
import net.lingala.zip4j.ZipFile;
import org.json.simple.JSONObject;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RequestMapping("/api/initializers")
@RestController
@RequiredArgsConstructor
public class InitializerController {
    private final InitializerService initializerService;

    //이니셜라이저 가능한지 확인
    @GetMapping("/{projectid}/ispossible")
    public ResponseEntity<InitializerPossibleResponse> initializerIsPossible(@PathVariable("projectid") Long projectId) {
        InitializerPossibleResponse body = initializerService.initializerIsPossible(projectId);

        return ResponseEntity.ok().body(body);
    }

    //이니셜라이징 시작
    @PostMapping("/{projectid}")
    public ResponseEntity<InputStreamResource> initializerStart(@PathVariable("projectid") Long projectId,
                                                                @Valid InitializerRequest initializerRequest
    ) throws IOException {
        ZipFile body = initializerService.initializerStart(projectId, initializerRequest);

        InputStreamResource resource3 = new InputStreamResource(new FileInputStream(body.getFile()));

        HttpHeaders header = new HttpHeaders();

        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + body.getFile().getName());
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        return ResponseEntity.ok()
                .headers(header)
                .contentLength(body.getFile().length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource3);
    }

    //이니셜라이징 미리보기
    @PostMapping("/{projectid}/previews")
    public ResponseEntity<List<PreviewResponse>> initializerPreview(@PathVariable("projectid") Long projectId,
                                                                    @RequestBody InitializerRequest initializerRequest) {
        List<PreviewResponse> body = initializerService.initializerPreview(projectId, initializerRequest);

        return ResponseEntity.ok().body(body);
    }

    //myini 다운로드
    @GetMapping("/downloads")
    public ResponseEntity<byte[]> myIniDownload() {
        ByteArrayOutputStream byteArrayOutputStream = initializerService.myIniDownload();

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM) //.APPLICATION_OCTET_STREAM
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "front Setup 0.1.0.exe" + "\"")
                .body(byteArrayOutputStream.toByteArray());
    }

    //이니셜라이징 세팅값 조회
    @GetMapping("/settings")
    public ResponseEntity<JSONObject> initializerSettings() {
        JSONObject body = initializerService.initializerSettings();
        return ResponseEntity.ok(body);
    }
}
