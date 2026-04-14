CREATE TABLE "attendances" (
    "student_id" BIGINT NOT NULL,
    "lesson_id" INTEGER NOT NULL,
    "status" INTEGER DEFAULT 0 NOT NULL,
    "note" TEXT,
    CONSTRAINT "pk_attendances" PRIMARY KEY ("student_id", "lesson_id")
);