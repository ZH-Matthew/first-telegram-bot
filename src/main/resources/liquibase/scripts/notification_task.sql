-- liquibase formatted sql

-- changeset mzhitenev:1
CREATE TABLE notification_task (
                       id SERIAL PRIMARY KEY,
                       chat_id BIGINT,
                       notification TEXT,
                       notification_send_time TIMESTAMP WITH TIME ZONE
)