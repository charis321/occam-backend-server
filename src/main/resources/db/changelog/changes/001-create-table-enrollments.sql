CREATE TABLE "enrollments" (
    "course_id" BIGINT NOT NULL,
    "student_id" BIGINT NOT NULL,
    "status" INTEGER DEFAULT 0 NOT NULL,
    "note" TEXT,
    "create_time" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP ,
    "update_time" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP ,
    CONSTRAINT "pk_enrollments" PRIMARY KEY ("course_id", "student_id")
);