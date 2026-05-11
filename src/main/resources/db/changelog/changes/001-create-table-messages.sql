create table "messages"(
    "id" BIGINT NOT NULL,
    "sender_id" BIGINT NOT NULL,
    "receiver_id" BIGINT NOT NULL,
    "title" VARCHAR(255),
    "body" VARCHAR(500),
    "attachment" VARCHAR(255),
    "create_time" TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP ,
    "noted" BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT "pk_messages" PRIMARY KEY ("id")
)