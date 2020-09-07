package com.tuncay.credit.api;

import com.tuncay.credit.dto.AccountDTO;
import com.tuncay.credit.dto.CreditResultDTO;
import com.tuncay.credit.service.CreditApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/credit")
@Api(value="/credit",tags = "Credit Account APIs")
@Slf4j
public class CreditController {

    @Autowired
    private CreditApplicationService creditApplicationService;

    @PostMapping
    @ApiOperation(value="Make Credit Application By Given Account",response = CreditResultDTO.class)
    public ResponseEntity<CreditResultDTO> makeCreditApplication(@Valid @RequestBody AccountDTO accountDTO){
        log.info("Request geldi Account bilgisi : " + accountDTO.toString());
        return ResponseEntity.ok(creditApplicationService.makeCreditApplicationDTO(accountDTO));
    }

}
