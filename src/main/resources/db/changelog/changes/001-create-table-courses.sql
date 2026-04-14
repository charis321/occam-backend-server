-- 建立 courses 資料表
CREATE TABLE "courses" (
    "id" BIGINT NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "teacher_id" BIGINT NOT NULL,
    "school" VARCHAR(255),
    "department" VARCHAR(255),
    "schedule_week" INTEGER,
    "schedule_start_time" TIME WITHOUT TIME ZONE,
    "schedule_end_time" TIME WITHOUT TIME ZONE,
    "duration" INTEGER,
    "classroom" VARCHAR(255),
    "info" TEXT,
    "create_time" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP ,
    "update_time" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP ,
    "status" INTEGER DEFAULT 0 NOT NULL,
    CONSTRAINT "pk_courses" PRIMARY KEY ("id")
);