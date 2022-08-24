package com.capstone.medigo.domain.mydata.controller;
import com.capstone.medigo.domain.mydata.controller.dto.MyDataSaveRequest;
import com.capstone.medigo.domain.mydata.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static java.text.MessageFormat.format;

@RestController
@RequestMapping("/api/v1/medicine")
@RequiredArgsConstructor
public class MedicineController {
    private final PrescriptionService prescriptionService;


    @PostMapping
    public ResponseEntity<Integer> savePrescription(
            @Valid @RequestBody MyDataSaveRequest saveRequest
    ) {
        prescriptionService.save(saveRequest);

        URI location = URI.create(format("/api/v1/medicine/{0}", 1));

        return ResponseEntity
                .created(location)
                .body(1);
    }
}
