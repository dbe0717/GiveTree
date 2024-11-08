package com.dareuda.givetree.token.domain;

import com.dareuda.givetree.blockchain.utils.EthereumCaller;
import com.dareuda.givetree.blockchain.utils.EthereumTransactionManager;
import com.dareuda.givetree.common.config.AdminConfig;
import com.dareuda.givetree.common.config.ContractConfig;
import com.dareuda.givetree.token.infrastructure.TokenContract;
import com.dareuda.givetree.token.infrastructure.TokenContractExceptionHandler;
import com.dareuda.givetree.transaction.domain.TransactionAppender;
import com.dareuda.givetree.wallet.domain.Wallet;
import com.dareuda.givetree.wallet.domain.WalletReader;
import org.springframework.stereotype.Component;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.Set;

@Component
public class TokenGenerator {

    public TokenGenerator(
            AdminConfig adminConfig,
            WalletReader walletReader,
            ContractConfig contractConfig,
            EthereumTransactionManager transactionManager,
            TransactionAppender transactionAppender,
            TokenContractExceptionHandler exceptionHandler
    ) {
        this.adminConfig = adminConfig;
        this.walletReader = walletReader;
        this.contractConfig = contractConfig;
        this.transactionManager = transactionManager;
        this.transactionAppender = transactionAppender;
        this.caller = new EthereumCaller(exceptionHandler);
    }

    private final AdminConfig adminConfig;
    private final WalletReader walletReader;
    private final ContractConfig contractConfig;
    private final TransactionAppender transactionAppender;
    private final EthereumTransactionManager transactionManager;
    private final EthereumCaller caller;

    public TransactionReceipt generate(long walletId, long amount, long ledgerId) {
        Wallet wallet = walletReader.read(walletId);

        TransactionReceipt receipt = transactionManager.execute(
                Set.of(wallet.getAddress()),
                contractConfig.getTokenContractAddress(),
                TokenContract.class,
                (TokenContract tokenContract) -> caller.call(tokenContract.mintToken(wallet.getAddress(), BigInteger.valueOf(amount)))
        );

        transactionAppender.append(adminConfig.getWalletId(), walletId, amount, receipt.getTransactionHash(), ledgerId);
        return receipt;
    }

}
