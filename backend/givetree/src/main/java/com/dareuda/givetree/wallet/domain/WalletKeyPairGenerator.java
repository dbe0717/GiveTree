package com.dareuda.givetree.wallet.domain;

import com.dareuda.givetree.common.errors.exception.RestApiException;
import com.dareuda.givetree.wallet.controller.WalletErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;

@Component
@RequiredArgsConstructor
public class WalletKeyPairGenerator {

    public WalletKeyPair generate() {
        ECKeyPair keyPair = null;
        try {
            keyPair = Keys.createEcKeyPair();
        } catch (Exception e){
            throw new RestApiException(WalletErrorCode.WALLET_GENERATE_FAIL);
        }
        String address = "0x"+Keys.getAddress(keyPair);
        String privateKey = "0x"+String.format("%064x", keyPair.getPrivateKey());
        return new WalletKeyPair(address, privateKey);
    }
}
