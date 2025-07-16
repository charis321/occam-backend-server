package com.charis.occam_spm_sys.common;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BatchInsertUtil {

    private static final int DEFAULT_BATCH_SIZE = 500;

    /**
     * 將 list 資料分批插入，出錯的批次會跳過並記錄
     * @param data 原始資料列表
     * @param batchSize 每批筆數
     * @param saveFunction 一個 consumer，接受一批資料執行保存（通常是 saveBatch）
     * @param <T> 資料型別
     * @return 失敗的批次資料
     */
    public static <T> List<List<T>> batchInsertWithSkip(
            List<T> data,
            int batchSize,
            Consumer<List<T>> saveFunction
    ) {
        List<List<T>> failedBatches = new ArrayList<>();

        for (int i = 0; i < data.size(); i += batchSize) {
            List<T> batch = data.subList(i, Math.min(i + batchSize, data.size()));
            try {
                saveFunction.accept(batch); // 呼叫你的 service.saveBatch()
            } catch (Exception e) {
                failedBatches.add(batch);
                System.err.println("批次插入失敗，筆數：" + batch.size());
                e.printStackTrace();
            }
        }

        return failedBatches;
    }

    public static <T> List<List<T>> batchInsertWithSkip(
            List<T> data,
            Consumer<List<T>> saveFunction
    ) {
        return batchInsertWithSkip(data, DEFAULT_BATCH_SIZE, saveFunction);
    }
}