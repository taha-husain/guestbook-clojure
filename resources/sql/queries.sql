--name:save-message!
-- creates a new message
INSERT INTO guestbook
(name, message, timestamp)
VALUES (:name, :message, :timestamp)

--name:get-messages
-- selects all available messages
SELECT * from guestbook

--name:delete-message!
-- deletes a message
DELETE FROM guestbook
WHERE id = :id
