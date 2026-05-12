CREATE TABLE "rollcalls" (
    "lesson_id" INT NOT NULL,
    "mode" int DEFAULT 0 NOT NULL,
    
    "rotation_time" int,
    "next_rotation_time" TIMESTAMPTZ,
    "start_time" TIMESTAMPTZ,
    "end_time" TIMESTAMPTZ,
    
    "status" INTEGER DEFAULT 0 NOT NULL,
    "code" VARCHAR(255),
    
    CONSTRAINT "pk_rollcalls" PRIMARY KEY ("lesson_id")
);
