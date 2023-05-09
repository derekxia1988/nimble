/*
 * Copyright (c) 2022.  Topjoy all rights reserved.
 */

package com.xcompany.nimble.biz.executor;

import com.google.common.base.Preconditions;
import com.xcompany.nimble.base.executor.OrderedExecutor;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.Executor;
import java.util.function.Consumer;

/**
 * @author xiadong
 * @since 2022/04/01
 **/
public class BizExecutor extends OrderedExecutor<String> {
    public BizExecutor(Executor inner, Consumer<Throwable> whenThrew) {
        super(inner, whenThrew);
    }

    @Override
    public void execute(@NonNull String rid, @NonNull Runnable command) {
        Preconditions.checkNotNull(command, "command is null, rid = " + rid);
        super.execute(rid, command);
    }
}
