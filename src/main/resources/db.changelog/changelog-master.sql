--liquibase formatted sql

--changeset mediocreasian:001-create-accounts
CREATE TABLE accounts
(
    id                  BIGSERIAL PRIMARY KEY,
    user_id             VARCHAR(10) NOT NULL,
    service_account_uid VARCHAR(55) NOT NULL,
    created_at          TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

ALTER TABLE accounts
    ADD CONSTRAINT uq_accounts_user_id UNIQUE (user_id);

ALTER TABLE accounts
    ADD CONSTRAINT uq_accounts_service_account_uid UNIQUE (service_account_uid);


--changeset mediocreasian:002-create-bill-history
CREATE TABLE bill_history
(
    id                 BIGSERIAL PRIMARY KEY,
    account_id         BIGINT      NOT NULL,
    bill_code          VARCHAR(30) NOT NULL,
    total_amount_cents BIGINT      NOT NULL,
    created_at         TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at         TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_bill_history_account_id
    ON bill_history (account_id);

ALTER TABLE bill_history
    ADD CONSTRAINT uq_bill_history_account_billcode
        UNIQUE (account_id, bill_code);

ALTER TABLE bill_history
    ADD CONSTRAINT fk_bill_history_account
        FOREIGN KEY (account_id)
            REFERENCES accounts (id)
            ON DELETE RESTRICT;


--changeset mediocreasian:003-create-bill-history-images
CREATE TABLE bill_history_images
(
    id              BIGSERIAL PRIMARY KEY,
    bill_history_id BIGINT NULL,
    file_name       VARCHAR(255) NOT NULL,
    storage_key     VARCHAR(500) NOT NULL,
    mime_type       VARCHAR(100) NOT NULL,
    file_size_bytes BIGINT       NOT NULL,
    created_at      TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

ALTER TABLE bill_history_images
    ADD CONSTRAINT fk_bill_history_images_bill_history
        FOREIGN KEY (bill_history_id)
            REFERENCES bill_history (id);

-- Enforces: one bill_history can have at most one image
ALTER TABLE bill_history_images
    ADD CONSTRAINT uq_bill_history_images_bill_history_id
        UNIQUE (bill_history_id);


--changeset mediocreasian:004-create-bill-history-details
CREATE TABLE bill_history_details
(
    id              BIGSERIAL PRIMARY KEY,
    bill_history_id BIGINT      NOT NULL,
    amount_cents    BIGINT      NOT NULL,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_bill_details_bill_history_id
    ON bill_history_details (bill_history_id);

ALTER TABLE bill_history_details
    ADD CONSTRAINT fk_bill_history_details_bill_history
        FOREIGN KEY (bill_history_id)
            REFERENCES bill_history (id)
            ON DELETE CASCADE;


--changeset mediocreasian:005-create-bill-history-payees
CREATE TABLE bill_history_payees
(
    id                     BIGSERIAL PRIMARY KEY,
    bill_history_id        BIGINT       NOT NULL,
    payee_name             VARCHAR(100) NOT NULL,
    payee_mobile_no        VARCHAR(20),
    payee_telegram_chat_id VARCHAR(100),
    amount_cents           BIGINT       NOT NULL,
    created_at             TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at             TIMESTAMPTZ  NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_bill_payees_bill_history_id
    ON bill_history_payees (bill_history_id);

ALTER TABLE bill_history_payees
    ADD CONSTRAINT fk_bill_history_payees_bill_history
        FOREIGN KEY (bill_history_id)
            REFERENCES bill_history (id)
            ON DELETE CASCADE;
