-- V5__Initialize_Wallets.sql

-- Ensure every user has a wallet
INSERT INTO wallets (id, user_id, balance)
SELECT gen_random_uuid()::text, id, 0
FROM users
WHERE id NOT IN (SELECT user_id FROM wallets);
