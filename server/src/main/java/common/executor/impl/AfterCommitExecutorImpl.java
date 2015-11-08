package common.executor.impl;

import common.executor.AfterCommitExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import web.persistence.domain.User;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static org.springframework.transaction.support.TransactionSynchronizationManager.isSynchronizationActive;

// @author Titov Mykhaylo (titov) on 28.04.2014.
@Component
@Slf4j
public class AfterCommitExecutorImpl extends TransactionSynchronizationAdapter implements AfterCommitExecutor {

    private static final ThreadLocal<List<Runnable>> COMMANDS = new ThreadLocal<>();

    @Override
    public void execute(Runnable command) {
        if (!isSynchronizationActive()) {
            command.run();
            return;
        }
        List<Runnable> threadCommands = COMMANDS.get();
        if (isNull(threadCommands)) {
            threadCommands = new ArrayList<>();
            COMMANDS.set(threadCommands);
            TransactionSynchronizationManager.registerSynchronization(this);
        }
        threadCommands.add(command);
    }

    @Override
    public void afterCommit() {
        List<Runnable> threadCommands = COMMANDS.get();
        for (Runnable threadCommand : threadCommands) {
            try {
                threadCommand.run();
            }catch (Exception e){
                log.error("Couldn't execute thread after transaction was committed", e);
            }
        }
    }

    @Override
    public void afterCompletion(int status) {
        COMMANDS.remove();
    }

}
