CREATE TABLE "lessons" (
     "id" SERIAL NOT NULL,
    "course_id" BIGINT NOT NULL,
    "teacher_id" BIGINT NOT NULL,
    "start_time" TIMESTAMPTZ  NOT NULL,
    "end_time" TIMESTAMPTZ  NOT NULL,
    "classroom" VARCHAR(255),
    "status" INTEGER DEFAULT 0 NOT NULL,
    "create_time" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP ,
    "update_time" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP ,
    CONSTRAINT "pk_lessons" PRIMARY KEY ("id")
);