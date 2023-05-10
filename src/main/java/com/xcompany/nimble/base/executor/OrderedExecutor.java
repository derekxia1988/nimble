package com.xcompany.nimble.base.executor;


import com.mongodb.lang.NonNull;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class OrderedExecutor<K> implements Executor, AutoCloseable {

    /**
     * 任务执行器。
     *
     * @see java.util.concurrent.ThreadPoolExecutor
     */
    private final Executor inner;

    /**
     * 当执行期间产生异常时被调用。
     */
    private final Consumer<Throwable> whenThrew;

    /**
     * 维护每个OrderKey的任务状态。
     * 通过弱引用维护一个状态表，因此在任务完成后内存可以被垃圾回收释放。
     */
    private final Map<K, WeakReference<CompletionStage<?>>> statuses = Collections.synchronizedMap(new WeakHashMap<>());

    /**
     * 这个Executor的状态，{@code true}表示没有被关闭。
     */
    private final AtomicBoolean running = new AtomicBoolean(true);

    public OrderedExecutor(Executor inner, Consumer<Throwable> whenThrew) {
        this.inner = Objects.requireNonNull(inner, "inner");
        this.whenThrew = Objects.requireNonNull(whenThrew, "whenThrew");
    }

    /**
     * 当前状态
     * @return 所有任务当前执行状态
     */
    public Map<K, CompletionStage<?>> statuses() {
        return statuses.entrySet().stream().collect(Collectors.toUnmodifiableMap(
            Map.Entry::getKey,
            e -> Objects.requireNonNullElseGet(e.getValue().get(), () -> CompletableFuture.completedFuture(null))
        ));
    }

    /**
     * 返回一个用于某个键的{@link Executor}，确保使用这个{@code Executor}执行的所有命令以这个键的顺序执行。
     *
     * @param k 键
     * @return {@code k}的{@link Executor}
     */
    public Executor forK(@NonNull K k) {
        Objects.requireNonNull(k, "k");

        return runnable -> execute(k, runnable);
    }

    @Override
    public void execute(@NonNull Runnable command) {
        inner.execute(command);
    }

    /**
     * 按顺序执行一个命令。
     *
     * @param k       顺序。
     * @param command 命令。
     */
    public void execute(@NonNull K k, @NonNull Runnable command) {
        Objects.requireNonNull(k, "k");
        Objects.requireNonNull(command, "command");

        if (running.get()) {
            statuses.compute(k, (key, reference) -> {
                var status = reference != null ? reference.get() : null;
                CompletionStage<Void> temp = status == null ? CompletableFuture.runAsync(command, this) :
                        status.thenRunAsync(command, this);
                return new WeakReference<>(temp.exceptionally(throwable -> {
                    whenThrew.accept(throwable);
                    return null;
                }));
            });
        } else {
            throw new IllegalStateException("shutdown");
        }
    }

    /**
     * 给正在执行的任务链末尾缀一个新的异步任务，不影响产生异步结果的逻辑顺序
     *
     * @param k     顺序
     * @param async 异步结果
     * @return 新的任务的执行状态
     */
    public CompletionStage<?> compose(@NonNull K k, @NonNull Supplier<CompletionStage<?>> async) {
        Objects.requireNonNull(k, "k");
        Objects.requireNonNull(async, "async");

        if (running.get()) {
            return statuses.compute(k, (key, reference) -> {
                var status = reference != null ? reference.get() : null;
                CompletionStage<?> temp = status == null
                        ? CompletableFuture.supplyAsync(async, this).thenCompose(c -> c)
                        : status.thenComposeAsync(v -> async.get(), this);
                return new WeakReference<>(temp.exceptionally(throwable -> {
                    whenThrew.accept(throwable);
                    return null;
                }));
            }).get();
        } else {
            throw new IllegalStateException("shutdown");
        }
    }

    @Override
    public void close() {
        if (running.compareAndSet(true, false)) {
            CompletableFuture.allOf(
                    this.statuses.values()
                            .stream()
                            .map(WeakReference::get)
                            .filter(Objects::nonNull)
                            .map(CompletionStage::toCompletableFuture)
                    .toArray(CompletableFuture[]::new)
            ).join();
        }
    }
}