CREATE TABLE "users" (
    "id" BIGINT NOT NULL,
    "name" VARCHAR(255),
    "email" VARCHAR(255),
    "password" VARCHAR(255),
    "school" VARCHAR(255),
    "department" VARCHAR(255),
    "role" INTEGER DEFAULT 0 NOT NULL,
    "status" INTEGER DEFAULT 1 NOT NULL,
    "sex" INTEGER,
    "create_time" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    "update_time" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT "pk_users" PRIMARY KEY ("id")
);