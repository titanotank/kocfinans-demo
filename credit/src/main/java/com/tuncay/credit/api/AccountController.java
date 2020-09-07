package com.tuncay.credit.api;

import com.tuncay.credit.dto.AccountDTO;
import com.tuncay.credit.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/account")
@Api(value="/account",tags = "Credit Account APIs")
@Slf4j
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{identity}")
    @ApiOperation(value="Select Account By Identity Number",response = AccountDTO.class)
    public AccountDTO getAccountByIdentityNumber(@PathVariable("identity") String identityNumber){
        log.info("Request geldi TC NO: "+identityNumber + " Hesap bilgisi alınıyor");
        return accountService.getAccountDTOByIdentityNumber(identityNumber);
    }
}
