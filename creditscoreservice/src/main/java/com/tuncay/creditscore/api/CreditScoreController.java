package com.tuncay.creditscore.api;

import com.tuncay.creditscore.entity.CreditScoreResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/creditScore")
@Api(value="/creditScore",tags = "Credit Score APIs")
@Slf4j
public class CreditScoreController {

    @GetMapping("/{identity}")
    @ApiOperation(value="Get Credit Score By Given Identity Number",response = CreditScoreResult.class)
    public CreditScoreResult getCreditScoreResult(@PathVariable("identity") String identity_number){

        // Kredi skorunu random yarattık
        int random_number = (int) (Math.random() * 1500);
        log.info("Kredi skoru gönderiliyor skor : " + random_number + " TC No: " + identity_number);
        return new CreditScoreResult(identity_number, random_number);
    }

}
