CREATE TABLE "users" (
    "id" BIGINT NOT NULL,
    "email" VARCHAR(255) NOT NULL,
    "name" VARCHAR(255) NOT NULL,
    "sex" INTEGER NOT NULL,
    "no" VARCHAR(255),
    "password" VARCHAR(255),
    "school" VARCHAR(255),
    "department" VARCHAR(255),
    "role" INTEGER DEFAULT 0 NOT NULL,
    "status" INTEGER DEFAULT 0 NOT NULL,
    
    "create_time" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP ,
    "update_time" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP ,
    CONSTRAINT "pk_users" PRIMARY KEY ("id")
);